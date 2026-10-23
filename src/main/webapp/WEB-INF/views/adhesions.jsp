<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demandes d'adhésion · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Demandes d'adhésion</h1>
            <p>Instruction et décisions des candidatures déposées en ligne (Direction du laboratoire).</p>
        </div>
    </section>

    <main class="container">
        <c:if test="${not empty sessionScope.succes}">
            <div class="alert" style="background: #e8f5e9; border-left: 5px solid #28a745; color: #155724; padding: 14px; margin: 20px 0;">
                <c:out value="${sessionScope.succes}"/>
            </div>
            <c:remove var="succes" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.erreur}">
            <div class="alert error" style="margin: 20px 0;">
                <c:out value="${sessionScope.erreur}"/>
            </div>
            <c:remove var="erreur" scope="session"/>
        </c:if>

        <div class="panel">
            <h2>Liste des candidatures reçues</h2>
            <p style="color: var(--muted); margin-bottom: 20px;">
                Conformément au cahier des charges, l'acceptation d'une demande crée automatiquement le compte utilisateur et le profil membre associé.
            </p>

            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 140px;">Date demande</th>
                        <th>Candidat</th>
                        <th>Établissement</th>
                        <th>Motivation</th>
                        <th style="width: 90px;">CV</th>
                        <th style="width: 110px;">Statut</th>
                        <th style="width: 200px;">Actions / Décision</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="d" items="${demandes}">
                        <tr>
                            <td><small>${d.dateDemande}</small></td>
                            <td>
                                <strong>${d.prenom} ${d.nom}</strong>
                                <br><small style="color: var(--muted);">${d.email}</small>
                            </td>
                            <td>${not empty d.etablissement ? d.etablissement : '—'}</td>
                            <td>
                                <div style="max-height: 80px; overflow-y: auto; font-size: 13px; color: #333;">
                                    ${d.motivation}
                                </div>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty d.cvPath}">
                                        <a href="${pageContext.request.contextPath}${d.cvPath}" target="_blank" 
                                           style="color: var(--blue); text-decoration: underline; font-weight: bold;">Voir CV</a>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: var(--muted);">—</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${d.statut == 'EN_ATTENTE'}">
                                        <span class="badge" style="background: #fff3cd; color: #856404;">En attente</span>
                                    </c:when>
                                    <c:when test="${d.statut == 'ACCEPTEE'}">
                                        <span class="badge" style="background: #d4edda; color: #155724;">Acceptée</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge" style="background: #f8d7da; color: #721c24;">Refusée</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${d.statut == 'EN_ATTENTE'}">
                                        <div style="display: flex; gap: 8px;">
                                            <form action="${pageContext.request.contextPath}/admin/adhesions" method="post" style="margin:0;">
                                                <input type="hidden" name="id" value="${d.id}">
                                                <input type="hidden" name="action" value="accepter">
                                                <button type="submit" class="button" style="padding: 6px 12px; font-size: 13px; background: #28a745; color:#fff;"
                                                        onclick="return confirm('Valider cette adhésion et créer le compte membre ?')">
                                                    Accepter
                                                </button>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/admin/adhesions" method="post" style="margin:0;">
                                                <input type="hidden" name="id" value="${d.id}">
                                                <input type="hidden" name="action" value="refuser">
                                                <button type="submit" class="button outline" style="padding: 6px 12px; font-size: 13px; border-color: #bb3838; color: #bb3838;"
                                                        onclick="return confirm('Refuser cette demande ?')">
                                                    Refuser
                                                </button>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <small style="color: var(--muted);">
                                            Décision prise 
                                            <c:if test="${not empty d.decisionParNom}">par ${d.decisionParNom}</c:if>
                                            <br>le ${d.dateDecision}
                                        </small>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty demandes}">
                        <tr>
                            <td colspan="7" style="text-align: center; color: var(--muted); padding: 24px;">
                                Aucune demande d'adhésion enregistrée pour le moment.
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
