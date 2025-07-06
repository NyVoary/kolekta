<%@ include file="../includes/head.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.Client" %>

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
          <%-- <div class="row">
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
      <%@ include file="../includes/sidebar.jsp" %> --%>
      <!-- partial -->
      <div class="main-panel">
        <%-- <div class="main-panel"> --%>
                        <c:if test="${not empty succes}">
                    <div style="color: green;">${succes}</div>
                </c:if>
          <div class="content-wrapper">
            <h2>Liste des Clients</h2>
        
            <div class="table-responsive">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom complet</th>
                    <th>Date de naissance</th>
                    <th>Sexe</th>
                    <th>Email</th>
                    <th>T&eacute;l&eacute;phone</th>
                    <th>Adresse</th>
                    <th>Image</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <%
                    List<Client> clients = (List<Client>) request.getAttribute("clients");
                    if (clients != null && !clients.isEmpty()) {
                      for (Client client : clients) {
                  %>
                  <tr>
                    <td><a href="/admin/informations-client/<%= client.getIdClient() %>" class="btn btn-sm btn-primary"><%= client.getIdClient() %></a></td>
                    <td><%= client.getNom() %> <%= client.getPrenoms() %></td>
                    <td><%= client.getDtn() %></td>
                    <td><%= client.getSexe() %></td>
                    <td><%= client.getEmail() != null ? client.getEmail() : "-" %></td>
                    <td><%= client.getTelephone() %></td>
                    <td><%= client.getAdresse() %></td>
                    <td>
                      <% if (client.getImage() != null && !client.getImage().isEmpty()) { %>
                        <img src="${pageContext.request.contextPath}/images/uploads/<%= client.getImage() %>" alt="photo" width="50" height="50" class="rounded-circle" />
                      <% } else { %>
                        <span class="text-muted">Aucune</span>
                      <% } %>
                    </td>
                    <td>
                      <a href="/admin/modifier-client/<%= client.getIdClient() %>" class="btn btn-sm btn-primary">Modifier</a>
                      <form action="/admin/supprimer-client/<%= client.getIdClient() %>" method="get" style="display:inline;">
                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Supprimer ce client ?')">Supprimer</button>
                      </form>
                    </td>
                  </tr>
                  <% 
                      } // fin du for
                    } else {
                  %>
                  <tr>
                    <td colspan="9" class="text-center">Aucun client trouv&eacute;</td>
                  </tr>
                  <% } %>
                </tbody>
              </table>
            </div>
        
            <a href="/admin/ajout-client" class="btn btn-primary mt-3">Ajouter un client</a>
          </div>
        <%-- </div> --%>
        <!-- partial:partials/_footer.html -->
      <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>
</body>
<script src="/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page -->
  <script src="/vendors/typeahead.js/typeahead.bundle.min.js"></script>
  <script src="/vendors/select2/select2.min.js"></script>
  <!-- End plugin js for this page -->
  <!-- inject:js -->
  <script src="/js/off-canvas.js"></script>
  <script src="/js/hoverable-collapse.js"></script>
  <script src="/js/template.js"></script>
  <script src="/js/settings.js"></script>
  <script src="/js/todolist.js"></script>
  <!-- endinject -->
  <!-- Custom js for this page-->
  <script src="/js/file-upload.js"></script>
  <script src="/js/typeahead.js"></script>
  <script src="/js/select2.js"></script>
  <!-- End custom js for this page-->
</html>

