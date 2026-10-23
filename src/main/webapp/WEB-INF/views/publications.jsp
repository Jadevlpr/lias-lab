<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Publications scientifiques · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Publications scientifiques</h1>
            <p>Production scientifique, revues, conférences, brevets et mémoires du laboratoire.</p>
        </div>
    </section>

    <main class="container">
        <div class="panel">
            <h2>Ajouter une publication</h2>
            <form action="${pageContext.request.contextPath}/admin/publications" method="post">
                <div class="form-grid">
                    <div class="field full">
                        <label for="titre">Titre de la publication :</label>
                        <input type="text" id="titre" name="titre" placeholder="ex: Deep Neural Architectures for Real-Time Predictive Analysis" required />
                    </div>
                    <div class="field">
                        <label for="type">Type :</label>
                        <select id="type" name="type" required>
                            <option value="Revue Internationale">Revue Internationale</option>
                            <option value="Conférence Internationale">Conférence Internationale</option>
                            <option value="Conférence Nationale">Conférence Nationale</option>
                            <option value="Chapitre d'ouvrage">Chapitre d'ouvrage</option>
                            <option value="Brevet">Brevet</option>
                            <option value="Thèse / HDR">Thèse / HDR</option>
                        </select>
                    </div>
                    <div class="field">
                        <label for="annee">Année de parution :</label>
                        <input type="number" id="annee" name="annee" min="1990" max="2099" value="2026" required />
                    </div>
                    <div class="field">
                        <label for="journalOrConf">Revue / Conférence :</label>
                        <input type="text" id="journalOrConf" name="journalOrConf" placeholder="ex: IEEE Transactions on Software Engineering" />
                    </div>
                    <div class="field">
                        <label for="doi">DOI (optionnel) :</label>
                        <input type="text" id="doi" name="doi" placeholder="ex: 10.1109/TSE.2025.1234567" />
                    </div>
                    <div class="field">
                        <label for="lienPdf">Lien vers le document / PDF :</label>
                        <input type="text" id="lienPdf" name="lienPdf" placeholder="https://... ou /uploads/..." />
                    </div>
                    <div class="field">
                        <label for="bibtex">Notice BibTeX / Résumé :</label>
                        <textarea id="bibtex" name="bibtex" rows="3" placeholder="@article{...} ou résumé"></textarea>
                    </div>
                </div>
                <br>
                <button type="submit" class="button">Enregistrer la publication</button>
            </form>
        </div>

        <div class="panel">
            <h2>Répertoire des publications</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 80px;">Année</th>
                        <th style="width: 160px;">Type</th>
                        <th>Titre &amp; Référence</th>
                        <th>Auteurs</th>
                        <th style="width: 140px;">Liens</th>
                        <th style="width: 120px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="publication" items="${publications}">
                        <tr>
                            <td><strong>${publication.annee}</strong></td>
                            <td><span class="badge">${publication.type}</span></td>
                            <td>
                                <strong>${publication.titre}</strong>
                                <c:if test="${not empty publication.journalOuConference}">
                                    <br><small style="color: var(--muted);">${publication.journalOuConference}</small>
                                </c:if>
                            </td>
                            <td>${not empty publication.listeAuteurs ? publication.listeAuteurs : '<span style="color:var(--muted);">Non spécifié</span>'}</td>
                            <td>
                                <c:if test="${not empty publication.doi}">
                                    <a href="https://doi.org/${publication.doi}" target="_blank" style="color: var(--blue); text-decoration: underline;">DOI</a>
                                </c:if>
                                <c:if test="${not empty publication.lienPdf}">
                                    <c:if test="${not empty publication.doi}"> | </c:if>
                                    <a href="${publication.lienPdf}" target="_blank" style="color: var(--blue); text-decoration: underline;">PDF</a>
                                </c:if>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/publications?action=delete&id=${publication.id}" 
                                   style="color: #bb3838; font-weight: bold;"
                                   onclick="return confirm('Confirmer la suppression de cette publication ?')">Supprimer</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty publications}">
                        <tr>
                            <td colspan="6" style="text-align: center; color: var(--muted); padding: 24px;">
                                Aucune publication enregistrée pour le moment.
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
