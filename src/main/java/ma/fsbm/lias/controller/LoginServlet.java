package ma.fsbm.lias.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.service.AuthService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        try {
            Optional<Utilisateur> resultat =
                    authService.authentifier(email, motDePasse);

            if (resultat.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute(
                        "utilisateurConnecte",
                        resultat.get()
                );

                response.sendRedirect(
                        request.getContextPath() + "/dashboard"
                );
                return;
            }

            request.setAttribute(
                    "erreur",
                    "E-mail ou mot de passe incorrect."
            );

            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log("Erreur de connexion", e);

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            request.setAttribute(
                    "erreur",
                    "Une erreur technique est survenue."
            );

            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                   .forward(request, response);
        }
    }
}