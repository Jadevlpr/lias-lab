<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIAS - Gestion des Événements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Gestion des Événements du Laboratoire</h2>

        <!-- Formulaire d'ajout d'événement -->
        <div class="card">
            <h3>Ajouter un événement</h3>
            <form action="${pageContext.request.contextPath}/admin/evenements" method="post">
                <div class="form-group">
                    <label>Titre :</label>
                    <input type="text" name="titre" required />
                </div>
                <div class="form-group">
                    <label>Type :</label>
                    <select name="type" required>
                        <option value="Séminaire">Séminaire</option>
                        <option value="Soutenance">Soutenance de thèse</option>
                        <option value="Workshop">Workshop / Atelier</option>
                        <option value="Conférence">Conférence</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Date & Heure de début :</label>
                    <input type="datetime-local" name="dateDebut" required />
                </div>
                <div class="form-group">
                    <label>Date & Heure de fin :</label>
                    <input type="datetime-local" name="dateFin" />
                </div>
                <div class="form-group">
                    <label>Lieu / Salle :</label>
                    <input type="text" name="lieu" placeholder="ex: Amphi 8, Salle de réunion LIAS" />
                </div>
                <div class="form-group">
                    <label>Lien Visioconférence (Optionnel) :</label>
                    <input type="url" name="lienEnLigne" placeholder="https://teams.microsoft.com/..." />
                </div>
                <div class="form-group">
                    <label>Description :</label>
                    <textarea name="description" rows="3"></textarea>
                </div>
                <button type="submit" class="btn">Enregistrer l'événement</button>
            </form>
        </div>

        <!-- Liste des événements -->
        <h3>Événements programmés</h3>
        <table border="1" style="width:100%; text-align:left;">
            <thead>
                <tr>
                    <th>Type</th>
                    <th>Titre</th>
                    <th>Date début</th>
                    <th>Lieu</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="evenement" items="${evenements}">
                    <tr>
                        <td><strong>${evenement.type}</strong></td>
                        <td>${evenement.titre}</td>
                        <td>${evenement.dateDebut}</td>
                        <td>${evenement.lieu}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/evenements?action=delete&id=${evenement.id}" 
                               onclick="return confirm('Supprimer cet événement ?')">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>