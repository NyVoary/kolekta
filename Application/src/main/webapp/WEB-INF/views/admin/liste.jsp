<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.Map, java.util.HashMap" %>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.demo.entity.AssignationTache" %>
<%@ page import="com.example.demo.entity.Acces" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="acces" class="com.example.demo.entity.Acces" scope="session" />

<%@ include file="includes/head.jsp" %>
<link rel="stylesheet" href="/css/barChart.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />

<body>
  <div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
        <%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Liste des paramètres</title></head>
<body>
  <h2>Liste des Paramètres</h2>
  <a href="/parametres/ajout">Ajouter un paramètre</a>
  <table border="1">
    <thead>
      <tr><th>ID</th><th>Libellé</th><th>Valeur</th><th>Actions</th></tr>
    </thead>
    <tbody>
      <c:forEach var="p" items="${parametres}">
        <tr>
          <td>${p.id_parametre}</td>
          <td>${p.libelle}</td>
          <td>${p.valeur}</td>
          <td>
            <a href="/parametres/edit/${p.id_parametre}">Modifier</a> |
            <a href="/parametres/delete/${p.id_parametre}">Supprimer</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
        </div>
        <%@ include file="includes/footer.jsp" %>
      </div>
    </div>
  </div>
</body>
</html>
