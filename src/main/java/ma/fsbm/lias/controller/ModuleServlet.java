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
        MODULES.put("equipes", new String[]{"Équipes et gouvernance", "Équipes de recherche, chefs d’équipe et mandats."});
        MODULES.put("adhesions", new String[]{"Demandes d’adhésion", "Instruction et décisions des demandes déposées en ligne."});
        MODULES.put("evenements", new String[]{"Événements scientifiques", "Conférences, séminaires, workshops et calendrier."});
        MODULES.put("publications", new String[]{"Publications", "Référencement par auteur, année et équipe."});
        MODULES.put("documents", new String[]{"Archives documentaires", "Documents administratifs, programmes, rapports et attestations."});
        MODULES.put("materiel", new String[]{"Matériel", "Arrivages, distribution équitable et besoins exprimés."});
        MODULES.put("conventions", new String[]{"Conventions et collaborations", "Partenaires, conventions et activités associées."});
        MODULES.put("reunions", new String[]{"Réunions et procès-verbaux", "Ordres du jour, PV et mémoire des décisions."});
        MODULES.put("rapport", new String[]{"Rapport annuel", "Consolidation annuelle des activités scientifiques et administratives."});
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cle = request.getParameter("page");
        String[] module = MODULES.getOrDefault(cle, new String[]{"Module", "Module non trouvé."});
        request.setAttribute("titre", module[0]);
        request.setAttribute("description", module[1]);
        request.getRequestDispatcher("/WEB-INF/views/module.jsp").forward(request, response);
    }
}
