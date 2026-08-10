package com.digistack.bank.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.digistack.bank.dao.AccountDao;
import com.digistack.bank.exception.InsufficientFundsException;

/**
 * AccountService
 *
 * Business logic layer for Deposit/Withdraw. This is the ONLY class
 * that enforces banking rules (e.g. "cannot withdraw more than the
 * balance").
 *
 * AccountDao deliberately does not know these rules (Sprint 2),
 * and the Controller (Sprint 4) will never bypass this class to
 * call AccountDao directly.
 */
public class AccountService {

    private static final Logger LOGGER =
            Logger.getLogger(AccountService.class.getName());

    private final AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    /**
     * Deposits a positive amount into the given account.
     *
     * @param accountNumber the account to deposit into
     * @param amount        the amount to deposit - must be positive
     * @return the new balance after the deposit
     * @throws SQLException if a database error occurs
     * @throws IllegalArgumentException if amount is zero or negative
     */
    public BigDecimal deposit(String accountNumber, BigDecimal amount)
            throws SQLException {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be positive. Received: " + amount);
        }

        BigDecimal currentBalance =
                accountDao.getBalance(accountNumber);

        BigDecimal newBalance =
                currentBalance.add(amount);

        accountDao.updateBalance(accountNumber, newBalance);

        LOGGER.info(
                "AccountService.deposit: accountNumber=" + accountNumber
                + ", amount=" + amount
                + ", oldBalance=" + currentBalance
                + ", newBalance=" + newBalance
        );

        return newBalance;
    }

    /**
     * Withdraws a positive amount from the given account, rejecting
     * the operation if it would exceed the current balance.
     *
     * @param accountNumber the account to withdraw from
     * @param amount        the amount to withdraw - must be positive
     * @return the new balance after the withdrawal
     * @throws SQLException if a database error occurs
     * @throws InsufficientFundsException if amount exceeds current balance
     * @throws IllegalArgumentException if amount is zero or negative
     */
    public BigDecimal withdraw(
            String accountNumber,
            BigDecimal amount)
            throws SQLException, InsufficientFundsException {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be positive. Received: " + amount);
        }

        BigDecimal currentBalance =
                accountDao.getBalance(accountNumber);

        // Business rule: cannot withdraw more than available balance.
        if (amount.compareTo(currentBalance) > 0) {

            LOGGER.warning(
                    "AccountService.withdraw: REJECTED - accountNumber="
                    + accountNumber
                    + ", requested=" + amount
                    + ", available=" + currentBalance
            );

            throw new InsufficientFundsException(
                    "Withdrawal of " + amount
                    + " exceeds available balance of "
                    + currentBalance
            );
        }

        BigDecimal newBalance =
                currentBalance.subtract(amount);

        accountDao.updateBalance(accountNumber, newBalance);

        LOGGER.info(
                "AccountService.withdraw: accountNumber=" + accountNumber
                + ", amount=" + amount
                + ", oldBalance=" + currentBalance
                + ", newBalance=" + newBalance
        );

        return newBalance;
    }
}