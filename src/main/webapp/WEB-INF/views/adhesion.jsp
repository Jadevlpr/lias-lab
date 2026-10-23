<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demande d'adhésion · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Rejoindre le laboratoire LIAS</h1>
            <p>Formulaire d'inscription et de demande d'adhésion pour enseignants-chercheurs, collaborateurs et doctorants.</p>
        </div>
    </section>

    <main class="container" style="max-width: 850px; margin: 30px auto;">
        <c:if test="${not empty succes}">
            <div class="alert" style="background: #e8f5e9; border-left: 5px solid #28a745; color: #155724; padding: 18px; margin-bottom: 25px;">
                <h3 style="margin-top: 0; color: #155724;">Candidature transmise avec succès !</h3>
                <p style="margin-bottom: 0;">${succes}</p>
                <br>
                <a class="button" href="${pageContext.request.contextPath}/home">Retour à l'accueil</a>
            </div>
        </c:if>

        <c:if test="${not empty erreur}">
            <div class="alert error" style="margin-bottom: 25px;">
                ${erreur}
            </div>
        </c:if>

        <c:if test="${empty succes}">
            <div class="panel">
                <div class="eyebrow" style="color: var(--blue);">Candidature en ligne</div>
                <h2>Formulaire d'adhésion</h2>
                <p style="color: var(--muted); margin-bottom: 25px;">
                    Conformément aux règles du laboratoire, toute demande d'adhésion est soumise à l'instruction et à la validation du Directeur en mandat.
                </p>

                <form action="${pageContext.request.contextPath}/adhesion" method="post" enctype="multipart/form-data">
                    <div class="form-grid">
                        <div class="field">
                            <label for="nom">Nom : <span style="color:#bb3838;">*</span></label>
                            <input type="text" id="nom" name="nom" placeholder="ex: El Idrissi" required />
                        </div>
                        <div class="field">
                            <label for="prenom">Prénom : <span style="color:#bb3838;">*</span></label>
                            <input type="text" id="prenom" name="prenom" placeholder="ex: Yassine" required />
                        </div>
                        <div class="field full">
                            <label for="email">Adresse E-mail : <span style="color:#bb3838;">*</span></label>
                            <input type="email" id="email" name="email" placeholder="ex: yassine.elidrissi@univ.ma" required />
                        </div>
                        <div class="field full">
                            <label for="etablissement">Établissement / Université d'origine :</label>
                            <input type="text" id="etablissement" name="etablissement" placeholder="ex: Faculté des Sciences Ben M'Sik, Université Hassan II" />
                        </div>
                        <div class="field full">
                            <label for="motivation">Lettre de motivation &amp; Thématiques de recherche : <span style="color:#bb3838;">*</span></label>
                            <textarea id="motivation" name="motivation" rows="4" placeholder="Présentez brièvement vos axes de recherche, vos motivations à intégrer le laboratoire LIAS et vos travaux passés..." required></textarea>
                        </div>
                        <div class="field">
                            <label for="cvFile">Curriculum Vitae (Fichier PDF) :</label>
                            <input type="file" id="cvFile" name="cvFile" accept=".pdf,.doc,.docx" />
                        </div>
                        <div class="field">
                            <label for="cvLien">Ou lien en ligne vers votre CV / profil LinkedIn :</label>
                            <input type="text" id="cvLien" name="cvLien" placeholder="https://linkedin.com/in/... ou Google Scholar" />
                        </div>
                    </div>
                    <br>
                    <button type="submit" class="button" style="width: 100%; text-align: center; font-size: 16px;">
                        Envoyer ma demande d'adhésion
                    </button>
                </form>
            </div>
        </c:if>
    </main>

    <%@ include file="fragments/footer.jspf" %>
</body>
</html>
