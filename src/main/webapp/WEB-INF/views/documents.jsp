<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIAS - Gestion des Documents &amp; PV</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Gestion Électronique des Documents &amp; Procès-Verbaux</h2>

        <!-- Formulaire d'ajout -->
        <div class="card">
            <h3>Ajouter un document</h3>
            <form action="${pageContext.request.contextPath}/admin/documents" method="post">
                <div class="form-group">
                    <label>Titre du document :</label>
                    <input type="text" name="titre" required />
                </div>
                <div class="form-group">
                    <label>Catégorie :</label>
                    <select name="categorie" required>
                        <option value="PV de réunion">PV de réunion</option>
                        <option value="Règlement intérieur">Règlement intérieur</option>
                        <option value="Formulaire / Modèle">Formulaire / Modèle</option>
                        <option value="Rapport d'activité">Rapport d'activité</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Lien ou chemin du fichier :</label>
                    <input type="text" name="cheminFichier" placeholder="/uploads/documents/pv-2026.pdf" required />
                </div>
                <button type="submit" class="btn">Enregistrer le document</button>
            </form>
        </div>

        <!-- Liste des documents -->
        <h3>Documents disponibles</h3>
        <table border="1" style="width:100%; text-align:left;">
            <thead>
                <tr>
                    <th>Catégorie</th>
                    <th>Titre</th>
                    <th>Date d'ajout</th>
                    <th>Ajouté par</th>
                    <th>Fichier</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="doc" items="${documents}">
                    <tr>
                        <td><strong>${doc.categorie}</strong></td>
                        <td>${doc.titre}</td>
                        <td>${doc.dateAjout}</td>
                        <td>${not empty doc.ajouteParNom ? doc.ajouteParNom : 'Système'}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}${doc.cheminFichier}" target="_blank">Consulter</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/documents?action=delete&id=${doc.id}" 
                               onclick="return confirm('Supprimer ce document ?')">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>