<%@ include file="../includes/head.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.Clients" %>

<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      
      <div class="main-panel">
                        <c:if test="${not empty succes}">
                    <div style="color: green;">${succes}</div>
                </c:if>
          <div class="content-wrapper col-12">
            <div class="row">
              <div class="col-md-12 grid-margin stretch-card">
                <div class="card">
                  <div class="card-header">
                    <h4 class="card-title">Liste des Clients</h4>
                  </div>
                  <div class="card-body">
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
                    List<Clients> clients = (List<Clients>) request.getAttribute("clients");
                    if (clients != null && !clients.isEmpty()) {
                      for (Clients client : clients) {
                  %>
                  <tr>
                    <td><a href="/admin/informations-client/<%= client.getId() %>" class="btn btn-sm btn-primary"><%= client.getId() %></a></td>
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
                      <a href="/admin/modifier-client/<%= client.getId() %>" class="btn btn-sm btn-primary">Modifier</a>
                      <form action="/admin/supprimer-client/<%= client.getId() %>" method="get" style="display:inline;">
                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Supprimer ce client ?')">Desactiver</button>
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
                  </div>
                </div>
              </div>
            </div>
        
            
        
            <a href="/admin/ajout-client" class="btn btn-primary mt-3">Ajouter un client</a>
          </div>
        <!-- partial:partials/_footer.html -->
      <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>
    <!-- partial -->
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

