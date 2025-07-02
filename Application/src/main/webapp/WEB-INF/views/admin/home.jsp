<%@ include file="includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row justify-content-center align-items-center" style="min-height:60vh;">
            <div class="col-md-8 text-center">
              <h1 class="display-4 mb-4">Bienvenue sur le tableau de bord</h1>
              <p class="lead mb-4">Bonjour, <strong>${acces.nomUtilisateur}</strong> (rôle : <strong>${acces.role}</strong>)</p>
              <div class="row justify-content-center">
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-client" class="btn btn-outline-primary btn-lg w-100">Gérer les clients</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/fournisseurs/liste" class="btn btn-outline-success btn-lg w-100">Gérer les fournisseurs</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-produit" class="btn btn-outline-info btn-lg w-100">Gérer les produits</a>
                </div>
              </div>
              <div class="row justify-content-center">
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-employe" class="btn btn-outline-warning btn-lg w-100">Gérer les employés</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-camion" class="btn btn-outline-secondary btn-lg w-100">Gérer les camions</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/commande" class="btn btn-outline-dark btn-lg w-100">Commandes & Livraisons</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <%@ include file="includes/footer.jsp" %>
      </div>
    </div>
  </div>
</body>
</html>

