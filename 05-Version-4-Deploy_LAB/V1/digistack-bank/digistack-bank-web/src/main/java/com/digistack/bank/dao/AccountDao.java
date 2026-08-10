package com.digistack.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AccountDao
 *
 * Data Access Object for the accounts table. This class is the ONLY
 * place in the application that runs SQL against accounts - the
 * Service layer (Sprint 3) and Controller layer (Sprint 4) never touch
 * JDBC directly, they call these plain Java methods instead.
 *
 * Direct JDBC (DriverManager) - deliberate Technical Debt, resolved at
 * Version 7 (JNDI DataSource migration). This same pattern is repeated
 * from LoginServlet/AppConfigTestServlet - all direct-JDBC code in this
 * project gets migrated together at v7.
 */
public class AccountDao {

    private static final Logger LOGGER = Logger.getLogger(AccountDao.class.getName());

    private static final String JDBC_URL = "jdbc:postgresql://192.168.10.30:5432/digistack_bank";
    private static final String DB_USER = "digistack_app";
    private static final String DB_PASSWORD = "ChangeThisToAStrongPassword123!"; // TODO: your real password

    /**
     * Retrieves the current balance for a given account number.
     *
     * @param accountNumber the account's public identifier (e.g. "DSB-ACC-0001")
     * @return the current balance
     * @throws SQLException if the account doesn't exist, or a DB error occurs
     */
    public BigDecimal getBalance(String accountNumber) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal balance = rs.getBigDecimal("balance");
                    LOGGER.info("AccountDao.getBalance: accountNumber=" + accountNumber + ", balance=" + balance);
                    return balance;
                } else {
                    throw new SQLException("No account found with account_number=" + accountNumber);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "AccountDao.getBalance: DB error for accountNumber=" + accountNumber, e);
            throw e;
        }
    }

    /**
     * Overwrites an account's balance with a new value.
     *
     * NOTE: this is a deliberately "dumb" method - it just SETS the
     * balance to whatever you give it. It does NOT know about deposit
     * vs withdraw, does NOT check for a negative result, does NOT
     * enforce any business rule. Those rules belong in the Service
     * layer (Sprint 3) - the DAO's job is only "talk to the database
     * exactly as told," nothing more.
     *
     * @param accountNumber the account's public identifier
     * @param newBalance    the new balance value to set
     * @throws SQLException if the account doesn't exist, or a DB error occurs
     */
    public void updateBalance(String accountNumber, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, newBalance);
            stmt.setString(2, accountNumber);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No account found with account_number=" + accountNumber + " - update affected 0 rows.");
            }

            LOGGER.info("AccountDao.updateBalance: accountNumber=" + accountNumber + ", newBalance=" + newBalance);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "AccountDao.updateBalance: DB error for accountNumber=" + accountNumber, e);
            throw e;
        }
    }
/**
     * Looks up the account_number belonging to a given username, via
     * the users -> accounts foreign key relationship (user_id).
     *
     * Assumes exactly one account per user, which is true for this
     * version - a real bank would support multiple accounts per
     * customer, but that's explicitly out of scope until later Parts.
     *
     * @param username the logged-in user's username
     * @return the account_number for that user's account
     * @throws SQLException if no matching account is found, or a DB error occurs
     */
    public String getAccountNumberForUsername(String username) throws SQLException {
        String sql = "SELECT a.account_number FROM accounts a "
                + "JOIN users u ON a.user_id = u.id "
                + "WHERE u.username = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("account_number");
                } else {
                    throw new SQLException("No account found for username=" + username);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "AccountDao.getAccountNumberForUsername: DB error for username=" + username, e);
            throw e;
        }
    }
}