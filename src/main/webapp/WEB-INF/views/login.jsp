<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <main class="container section" style="max-width: 600px; margin: 40px auto;">
        <div class="panel">
            <div class="eyebrow" style="color: var(--blue);">Espace sécurisé</div>
            <h1>Connexion membre</h1>
            <p class="section-intro">Accédez aux outils administratifs et scientifiques du laboratoire.</p>

            <c:if test="${not empty erreur}">
                <div class="alert error"><c:out value="${erreur}"/></div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/login">
                <div class="field">
                    <label for="email">E-mail</label>
                    <input id="email" type="email" name="email" placeholder="votre.email@fsbm.ma" required>
                </div>
                <br>
                <div class="field">
                    <label for="motDePasse">Mot de passe</label>
                    <input id="motDePasse" type="password" name="motDePasse" placeholder="••••••••" required>
                </div>
                <br>
                <button class="button" type="submit" style="width: 100%; text-align: center;">Se connecter</button>
            </form>

            <hr style="margin: 25px 0; border: 0; border-top: 1px solid var(--line);">

            <div style="text-align: center;">
                <p style="color: var(--muted); margin-bottom: 12px; font-size: 14px;">
                    Vous êtes chercheur ou doctorant et n'avez pas encore de compte ?
                </p>
                <a class="button outline" style="color: var(--navy); border-color: var(--navy); display: inline-block;" 
                   href="${pageContext.request.contextPath}/adhesion">
                    Déposer une demande d'adhésion / Inscription
                </a>
            </div>
        </div>
    </main>

    <%@ include file="fragments/footer.jspf" %>
</body>
</html>
