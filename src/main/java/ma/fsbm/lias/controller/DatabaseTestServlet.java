package ma.fsbm.lias.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fsbm.lias.util.DatabaseConnection;

@WebServlet("/test-db")
public class DatabaseTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        try (Connection connection = DatabaseConnection.getConnection()) {
            response.getWriter().println(
                    "<h2>Connexion à MySQL réussie ✅</h2>"
            );
        } catch (SQLException e) {
            getServletContext().log("Erreur de connexion MySQL", e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(
                    "<h2>Connexion à MySQL échouée ❌</h2>"
            );
        }
    }
}