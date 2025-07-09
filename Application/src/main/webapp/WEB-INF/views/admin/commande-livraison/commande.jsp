```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.dto.CommandeDTO,java.util.List" %>
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
            <div class="col-12 grid-margin">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Liste des Commandes</h4>
                  <div class="table-responsive">
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>ID Commande</th>
                          <th>Date Commande</th>
                          <th>Client</th>
                          <th>Frais Livraison</th>
                          <th>Montant Total</th>
                          <th>Statut</th>
                          <th>Actions</th>
                        </tr>
                      </thead>
                      <tbody>
                        <%
                          List<CommandeDTO> commandes = (List<CommandeDTO>) request.getAttribute("commandes");
                          if (commandes != null && !commandes.isEmpty()) {
                            for (CommandeDTO commande : commandes) {
                        %>
                        <tr>
                          <td><%= commande.getIdCommande() %></td>
                          <td><%= commande.getDateCommande() %></td>
                          <td><%= commande.getClientNom() %></td>
                          <td><%= commande.getFraisLivraison() %> Ar</td>
                          <td><%= commande.getMontantTotal() %> Ar</td>
                          <td><%= commande.getStatut() %></td>
                          <td>
                            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#detailsModal<%= commande.getIdCommande() %>">
                              Détails
                            </button>
                          </td>
                        </tr>
                        <!-- Modale pour les détails de la commande -->
                        <div class="modal fade" id="detailsModal<%= commande.getIdCommande() %>" tabindex="-1" role="dialog" aria-labelledby="detailsModalLabel<%= commande.getIdCommande() %>" aria-hidden="true">
                          <div class="modal-dialog" role="document">
                            <div class="modal-content">
                              <div class="modal-header">
                                <h5 class="modal-title" id="detailsModalLabel<%= commande.getIdCommande() %>">Détails de la Commande #<%= commande.getIdCommande() %></h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                  <span aria-hidden="true">&times;</span>
                                </button>
                              </div>
                              <div class="modal-body">
                                <p><strong>ID Commande:</strong> <%= commande.getIdCommande() %></p>
                                <p><strong>Date Commande:</strong> <%= commande.getDateCommande() %></p>
                                <p><strong>Client:</strong> <%= commande.getClientNom() %></p>
                                <p><strong>Adresse Livraison:</strong> <%= commande.getAdresseLivraison() %></p>
                                <p><strong>Ville:</strong> <%= commande.getVilleLivraison() %></p>
                                <p><strong>Téléphone:</strong> <%= commande.getTelephoneLivraison() %></p>
                                <p><strong>Email:</strong> <%= commande.getEmailLivraison() %></p>
                                <p><strong>Frais Livraison:</strong> <%= commande.getFraisLivraison() %> Ar</p>
                                <p><strong>Montant Total:</strong> <%= commande.getMontantTotal() %> Ar</p>
                                <p><strong>Statut:</strong> <%= commande.getStatut() %></p>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                              </div>
                            </div>
                          </div>
                        </div>
                        <%
                            }
                          } else {
                        %>
                        <tr>
                          <td colspan="7" class="text-center">Aucune commande trouvée</td>
                        </tr>
                        <%
                          }
                        %>
                      </tbody>
                    </table>
                  </div>
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
  <!-- Inclure les scripts Bootstrap pour les modales -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>