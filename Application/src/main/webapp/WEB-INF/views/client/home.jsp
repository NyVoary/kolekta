<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
</head>
<body>
    <%@ include file="./inc/css.jsp"  %>
    <%@ include file="./inc/header.jsp" %>

    <div class="section">
        <h1>Section</h1>
    </div>

    <ul>
        <c:forEach var="produit" items="${produitsSaison}">
            <li>${produit.nom}</li>
        </c:forEach>
    </ul>

    <%@ include file="./inc/footer.jsp"%>
    <%@ include file="./inc/js.jsp"  %>
</body>
</html>