package com.digistack.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digistack.bank.exception.InsufficientFundsException;
import com.digistack.bank.service.AccountService;

/**
 * ServiceTestServlet
 *
 * TEMPORARY - Sprint 3 verification only. Proves AccountService's
 * deposit/withdraw business rules work correctly, including the
 * overdraft rejection. Deleted once Sprint 4's real UI exists.
 */
@WebServlet("/ServiceTest")
public class ServiceTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        AccountService service = new AccountService();
        String accountNumber = "DSB-ACC-0001";

        try {
            BigDecimal startingBalance = new java.math.BigDecimal("1000.00");

            // Test 1: Deposit increases balance correctly.
            BigDecimal afterDeposit = service.deposit(accountNumber, new BigDecimal("100.00"));
            out.println("Test 1 - Deposit 100.00: new balance = " + afterDeposit
                    + " (expected 1100.00) -> " + (afterDeposit.compareTo(new BigDecimal("1100.00")) == 0 ? "PASS" : "FAIL"));

            // Test 2: Withdraw decreases balance correctly.
            BigDecimal afterWithdraw = service.withdraw(accountNumber, new BigDecimal("50.00"));
            out.println("Test 2 - Withdraw 50.00: new balance = " + afterWithdraw
                    + " (expected 1050.00) -> " + (afterWithdraw.compareTo(new BigDecimal("1050.00")) == 0 ? "PASS" : "FAIL"));

            // Test 3: Over-withdrawal is correctly REJECTED.
            boolean overdraftRejected = false;
            try {
                service.withdraw(accountNumber, new BigDecimal("999999.00"));
            } catch (InsufficientFundsException e) {
                overdraftRejected = true;
                out.println("Test 3 - Over-withdrawal correctly rejected: " + e.getMessage());
            }
            out.println("Test 3 result -> " + (overdraftRejected ? "PASS" : "FAIL (overdraft was NOT rejected!)"));

            // Restore original balance so we don't leave test data behind.
            BigDecimal currentBalance = new java.math.BigDecimal(afterWithdraw.toString());
            BigDecimal adjustment = startingBalance.subtract(currentBalance);
            if (adjustment.compareTo(BigDecimal.ZERO) > 0) {
                service.deposit(accountNumber, adjustment);
            } else if (adjustment.compareTo(BigDecimal.ZERO) < 0) {
                service.withdraw(accountNumber, adjustment.abs());
            }

            out.println();
            out.println("Balance restored to original 1000.00 for clean state.");

        } catch (Exception e) {
            out.println("SERVICE TEST FAILED - Unexpected exception: " + e.getMessage());
        }
    }
}