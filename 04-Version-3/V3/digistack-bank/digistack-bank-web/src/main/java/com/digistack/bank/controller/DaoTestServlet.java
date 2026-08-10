package com.digistack.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digistack.bank.dao.AccountDao;

/**
 * DaoTestServlet
 *
 * TEMPORARY - Sprint 2 verification only. Proves AccountDao's
 * getBalance/updateBalance work correctly against the seed account.
 * This servlet is deleted once Sprint 4's real Deposit/Withdraw UI
 * exists - it is not part of the permanent application.
 */
@WebServlet("/DaoTest")
public class DaoTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        AccountDao dao = new AccountDao();
        String accountNumber = "DSB-ACC-0001";

        try {
            BigDecimal originalBalance = dao.getBalance(accountNumber);
            out.println("Step 1 - getBalance: " + originalBalance);

            BigDecimal testBalance = originalBalance.add(new BigDecimal("50.00"));
            dao.updateBalance(accountNumber, testBalance);
            out.println("Step 2 - updateBalance called with: " + testBalance);

            BigDecimal confirmedBalance = dao.getBalance(accountNumber);
            out.println("Step 3 - getBalance after update: " + confirmedBalance);

            // Restore original balance so we don't leave test data behind.
            dao.updateBalance(accountNumber, originalBalance);
            BigDecimal restoredBalance = dao.getBalance(accountNumber);
            out.println("Step 4 - restored to original: " + restoredBalance);

            out.println();
            out.println("DAO TEST " + (restoredBalance.equals(originalBalance) ? "PASSED" : "FAILED"));

        } catch (Exception e) {
            out.println("DAO TEST FAILED - Exception: " + e.getMessage());
        }
    }
}