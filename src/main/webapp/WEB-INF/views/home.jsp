<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LIAS · Laboratoire</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="fragments/header.jspf" %>
    <main>
        <section class="hero">
            <div class="container">
                <div class="eyebrow">Recherche · Innovation · Collaboration</div>
                <h1>Le laboratoire LIAS, au cœur de la recherche scientifique.</h1>
                <p>Une plateforme institutionnelle pour valoriser les équipes, activités, publications et collaborations du laboratoire.</p>
                <div style="display: flex; gap: 12px; flex-wrap: wrap; margin-top: 20px;">
                    <a class="button" href="${pageContext.request.contextPath}/adhesion">Rejoindre le laboratoire (Adhésion)</a>
                    <a class="button outline" href="${pageContext.request.contextPath}/login">Espace membre</a>
                    <a class="button outline" href="#equipes">Découvrir les équipes</a>
                </div>
            </div>
        </section>

        <section class="section">
            <div class="container">
                <h2 class="section-title">Actualités du laboratoire</h2>
                <p class="section-intro">Les activités scientifiques, événements et appels à collaboration sont rassemblés dans un espace unique et structuré.</p>
                <div class="grid">
                    <article class="card">
                        <h3>Événements scientifiques</h3>
                        <p>Conférences, séminaires et workshops organisés ou soutenus par le laboratoire.</p>
                    </article>
                    <article class="card">
                        <h3>Publications</h3>
                        <p>Une mémoire scientifique consultable par année, auteur et équipe.</p>
                    </article>
                    <article class="card">
                        <h3>Collaborations</h3>
                        <p>Partenariats académiques, industriels et conventions suivis dans le temps.</p>
                    </article>
                </div>
            </div>
        </section>

        <section class="band">
            <div class="container">
                <div class="stats">
                    <div class="stat"><strong>3</strong><span>pôles de recherche</span></div>
                    <div class="stat"><strong>1</strong><span>mémoire institutionnelle</span></div>
                    <div class="stat"><strong>∞</strong><span>historique conservé</span></div>
                    <div class="stat"><strong>100%</strong><span>traçabilité des décisions</span></div>
                </div>
            </div>
        </section>

        <section class="section" id="equipes">
            <div class="container">
                <h2 class="section-title">Équipes &amp; axes de recherche</h2>
                <div class="grid">
                    <article class="card">
                        <h3>Data &amp; Intelligence</h3>
                        <p>Données, systèmes intelligents et aide à la décision.</p>
                    </article>
                    <article class="card">
                        <h3>Systèmes &amp; Automatique</h3>
                        <p>Modélisation, commande et systèmes complexes.</p>
                    </article>
                    <article class="card">
                        <h3>Innovation &amp; partenariats</h3>
                        <p>Valorisation et collaborations avec l'environnement socio-économique.</p>
                    </article>
                </div>
            </div>
        </section>
    </main>
    <%@ include file="fragments/footer.jspf" %>
</body>
</html>
