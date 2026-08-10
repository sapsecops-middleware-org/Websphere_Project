package com.digistack.bank.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digistack.bank.dao.AccountDao;
import com.digistack.bank.exception.InsufficientFundsException;
import com.digistack.bank.service.AccountService;

/**
 * AccountController
 *
 * Version 3, Sprint 4: displays Balance and handles Deposit/Withdraw
 * form submissions. Contains NO business logic itself - all balance
 * math and rules live in AccountService (Sprint 3). This class only:
 * reads the logged-in username from the session, reads form input,
 * calls the Service layer, and decides which JSP to show.
 */
@WebServlet("/Account")
public class AccountController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AccountDao accountDao = new AccountDao();
    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.sendRedirect("Login");
            return;
        }

        showAccountPage(request, response, username, null, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");
        String amountText = request.getParameter("amount");

        String successMessage = null;
        String errorMessage = null;

        try {
            String accountNumber = accountDao.getAccountNumberForUsername(username);

            BigDecimal amount;
            try {
                amount = new BigDecimal(amountText);
            } catch (NumberFormatException e) {
                errorMessage = "Please enter a valid numeric amount.";
                showAccountPage(request, response, username, successMessage, errorMessage);
                return;
            }

            if ("deposit".equals(action)) {
                BigDecimal newBalance = accountService.deposit(accountNumber, amount);
                successMessage = "Deposit of " + amount + " successful. New balance: " + newBalance;

            } else if ("withdraw".equals(action)) {
                try {
                    BigDecimal newBalance = accountService.withdraw(accountNumber, amount);
                    successMessage = "Withdrawal of " + amount + " successful. New balance: " + newBalance;
                } catch (InsufficientFundsException e) {
                    errorMessage = e.getMessage();
                }

            } else {
                errorMessage = "Unknown action requested.";
            }

        } catch (IllegalArgumentException e) {
            // Thrown by AccountService for a zero/negative amount.
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = "A system error occurred. Please try again later.";
        }

        showAccountPage(request, response, username, successMessage, errorMessage);
    }

    /**
     * Shared helper: looks up the current balance and forwards to
     * Account.jsp, with optional success/error messages from a prior
     * form submission.
     */
    private void showAccountPage(HttpServletRequest request, HttpServletResponse response,
                                  String username, String successMessage, String errorMessage)
            throws ServletException, IOException {

        try {
            String accountNumber = accountDao.getAccountNumberForUsername(username);
            BigDecimal balance = accountDao.getBalance(accountNumber);

            request.setAttribute("accountNumber", accountNumber);
            request.setAttribute("balance", balance);
        } catch (Exception e) {
            errorMessage = "Unable to load account information.";
        }

        request.setAttribute("successMessage", successMessage);
        request.setAttribute("errorMessage", errorMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Account.jsp");
        dispatcher.forward(request, response);
    }
}