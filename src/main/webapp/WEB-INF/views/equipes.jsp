<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIAS - Gestion des Équipes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Gestion des Équipes de Recherche</h2>

        <div class="card">
            <h3>Ajouter une équipe</h3>
            <form action="${pageContext.request.contextPath}/admin/equipes" method="post">
                <div class="form-group">
                    <label>Nom de l'équipe :</label>
                    <input type="text" name="nom" required />
                </div>
                <div class="form-group">
                    <label>Acronyme :</label>
                    <input type="text" name="acronyme" required />
                </div>
                <div class="form-group">
                    <label>Description :</label>
                    <textarea name="description" rows="3"></textarea>
                </div>
                <button type="submit" class="btn">Créer l'équipe</button>
            </form>
        </div>

        <h3>Liste des Équipes</h3>
        <table border="1" style="width:100%; text-align:left;">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Acronyme</th>
                    <th>Nom</th>
                    <th>Responsable</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="equipe" items="${equipes}">
                    <tr>
                        <td>${equipe.id}</td>
                        <td><strong>${equipe.acronyme}</strong></td>
                        <td>${equipe.nom}</td>
                        <td>${equipe.chefNomComplet != null ? equipe.chefNomComplet : 'Non assigné'}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/equipes?action=delete&id=${equipe.id}" 
                               onclick="return confirm('Confirmer la suppression ?')">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>