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
      <%@ include file="includes/head.jsp" %>
</head>
<body>
<div class="container-scroller">
    <!-- partial:partials/_navbar.html -->
    <%@ include file="includes/navbar.jsp" %>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:partials/_settings-panel.html -->
      <!-- partial -->
      <!-- partial:partials/_sidebar.html -->
      
    <h3>Information pour la livraison</h3>

    <%
        Commande commande = (Commande) request.getAttribute("liste");

        Long idCommande = null;
        String adresse = "";
        String ville = "";
        String telephone = "";
        String email = "";

        if (commande != null) {
            idCommande = commande.getId();
            adresse = commande.getAdresseLivraison() != null ? commande.getAdresseLivraison() : "";
            ville = commande.getVilleLivraison() != null ? commande.getVilleLivraison() : "";
            telephone = commande.getTelephoneLivraison() != null ? commande.getTelephoneLivraison() : "";
            email = commande.getEmailLivraison() != null ? commande.getEmailLivraison() : "";
        }
    %>

    <div style="display: flex; justify-content: left; align-items: center; min-height: 50vh;">

    <form method="post" action="${pageContext.request.contextPath}/Livraison-client/save">
        <% if (idCommande != null) { %>
            <input type="hidden" name="idCommande" value="<%= idCommande %>" style = "width:250px">
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

        <%
            java.time.LocalDate dateLivraison = java.time.LocalDate.now().plusDays(3);
        %>
        <div class="form-group"> 
            <label>Date de livraison :</label>
            <input type="date" name="dateLivraison" class="form-control" required 
                  value="<%= dateLivraison.toString() %>" readonly>
        </div>


        <button type="submit" class="btn btn-primary">Envoyer</button>
    </form>
    </div>
</div>
</div>
      <%@ include file="includes/footer.jsp" %>
        <!-- partial -->
      </div>
      <!-- main-panel ends -->
    </div>   
    <!-- page-body-wrapper ends -->
  </div>
  <!-- container-scroller -->
</body>
</html>
