<c:forEach var="publication" items="${publications}">
    <tr>
        <td><strong>${publication.annee}</strong></td>
        <td>${publication.type}</td>
        <td>${publication.titre}</td>
        <td>${not empty publication.listeAuteurs ? publication.listeAuteurs : 'N/A'}</td>
        <td>
            <c:if test="${not empty publication.doi}">
                <a href="https://doi.org/${publication.doi}" target="_blank">DOI</a>
            </c:if>
            <c:if test="${not empty publication.lienPdf}">
                | <a href="${publication.lienPdf}" target="_blank">PDF</a>
            </c:if>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/admin/publications?action=delete&id=${publication.id}" 
               onclick="return confirm('Supprimer cette publication ?')">Supprimer</a>
        </td>
    </tr>
</c:forEach>