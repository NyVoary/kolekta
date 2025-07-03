<%@ include file="../includes/head.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.Client" %>

<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <div id="right-sidebar" class="settings-panel">
        <i class="settings-close ti-close"></i>
        <ul class="nav nav-tabs border-top" id="setting-panel" role="tablist">
          <li class="nav-item">
            <a class="nav-link active" id="todo-tab" data-toggle="tab" href="#todo-section" role="tab" aria-controls="todo-section" aria-expanded="true">TO DO LIST</a>
          </li>
        </ul>
        <div class="tab-content" id="setting-content">
          <div class="tab-pane fade show active scroll-wrapper" id="todo-section" role="tabpanel" aria-labelledby="todo-section">
            <div class="add-items d-flex px-3 mb-0">
              <form class="form w-100">
                <div class="form-group d-flex">
                  <input type="text" class="form-control todo-list-input" placeholder="Add To-do">
                  <button type="submit" class="add btn btn-primary todo-list-add-btn" id="add-task">Add</button>
                </div>
              </form>
            </div>
            <div class="list-wrapper px-3">
              <ul class="d-flex flex-column-reverse todo-list">
                <li>
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox">
                      Team review meeting at 3.00 PM
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li>
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox">
                      Prepare for presentation
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li>
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox">
                      Resolve all the low priority tickets due today
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li class="completed">
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox" checked>
                      Schedule meeting for next week
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li class="completed">
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox" checked>
                      Project review
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
              </ul>
            </div>
          </div>
          <!-- To do section tab ends -->
        </div>
      </div>
      <%@ include file="../includes/sidebar.jsp" %>
      <!-- partial -->
      <div class="main-panel">
        <div class="main-panel">
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
                    <td><%= client.getSexe() == 'M' ? "Masculin" : "F&eacute;minin" %></td>
                    <td><%= client.getEmail() != null ? client.getEmail() : "-" %></td>
                    <td><%= client.getTelephone() %></td>
                    <td><%= client.getAdresse() %></td>
                    <td>
                      <% if (client.getImage() != null && !client.getImage().isEmpty()) { %>
                        <img src="/images/uploads/<%= client.getImage() %>" alt="photo" width="50" height="50" class="rounded-circle" />
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
        </div>
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

