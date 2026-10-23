<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Événements scientifiques · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Événements scientifiques</h1>
            <p>Conférences, séminaires, soutenances de thèse, workshops et calendrier du laboratoire.</p>
        </div>
    </section>

    <main class="container">
        <div class="panel">
            <h2>Planifier un événement</h2>
            <form action="${pageContext.request.contextPath}/admin/evenements" method="post">
                <div class="form-grid">
                    <div class="field">
                        <label for="titre">Titre de l'événement :</label>
                        <input type="text" id="titre" name="titre" placeholder="ex: Séminaire sur le Deep Learning" required />
                    </div>
                    <div class="field">
                        <label for="type">Type d'événement :</label>
                        <select id="type" name="type" required>
                            <option value="SEMINAIRE">Séminaire</option>
                            <option value="CONFERENCE">Conférence</option>
                            <option value="WORKSHOP">Workshop / Atelier</option>
                            <option value="REUNION">Réunion</option>
                            <option value="AUTRE">Autre événement</option>
                        </select>
                    </div>
                    <div class="field">
                        <label for="dateDebut">Date &amp; heure de début :</label>
                        <input type="datetime-local" id="dateDebut" name="dateDebut" required />
                    </div>
                    <div class="field">
                        <label for="dateFin">Date &amp; heure de fin (optionnel) :</label>
                        <input type="datetime-local" id="dateFin" name="dateFin" />
                    </div>
                    <div class="field">
                        <label for="lieu">Lieu / Salle :</label>
                        <input type="text" id="lieu" name="lieu" placeholder="ex: Salle de conférence, FSBM" />
                    </div>
                    <div class="field">
                        <label for="lienEnLigne">Lien de visioconférence (optionnel) :</label>
                        <input type="url" id="lienEnLigne" name="lienEnLigne" placeholder="https://teams.microsoft.com/..." />
                    </div>
                    <div class="field full">
                        <label for="description">Description :</label>
                        <textarea id="description" name="description" rows="3" placeholder="Programme, intervenants, résumé de la rencontre..."></textarea>
                    </div>
                </div>
                <br>
                <button type="submit" class="button">Enregistrer l'événement</button>
            </form>
        </div>

        <div class="panel">
            <h2>Événements programmés</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 140px;">Type</th>
                        <th>Titre de l'événement</th>
                        <th style="width: 180px;">Date &amp; heure</th>
                        <th>Lieu / Lien</th>
                        <th style="width: 120px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ev" items="${evenements}">
                        <tr>
                            <td><span class="badge">${ev.type}</span></td>
                            <td><strong>${ev.titre}</strong></td>
                            <td>${ev.dateDebut}</td>
                            <td>
                                ${not empty ev.lieu ? ev.lieu : 'En ligne'}
                                <c:if test="${not empty ev.lienEnLigne}">
                                    <br><a href="${ev.lienEnLigne}" target="_blank" style="color: var(--blue); text-decoration: underline; font-size: 13px;">Lien d'accès</a>
                                </c:if>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/evenements?action=delete&id=${ev.id}" 
                                   style="color: #bb3838; font-weight: bold;"
                                   onclick="return confirm('Confirmer la suppression de cet événement ?')">Supprimer</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty evenements}">
                        <tr>
                            <td colspan="5" style="text-align: center; color: var(--muted); padding: 24px;">
                                Aucun événement programmé pour le moment.
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
