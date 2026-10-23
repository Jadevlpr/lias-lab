package ma.fsbm.lias.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/module")
public class ModuleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Map<String, String[]> MODULES = new LinkedHashMap<>();
    static {
        MODULES.put("conventions", new String[]{"Conventions et collaborations", "Partenaires, conventions et activités associées."});
        MODULES.put("reunions", new String[]{"Réunions et procès-verbaux", "Ordres du jour, PV et mémoire des décisions."});
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cle = request.getParameter("page");
        
        // Redirection vers les modules pleinement implémentés
        if ("adhesions".equalsIgnoreCase(cle)) {
            response.sendRedirect(request.getContextPath() + "/admin/adhesions");
            return;
        } else if ("equipes".equalsIgnoreCase(cle)) {
            response.sendRedirect(request.getContextPath() + "/admin/equipes");
            return;
        } else if ("evenements".equalsIgnoreCase(cle)) {
            response.sendRedirect(request.getContextPath() + "/admin/evenements");
            return;
        } else if ("publications".equalsIgnoreCase(cle)) {
            response.sendRedirect(request.getContextPath() + "/admin/publications");
            return;
        } else if ("documents".equalsIgnoreCase(cle)) {
            response.sendRedirect(request.getContextPath() + "/admin/documents");
            return;
        } else if ("materiel".equalsIgnoreCase(cle) || "materiels".equalsIgnoreCase(cle)) {
            response.sendRedirect(request.getContextPath() + "/admin/materiels");
            return;
        } else if ("rapport".equalsIgnoreCase(cle)) {
            response.sendRedirect(request.getContextPath() + "/admin/rapport");
            return;
        }

        String[] module = MODULES.getOrDefault(cle, new String[]{"Module", "Module en cours de développement."});
        request.setAttribute("titre", module[0]);
        request.setAttribute("description", module[1]);
        request.getRequestDispatcher("/WEB-INF/views/module.jsp").forward(request, response);
    }
}
