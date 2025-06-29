<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" />
</head>

<body>
    <%-- <%@ include file="./inc/css.jsp"  %>
    <%@ include file="./inc/header.jsp" %> --%>
    <%@ include file="includes/navbar.jsp" %>
    <div class="section" style="margin-top: 10vh;background-color:rgb(86, 135, 185); padding: 20px;">
        <h1>Produits du moment:</h1>
        <ul>
            <c:forEach var="produit" items="${produitsSaison}">
                <li>${produit.nom}</li>
            </c:forEach>
        </ul>
    </div>
    <%@ include file="includes/footer.jsp" %>
    <%-- <%@ include file="./inc/footer.jsp"%>
    <%@ include file="./inc/js.jsp"  %> --%>
    <script src="${pageContext.request.contextPath}/vendors/js/vendor.bundle.base.js"></script>
    <!-- endinject -->
    <!-- Plugin js for this page -->
    <!-- End plugin js for this page -->
    <!-- inject:js -->
    <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
    <script src="${pageContext.request.contextPath}/js/hoverable-collapse.js"></script>
    <script src="${pageContext.request.contextPath}/js/template.js"></script>
    <script src="${pageContext.request.contextPath}/js/settings.js"></script>
    <script src="${pageContext.request.contextPath}/js/todolist.js"></script>
    <!-- endinject -->
</body>
</html>