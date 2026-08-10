package com.digistack.bank.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digistack.bank.util.PasswordUtil;

/**
 * LoginServlet
 *
 * Version 2:
 *  - Sprint 2: credential validation against users table.
 *  - Sprint 3 (this update): creates a real HttpSession on successful
 *    login, storing a "lastLogin" attribute.
 *
 * Direct JDBC (DriverManager) - deliberate Technical Debt, resolved at
 * Version 7.
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    private static final String JDBC_URL = "jdbc:postgresql://192.168.10.30:5432/digistack_bank";
    private static final String DB_USER = "digistack_app";
    private static final String DB_PASSWORD = "ChangeThisToAStrongPassword123!"; // TODO: your real password

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String errorMessage = null;
        boolean loginSuccess = false;

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            errorMessage = "Username and password are both required.";
        } else {
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

                String sql = "SELECT password_hash, salt FROM users WHERE username = ?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            String storedHash = rs.getString("password_hash");
                            String salt = rs.getString("salt");
                            String computedHash = PasswordUtil.hashPassword(password, salt);

                            if (computedHash.equals(storedHash)) {
                                loginSuccess = true;
                                LOGGER.info("LoginServlet: Successful login for username=" + username);
                            } else {
                                errorMessage = "Invalid username or password.";
                                LOGGER.warning("LoginServlet: Failed login attempt (bad password) for username=" + username);
                            }
                        } else {
                            errorMessage = "Invalid username or password.";
                            LOGGER.warning("LoginServlet: Failed login attempt (unknown username)=" + username);
                        }
                    }
                }

            } catch (Exception e) {
                errorMessage = "A system error occurred. Please try again later.";
                LOGGER.log(Level.SEVERE, "LoginServlet: Database error during login.", e);
            }
        }

        if (loginSuccess) {
            // --- Sprint 3: create the HttpSession and store lastLogin ---
            HttpSession session = request.getSession(true);
            // getSession(true) means: "give me the existing session for
            // this browser if one exists, or create a brand new one if
            // it doesn't." This is the actual moment a session is born.

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lastLoginTimestamp = sdf.format(new Date());

            session.setAttribute("lastLogin", lastLoginTimestamp);
            session.setAttribute("username", username);

            LOGGER.info("LoginServlet: Session created, sessionId=" + session.getId()
                    + ", lastLogin=" + lastLoginTimestamp);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginSuccess.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
            dispatcher.forward(request, response);
        }
    }
}