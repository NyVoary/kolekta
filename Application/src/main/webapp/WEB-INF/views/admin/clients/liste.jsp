<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row mb-4">
            <div class="col-12 text-center">
              <h2 class="font-weight-bold mb-3">Liste des Clients</h2>
              <a href="/admin/clients/ajout-client" class="btn btn-primary mb-3">Ajouter un client</a>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <div class="card shadow-sm">
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-striped table-hover">
                      <thead class="thead-dark">
                        <tr>
                          <th>#</th>
                          <th>Nom</th>
                          <th>Email</th>
                          <th>Téléphone</th>
                          <th>Actions</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="client" items="${clients}" varStatus="status">
                          <tr>
                            <td>${status.index + 1}</td>
                            <td>${client.nom}</td>
                            <td>${client.email}</td>
                            <td>${client.telephone}</td>
                            <td>
                              <a href="/admin/clients/edit?id=${client.id}" class="btn btn-sm btn-warning">Éditer</a>
                              <a href="/admin/clients/delete?id=${client.id}" class="btn btn-sm btn-danger" onclick="return confirm('Supprimer ce client ?');">Supprimer</a>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>
  </div>
</body>
</html>

