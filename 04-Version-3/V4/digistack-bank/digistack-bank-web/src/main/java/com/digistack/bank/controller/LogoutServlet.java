package com.digistack.bank.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogoutServlet
 *
 * Version 2, Sprint 4: invalidates the current session entirely and
 * redirects to Login. Uses sendRedirect (not forward) so the browser
 * genuinely navigates to a fresh page - see Sprint 4 notes on why this
 * distinction matters for logout specifically.
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        // getSession(false): do NOT create a new session just to log out
        // of a session that doesn't exist - if there's no session,
        // there's nothing to invalidate.

        if (session != null) {
            String sessionId = session.getId();
            String username = (String) session.getAttribute("username");

            session.invalidate();

            LOGGER.info("LogoutServlet: Session invalidated. sessionId=" + sessionId
                    + ", username=" + username);
        } else {
            LOGGER.info("LogoutServlet: Logout called with no active session.");
        }

        response.sendRedirect("Login");
    }
}