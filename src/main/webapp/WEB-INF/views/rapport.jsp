<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIAS - Bilan &amp; Rapport Annuel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; margin-top: 20px; }
        .stat-card { background: #f4f6f9; border-left: 5px solid #0056b3; padding: 15px; border-radius: 4px; }
        .stat-card h4 { margin: 0; color: #555; }
        .stat-card .number { font-size: 2em; font-weight: bold; color: #0056b3; margin-top: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Bilan d'Activité &amp; Rapport Annuel</h2>

        <!-- Filtre par année -->
        <div class="card">
            <form action="${pageContext.request.contextPath}/admin/rapport" method="get">
                <label>Sélectionner l'année du bilan :</label>
                <input type="number" name="annee" value="${anneeSelectionnee}" min="2020" max="2099" required />
                <button type="submit" class="btn">Générer le rapport</button>
                <button type="button" onclick="window.print()" class="btn" style="background-color: #28a745;">Imprimer / Exporter PDF</button>
            </form>
        </div>

        <!-- Cartes statistiques -->
        <h3>Statistiques globales pour l'année ${anneeSelectionnee}</h3>
        <div class="stats-grid">
            <div class="stat-card">
                <h4>Membres du Laboratoire</h4>
                <div class="number">${stats.totalMembres}</div>
            </div>
            <div class="stat-card">
                <h4>Équipes de Recherche</h4>
                <div class="number">${stats.totalEquipes}</div>
            </div>
            <div class="stat-card">
                <h4>Publications Scientifiques</h4>
                <div class="number">${stats.totalPublications}</div>
            </div>
            <div class="stat-card">
                <h4>Événements / Séminaires</h4>
                <div class="number">${stats.totalEvenements}</div>
            </div>
            <div class="stat-card">
                <h4>Équipements Répertoriés</h4>
                <div class="number">${stats.totalMateriels}</div>
            </div>
        </div>
    </div>
</body>
</html>