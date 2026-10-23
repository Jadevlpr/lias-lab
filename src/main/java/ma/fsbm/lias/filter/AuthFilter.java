package ma.fsbm.lias.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter({"/dashboard", "/admin/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest requete = (HttpServletRequest) request;
        HttpServletResponse reponse = (HttpServletResponse) response;

        HttpSession session = requete.getSession(false);

        boolean utilisateurConnecte = session != null
                && session.getAttribute("utilisateurConnecte") != null;

        if (utilisateurConnecte) {
            chain.doFilter(request, response);
            return;
        }

        reponse.sendRedirect(
                requete.getContextPath() + "/login"
        );
    }
}
