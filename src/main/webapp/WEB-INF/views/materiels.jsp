<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Matériel &amp; Équipements · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Inventaire du matériel</h1>
            <p>Parc informatique, stations de travail, serveurs de calcul, capteurs et traçabilité des équipements.</p>
        </div>
    </section>

    <main class="container">
        <div class="panel">
            <h2>Ajouter un équipement</h2>
            <form action="${pageContext.request.contextPath}/admin/materiels" method="post">
                <div class="form-grid">
                    <div class="field">
                        <label for="nom">Nom de l'équipement :</label>
                        <input type="text" id="nom" name="nom" placeholder="ex: Serveur GPU Dell PowerEdge" required />
                    </div>
                    <div class="field">
                        <label for="numeroSerie">N° de série / Référence :</label>
                        <input type="text" id="numeroSerie" name="numeroSerie" placeholder="ex: SN-2026-GPU-01" required />
                    </div>
                    <div class="field">
                        <label for="categorie">Catégorie :</label>
                        <select id="categorie" name="categorie" required>
                            <option value="Serveur">Serveur / Nœud de calcul</option>
                            <option value="PC">PC / Station de travail</option>
                            <option value="Capteur">Capteur / Matériel embarqué</option>
                            <option value="Réseau">Équipement Réseau</option>
                            <option value="Autre">Autre équipement</option>
                        </select>
                    </div>
                    <div class="field">
                        <label for="etat">État :</label>
                        <select id="etat" name="etat" required>
                            <option value="Disponible">Disponible</option>
                            <option value="Emprunté">Emprunté</option>
                            <option value="En maintenance">En maintenance</option>
                            <option value="Réformé">Réformé</option>
                        </select>
                    </div>
                    <div class="field full">
                        <label for="localisation">Localisation / Emplacement :</label>
                        <input type="text" id="localisation" name="localisation" placeholder="ex: Salle serveurs LIAS, Salle 102" required />
                    </div>
                </div>
                <br>
                <button type="submit" class="button">Enregistrer l'équipement</button>
            </form>
        </div>

        <div class="panel">
            <h2>Équipements répertoriés</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 140px;">Catégorie</th>
                        <th>Nom du matériel</th>
                        <th style="width: 160px;">N° de série</th>
                        <th style="width: 130px;">État</th>
                        <th>Localisation</th>
                        <th style="width: 110px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="mat" items="${materiels}">
                        <tr>
                            <td><span class="badge">${mat.categorie}</span></td>
                            <td><strong>${mat.nom}</strong></td>
                            <td>${mat.numeroSerie}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${mat.etat == 'Disponible'}">
                                        <span style="color: #28a745; font-weight: bold;">Disponible</span>
                                    </c:when>
                                    <c:when test="${mat.etat == 'Emprunté'}">
                                        <span style="color: #d8a62a; font-weight: bold;">Emprunté</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #bb3838; font-weight: bold;">${mat.etat}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${mat.localisation}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/materiels?action=delete&id=${mat.id}" 
                                   style="color: #bb3838; font-weight: bold;"
                                   onclick="return confirm('Confirmer la suppression de ce matériel ?')">Supprimer</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty materiels}">
                        <tr>
                            <td colspan="6" style="text-align: center; color: var(--muted); padding: 24px;">
                                Aucun équipement répertorié pour le moment.
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
