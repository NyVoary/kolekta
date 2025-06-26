<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.Livraison" %>
<%@ page import="com.example.demo.entity.Commande" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Ajouter une livraison</title>

  <!-- CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" />
</head>
<body>
<div class="container mt-5">
    <h3>Ajouter une livraison</h3>

    <%
        Commande liste = (Commande) request.getAttribute("liste");

        Long idCommande = null;
        String adresse = "";
        String ville = "";
        String telephone = "";
        String email = "";

       
        if (liste != null) {
            idCommande = liste.getId();
            adresse = liste.getAdresseLivraison() != null ? liste.getAdresseLivraison() : "";
            ville = liste.getVilleLivraison() != null ? liste.getVilleLivraison() : "";
            telephone = liste.getTelephoneLivraison() != null ? liste.getTelephoneLivraison() : "";
            email = liste.getEmailLivraison() != null ? liste.getEmailLivraison() : "";
        }
    %>

    <form method="post" action="${pageContext.request.contextPath}/Livraison-client/save">
        <% if (idCommande != null) { %>
            <input type="hidden" name="idCommande" value="<%= idCommande %>">
        <% } %>

        <div class="form-group">
            <label>Adresse de livraison :</label>
            <input type="text" name="adresseLivraison" value="<%= adresse %>" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Ville :</label>
            <input type="text" name="villeLivraison" value="<%= ville %>" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Téléphone :</label>
            <input type="text" name="telephoneLivraison" value="<%= telephone %>" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Email :</label>
            <input type="email" name="emailLivraison" value="<%= email %>" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Date de livraison :</label>
            <input type="date" name="dateLivraison" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary">Envoyer</button>
    </form>
</div>
</body>
</html>
