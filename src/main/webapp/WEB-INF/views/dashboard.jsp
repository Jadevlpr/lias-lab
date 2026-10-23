<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <div class="container dashboard-grid">
        <aside class="sidebar">
            <strong>Administration &amp; Gestion</strong>
            <a href="${pageContext.request.contextPath}/dashboard">Vue d'ensemble</a>
            <a href="${pageContext.request.contextPath}/admin/membres">Membres</a>
            <a href="${pageContext.request.contextPath}/admin/equipes">Équipes &amp; mandats</a>
            <a href="${pageContext.request.contextPath}/admin/adhesions">Adhésions</a>
            <a href="${pageContext.request.contextPath}/admin/evenements">Événements</a>
            <a href="${pageContext.request.contextPath}/admin/publications">Publications</a>
            <a href="${pageContext.request.contextPath}/admin/documents">Documents</a>
            <a href="${pageContext.request.contextPath}/admin/materiels">Matériel</a>
            <a href="${pageContext.request.contextPath}/admin/module?page=conventions">Conventions</a>
            <a href="${pageContext.request.contextPath}/admin/module?page=reunions">PV &amp; réunions</a>
            <a href="${pageContext.request.contextPath}/admin/rapport">Rapport annuel</a>
        </aside>

        <main>
            <section class="page-head" style="border-radius: 4px;">
                <div class="container">
                    <h1>Bonjour, <c:out value="${sessionScope.utilisateurConnecte.prenom}"/> <c:out value="${sessionScope.utilisateurConnecte.nom}"/>.</h1>
                    <p style="margin-top: 8px;">
                        Statut : 
                        <c:forEach var="r" items="${sessionScope.utilisateurConnecte.roles}">
                            <span class="badge" style="background: #f7ce62; color: #102c46; font-weight: bold; padding: 3px 8px; border-radius: 3px;">
                                <c:out value="${r}"/>
                            </span>
                        </c:forEach>
                        <span style="margin-left: 10px; color: #cbd9e1;">· <c:out value="${sessionScope.utilisateurConnecte.email}"/></span>
                    </p>
                </div>
            </section>

            <div class="panel">
                <h2>Actions rapides</h2>
                <div class="grid">
                    <a class="card" href="${pageContext.request.contextPath}/admin/membres/nouveau">
                        <h3>+ Ajouter un membre</h3>
                        <p>Créer un compte, un profil et une affiliation initiale.</p>
                    </a>
                    <a class="card" href="${pageContext.request.contextPath}/admin/evenements">
                        <h3>Planifier un événement</h3>
                        <p>Préparer une conférence, un séminaire ou un workshop.</p>
                    </a>
                    <a class="card" href="${pageContext.request.contextPath}/admin/rapport">
                        <h3>Préparer le rapport annuel</h3>
                        <p>Consolider les productions et activités du laboratoire.</p>
                    </a>
                </div>
            </div>

            <a class="button outline" style="color: #bb3838; border-color: #bb3838;" href="${pageContext.request.contextPath}/logout">
                Se déconnecter
            </a>
        </main>
    </div>

    <%@ include file="fragments/footer.jspf" %>
</body>
</html>
