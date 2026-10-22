<%@ page import="com.example.demo.entity.Client" %>
<%@ page import="com.example.demo.entity.Commande" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ include file="../includes/head.jsp" %>
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
        <div class="content-wrapper">
          <div class="row">
            <div class="col-md-6 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Informations du client</h4>
                  
                  <% Client client = (Client) request.getAttribute("client"); %>
                  <% if (client != null) { %>
                  
                    <div class="text-center mb-4">
                      <% if (client.getImage() != null && !client.getImage().isEmpty()) { %>
                        <img src="/images/uploads/<%= client.getImage() %>" 
                             alt="Photo du client" 
                             class="rounded-circle" 
                             width="150" 
                             height="150">
                      <% } else { %>
                        <div class="avatar-placeholder rounded-circle">
                          <i class="ti-user" style="font-size: 80px;"></i>
                        </div>
                      <% } %>
                    </div>
                    
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label">Nom complet</label>
                      <div class="col-sm-9">
                        <p class="form-control-plaintext">
                          <%= client.getNom() %> <%= client.getPrenoms() %>
                        </p>
                      </div>
                    </div>
                    
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label">Date de naissance</label>
                      <div class="col-sm-9">
                        <p class="form-control-plaintext">
                          <%= client.getDtn() != null ? client.getDtn().toString() : "Non sp&eacute;cifi&eacute;e" %>
                        </p>
                      </div>
                    </div>
                    
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label">Sexe</label>
                      <div class="col-sm-9">
                        <p class="form-control-plaintext">
                        </p>
                      </div>
                    </div>
                    
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label">Email</label>
                      <div class="col-sm-9">
                        <p class="form-control-plaintext">
                          <%= client.getEmail() != null ? client.getEmail() : "Non sp&eacute;cifi&eacute;" %>
                        </p>
                      </div>
                    </div>
                    
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label">T&eacute;l&eacute;phone</label>
                      <div class="col-sm-9">
                        <p class="form-control-plaintext">
                          <%= client.getTelephone() %>
                        </p>
                      </div>
                    </div>
                    
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label">Adresse</label>
                      <div class="col-sm-9">
                        <p class="form-control-plaintext">
                          <%= client.getAdresse() %>
                        </p>
                      </div>
                    </div>
                    
                    <div class="mt-4 text-center">
                      <a href="/admin/liste-client" class="btn btn-light mr-2">Retour</a>
                      <a href="/admin/modifier-client/<%= client.getIdClient() %>" class="btn btn-primary">Modifier</a>
                    </div>
                    
                  <% } else { %>
                    <div class="alert alert-danger">
                      Client non trouv&eacute;
                    </div>
                    <a href="/admin/liste-client" class="btn btn-light">Retour &agrave; la liste</a>
                  <% } %>
                  
                </div>
              </div>
            </div>
            
            <div class="col-md-6 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Historique des commandes</h4>
                  
                  <% List<Commande> commandes = (List<Commande>) request.getAttribute("commandes"); %>
                  <% if (commandes != null && !commandes.isEmpty()) { %>
                    <div class="table-responsive">
                      <table class="table table-hover">
                        <thead>
                          <tr>
                            <th>N° Commande</th>
                            <th>Date</th>
                            <th>Montant</th>
                            <th>Statut</th>
                            <th>Actions</th>
                          </tr>
                        </thead>
                        <tbody>
                          <% for (Commande commande : commandes) { %>
                            <tr>
                              <td><%= commande.getId() %></td>
                              <td><%= commande.getDateCommande() %></td>
                              <td><%= String.format("%,.2f", commande.getMontantTotal()) %> Ar</td>
                              <td>
                                <span class="badge badge-<%= getStatusBadgeClass(commande.getDernierStatut()) %>">
                                    <%= commande.getDernierStatut() != null ? commande.getDernierStatut() : "Inconnu" %>
                                </span>
                            </td>
                              <td>
                                <a href="/admin/commande/<%= commande.getId() %>" 
                                   class="btn btn-sm btn-info" 
                                   title="Détails">
                                  <i class="ti-eye"></i>
                                </a>
                              </td>
                            </tr>
                          <% } %>
                        </tbody>
                      </table>
                    </div>
                  <% } else { %>
                    <div class="alert alert-info">
                      Ce client n'a pas encore pass&eacute; de commande.
                    </div>
                  <% } %>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- partial:partials/_footer.html -->
        <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>
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
  
  <%!
    private String getStatusBadgeClass(String status) {
      if (status == null) return "secondary";
      
      switch(status.toLowerCase()) {
        case "validée":
          return "success";
        case "en attente de validation":
          return "warning";
        case "annulée":
          return "danger";
        case "livrée":
          return "primary";
        case "en cours de traitement":
          return "info";
        case "expédiée":
          return "primary";
        case "livraison en cours":
          return "info";
        default:
          return "secondary";
      }
    }
  %>
</body>
</html>