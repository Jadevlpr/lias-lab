<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rapport annuel · LIAS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px; margin-top: 20px; }
        .stat-card { background: #fff; border: 1px solid var(--line); border-top: 4px solid var(--gold); padding: 22px; border-radius: 3px; box-shadow: 0 4px 12px rgba(0,0,0,0.04); }
        .stat-card h4 { margin: 0; color: var(--navy); font-size: 15px; }
        .stat-card .number { font-size: 2.4em; font-weight: bold; color: var(--blue); margin-top: 12px; }
        @media print {
            .site-header, .footer, .no-print { display: none !important; }
            body { background: #fff; }
            .panel { border: none; box-shadow: none; padding: 0; }
        }
    </style>
</head>
<body>
    <%@ include file="fragments/header.jspf" %>

    <section class="page-head">
        <div class="container">
            <h1>Bilan d'activité &amp; Rapport annuel</h1>
            <p>Consolidation annuelle des activités scientifiques, des effectifs et des moyens du laboratoire.</p>
        </div>
    </section>

    <main class="container">
        <div class="panel no-print">
            <h2>Sélection de l'exercice</h2>
            <form action="${pageContext.request.contextPath}/admin/rapport" method="get" style="display: flex; gap: 14px; align-items: flex-end; flex-wrap: wrap;">
                <div style="flex: 1; min-width: 200px;">
                    <label for="annee">Année du bilan :</label>
                    <input type="number" id="annee" name="annee" value="${anneeSelectionnee}" min="2020" max="2099" required />
                </div>
                <button type="submit" class="button">Générer le rapport</button>
                <button type="button" onclick="window.print()" class="button outline" style="background-color: #28a745; border-color: #28a745; color: #fff;">Imprimer / Exporter PDF</button>
            </form>
        </div>

        <div class="panel">
            <h2>Synthèse d'activité pour l'année ${anneeSelectionnee}</h2>
            <p style="color: var(--muted); margin-bottom: 24px;">
                Indicateurs clés consolidés sur l'ensemble des départements et équipes du laboratoire LIAS.
            </p>

            <div class="stats-grid">
                <div class="stat-card">
                    <h4>Membres actifs</h4>
                    <div class="number">${stats.totalMembres}</div>
                    <small style="color: var(--muted);">Chercheurs, enseignants &amp; doctorants</small>
                </div>
                <div class="stat-card">
                    <h4>Équipes de recherche</h4>
                    <div class="number">${stats.totalEquipes}</div>
                    <small style="color: var(--muted);">Pôles scientifiques structurés</small>
                </div>
                <div class="stat-card">
                    <h4>Publications scientifiques</h4>
                    <div class="number">${stats.totalPublications}</div>
                    <small style="color: var(--muted);">Articles et actes en ${anneeSelectionnee}</small>
                </div>
                <div class="stat-card">
                    <h4>Événements organisés</h4>
                    <div class="number">${stats.totalEvenements}</div>
                    <small style="color: var(--muted);">Conférences, workshops &amp; séminaires</small>
                </div>
                <div class="stat-card">
                    <h4>Équipements répertoriés</h4>
                    <div class="number">${stats.totalMateriels}</div>
                    <small style="color: var(--muted);">Ressources matérielles suivies</small>
                </div>
            </div>
        </div>
    </main>

    <%@ include file="fragments/footer.jspf" %>
</body>
</html>
