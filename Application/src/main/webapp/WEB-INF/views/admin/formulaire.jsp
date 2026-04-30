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
<head><title>Formulaire Paramètre</title></head>
<body>
  <h2>Formulaire Paramètre</h2>
  <form action="/parametres/save" method="post">
    <input type="hidden" name="id_parametre" value="${parametre.id_parametre}" />
    Libellé: <input type="text" name="libelle" value="${parametre.libelle}" /><br/>
    Valeur: <input type="number" name="valeur" value="${parametre.valeur}" /><br/>
    <button type="submit">Enregistrer</button>
  </form>
</body>
</html>
        </div>
        <%@ include file="includes/footer.jsp" %>
      </div>
    </div>
  </div>
</body>
</html>
