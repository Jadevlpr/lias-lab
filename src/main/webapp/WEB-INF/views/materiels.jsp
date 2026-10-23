<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIAS - Gestion du Matériel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Inventaire du Matériel du Laboratoire</h2>

        <!-- Formulaire d'ajout -->
        <div class="card">
            <h3>Ajouter un équipement</h3>
            <form action="${pageContext.request.contextPath}/admin/materiels" method="post">
                <div class="form-group">
                    <label>Nom du matériel :</label>
                    <input type="text" name="nom" required />
                </div>
                <div class="form-group">
                    <label>N° de série / Tag :</label>
                    <input type="text" name="numeroSerie" required />
                </div>
                <div class="form-group">
                    <label>Catégorie :</label>
                    <select name="categorie" required>
                        <option value="Serveur">Serveur / Nœud de calcul</option>
                        <option value="PC">PC / Station de travail</option>
                        <option value="Capteur">Capteur / Matériel embarqué</option>
                        <option value="Réseau">Équipement Réseau</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>État :</label>
                    <select name="etat" required>
                        <option value="Disponible">Disponible</option>
                        <option value="Emprunté">Emprunté</option>
                        <option value="En maintenance">En maintenance</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Localisation / Bureau :</label>
                    <input type="text" name="localisation" placeholder="ex: Salle Serveurs, Labo 102" required />
                </div>
                <button type="submit" class="btn">Enregistrer l'équipement</button>
            </form>
        </div>

        <!-- Liste des équipements -->
        <h3>Équipements répertoriés</h3>
        <table border="1" style="width:100%; text-align:left;">
            <thead>
                <tr>
                    <th>Catégorie</th>
                    <th>Nom</th>
                    <th>N° Série</th>
                    <th>État</th>
                    <th>Localisation</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="mat" items="${materiels}">
                    <tr>
                        <td><strong>${mat.categorie}</strong></td>
                        <td>${mat.nom}</td>
                        <td>${mat.numeroSerie}</td>
                        <td>${mat.etat}</td>
                        <td>${mat.localisation}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/materiels?action=delete&id=${mat.id}" 
                               onclick="return confirm('Supprimer ce matériel ?')">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>