package com.digistack.bank.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AppConfigTestServlet
 *
 * Version 1 (Sprint 3/4) - direct JDBC read of app_config, forwarded to
 * Home.jsp for rendering. Direct JDBC is deliberate Technical Debt,
 * resolved at Version 7 (migration to JNDI DataSource/pooling).
 */
@WebServlet("/Home")
public class AppConfigTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AppConfigTestServlet.class.getName());

    private static final String JDBC_URL = "jdbc:postgresql://192.168.10.30:5432/digistack_bank";
    private static final String DB_USER = "digistack_app";
    private static final String DB_PASSWORD = "Wasadmin@951951";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String configValue;
        String errorMessage = null;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

            LOGGER.info("AppConfigTestServlet: Connected to PostgreSQL successfully.");

            String sql = "SELECT config_value FROM app_config WHERE config_key = 'welcome_message'";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                if (rs.next()) {
                    configValue = rs.getString("config_value");
                    LOGGER.info("AppConfigTestServlet: Read value -> " + configValue);
                } else {
                    configValue = null;
                    errorMessage = "Query returned no rows.";
                    LOGGER.warning("AppConfigTestServlet: " + errorMessage);
                }
            }

        } catch (Exception e) {
            configValue = null;
            errorMessage = e.getMessage();
            LOGGER.log(Level.SEVERE, "AppConfigTestServlet: DB connection or query failed.", e);
        }

        request.setAttribute("configValue", configValue);
        request.setAttribute("errorMessage", errorMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Home.jsp");
        dispatcher.forward(request, response);
    }
}
