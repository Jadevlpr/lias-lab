<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Documents &amp; PV · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Documents &amp; Procès-Verbaux</h1>
            <p>Archives documentaires, règlements, procès-verbaux de réunions et attestations administratives.</p>
        </div>
    </section>

    <main class="container">
        <div class="panel">
            <h2>Ajouter un document ou PV</h2>
            <form action="${pageContext.request.contextPath}/admin/documents" method="post">
                <div class="form-grid">
                    <div class="field">
                        <label for="titre">Titre du document :</label>
                        <input type="text" id="titre" name="titre" placeholder="ex: PV de réunion de rentrée 2026" required />
                    </div>
                    <div class="field">
                        <label for="categorie">Catégorie :</label>
                        <select id="categorie" name="categorie" required>
                            <option value="PV">Procès-Verbal (PV)</option>
                            <option value="ADMINISTRATIF">Règlement / Administratif</option>
                            <option value="RAPPORT">Rapport d'activité</option>
                            <option value="FINANCEMENT">Financement / Budget</option>
                            <option value="PROGRAMME">Programme scientifique</option>
                            <option value="CONVENTION">Convention</option>
                            <option value="ATTESTATION">Attestation</option>
                            <option value="AUTRE">Autre document</option>
                        </select>
                    </div>
                    <div class="field full">
                        <label for="cheminFichier">Chemin ou lien vers le fichier :</label>
                        <input type="text" id="cheminFichier" name="cheminFichier" placeholder="/uploads/documents/pv-2026.pdf" required />
                    </div>
                </div>
                <br>
                <button type="submit" class="button">Enregistrer le document</button>
            </form>
        </div>

        <div class="panel">
            <h2>Documents disponibles</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 160px;">Catégorie</th>
                        <th>Titre du document</th>
                        <th style="width: 170px;">Date d'ajout</th>
                        <th style="width: 180px;">Ajouté par</th>
                        <th style="width: 120px;">Fichier</th>
                        <th style="width: 110px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="doc" items="${documents}">
                        <tr>
                            <td><span class="badge">${doc.categorie}</span></td>
                            <td><strong>${doc.titre}</strong></td>
                            <td>${doc.dateAjout}</td>
                            <td>${not empty doc.ajouteParNom ? doc.ajouteParNom : 'Système'}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}${doc.cheminFichier}" target="_blank" 
                                   style="color: var(--blue); text-decoration: underline;">Consulter</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/documents?action=delete&id=${doc.id}" 
                                   style="color: #bb3838; font-weight: bold;"
                                   onclick="return confirm('Confirmer la suppression de ce document ?')">Supprimer</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty documents}">
                        <tr>
                            <td colspan="6" style="text-align: center; color: var(--muted); padding: 24px;">
                                Aucun document enregistré pour le moment.
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
