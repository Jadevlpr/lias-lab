<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Équipes de recherche · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Équipes &amp; mandats</h1>
            <p>Structure de recherche, équipes scientifiques et responsables de structures.</p>
        </div>
    </section>

    <main class="container">
        <div class="panel">
            <h2>Créer une nouvelle équipe</h2>
            <form action="${pageContext.request.contextPath}/admin/equipes" method="post">
                <div class="form-grid">
                    <div class="field">
                        <label for="nom">Nom complet de l'équipe :</label>
                        <input type="text" id="nom" name="nom" placeholder="ex: Systèmes Intelligents & Décision" required />
                    </div>
                    <div class="field">
                        <label for="acronyme">Acronyme :</label>
                        <input type="text" id="acronyme" name="acronyme" placeholder="ex: SID" required />
                    </div>
                    <div class="field full">
                        <label for="description">Description / Axe de recherche :</label>
                        <textarea id="description" name="description" rows="3" placeholder="Description des thématiques et axes de recherche"></textarea>
                    </div>
                </div>
                <br>
                <button type="submit" class="button">Créer l'équipe</button>
            </form>
        </div>

        <div class="panel">
            <h2>Équipes enregistrées</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 80px;">ID</th>
                        <th style="width: 140px;">Acronyme</th>
                        <th>Nom de l'équipe</th>
                        <th>Responsable</th>
                        <th style="width: 120px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equipe" items="${equipes}">
                        <tr>
                            <td>${equipe.id}</td>
                            <td><strong>${equipe.acronyme != null ? equipe.acronyme : '—'}</strong></td>
                            <td>${equipe.nom}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty equipe.chefNomComplet}">
                                        <span class="badge">${equipe.chefNomComplet}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: var(--muted);">Non assigné</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/equipes?action=delete&id=${equipe.id}" 
                                   style="color: #bb3838; font-weight: bold;"
                                   onclick="return confirm('Confirmer la suppression de cette équipe ?')">Supprimer</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty equipes}">
                        <tr>
                            <td colspan="5" style="text-align: center; color: var(--muted); padding: 24px;">
                                Aucune équipe enregistrée pour le moment.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </main>

    <%@ include file="fragments/footer.jspf" %>
</body>
</html>
