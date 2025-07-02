<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.entity.Client,com.example.demo.entity.Produit,java.util.List" %>
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
                  <h4 class="card-title">Ajouter une Commande</h4>
                  <form action="/admin/ajout-commande/traitement" method="post" class="form-sample">
                    <div class="form-group">
                      <label for="idClient">Client</label>
                      <select class="form-control" id="idClient" name="idClient" required>
                        <option value="">Sélectionner un client</option>
                        <%
                          List<Client> clients = (List<Client>) request.getAttribute("clients");
                          if (clients != null) {
                            for (Client client : clients) {
                        %>
                        <option value="<%= client.getIdClient() %>"><%= client.getNom() %> <%= client.getPrenoms() %></option>
                        <%
                            }
                          }
                        %>
                      </select>
                    </div>
                    <div class="form-group">
                      <label>Produits</label>
                      <div id="produits-container">
                        <div class="produit-row mb-3">
                          <div class="row">
                            <div class="col-md-8">
                              <select class="form-control" name="produitIds" required>
                                <option value="">Sélectionner un produit</option>
                                <%
                                  List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                                  if (produits != null) {
                                    for (Produit produit : produits) {
                                %>
                                <option value="<%= produit.getIdProduit() %>" data-prix="<%= produit.getPrixVente() %>">
                                  <%= produit.getNom() %> (<%= produit.getPrixVente() %> €)
                                </option>
                                <%
                                    }
                                  }
                                %>
                              </select>
                            </div>
                            <div class="col-md-4">
                              <input type="number" class="form-control quantite" name="quantites" min="1" placeholder="Quantité" required>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="fraisLivraison">Frais de Livraison (€)</label>
                      <input type="number" step="0.01" class="form-control" id="fraisLivraison" name="fraisLivraison" required>
                    </div>
                    <div class="form-group">
                      <label for="adresseLivraison">Adresse de Livraison</label>
                      <input type="text" class="form-control" id="adresseLivraison" name="adresseLivraison" required>
                    </div>
                    <div class="form-group">
                      <label for="villeLivraison">Ville de Livraison</label>
                      <input type="text" class="form-control" id="villeLivraison" name="villeLivraison" required>
                    </div>
                    <div class="form-group">
                      <label for="telephoneLivraison">Téléphone de Livraison</label>
                      <input type="text" class="form-control" id="telephoneLivraison" name="telephoneLivraison" required>
                    </div>
                    <div class="form-group">
                      <label for="emailLivraison">Email de Livraison (optionnel)</label>
                      <input type="email" class="form-control" id="emailLivraison" name="emailLivraison">
                    </div>
                    <div class="form-group">
                      <label><strong>Montant Total: </strong><span id="montantTotal">0.00</span> €</label>
                    </div>
                    <button type="submit" class="btn btn-primary mr-2">Ajouter Commande</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script>
    $(document).ready(function() {
      // Calculer le montant total en temps réel
      function calculerMontantTotal() {
        var total = 0;
        $(".produit-row").each(function() {
          var prix = $(this).find("select option:selected").data("prix") || 0;
          var quantite = $(this).find(".quantite").val() || 0;
          total += prix * quantite;
        });
        total += parseFloat($("#fraisLivraison").val() || 0);
        $("#montantTotal").text(total.toFixed(2));
      }

      // Mettre à jour le montant total à chaque changement
      $(document).on("change", ".produit-row select, .quantite, #fraisLivraison", calculerMontantTotal);
    });
  </script>
</body>
</html>