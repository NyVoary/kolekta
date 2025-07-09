<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.entity.Client,com.example.demo.entity.Produits,java.util.List" %>
<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
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
                              <select class="form-control" name="produitIds[]" required>
                                <option value="">Sélectionner un produit</option>
                                <%
                                  List<Produits> produits = (List<Produits>) request.getAttribute("produits");
                                  if (produits != null) {
                                    for (Produits produit : produits) {
                                %>
                                <option value="<%= produit.getId() %>" data-prix="<%= produit.getPrixVente() %>">
                                  <%= produit.getNom() %> (<%= produit.getPrixVente() %> Ar)
                                </option>
                                <%
                                    }
                                  }
                                %>
                              </select>
                            </div>
                            <div class="col-md-4">
                              <input type="number" class="form-control quantite" name="quantites[]" min="1" placeholder="Quantité" required>
                            </div>
                          </div>
                        </div>
                      </div>
                      <button type="button" class="btn btn-secondary mt-2" id="ajouter-produit">Ajouter un produit</button>
                    </div>

                    <div class="form-group">
                      <label for="fraisLivraison">Frais de Livraison (Ar)</label>
                      <input type="number" step="0.01" class="form-control" id="fraisLivraison" name="fraisLivraison" readonly required>
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
                      <label><strong>Montant Total: </strong><span id="montantTotal">0.00</span> Ar</label>
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
  <script>
    $(document).ready(function() {
      function calculerMontantTotal() {
        var totalProduits = 0;
        $(".produit-row").each(function() {
          var prix = $(this).find("select option:selected").data("prix") || 0;
          var quantite = $(this).find(".quantite").val() || 0;
          totalProduits += prix * quantite;
        });

        var fraisLivraison = totalProduits * 0.10;
        if (fraisLivraison < 5000) {
          fraisLivraison = 5000;
        }

        $("#fraisLivraison").val(fraisLivraison.toFixed(2));

        var total = totalProduits + fraisLivraison;
        $("#montantTotal").text(total.toFixed(2));
      }

      // Déclencheur sur changement
      $(document).on("change", ".produit-row select, .quantite", calculerMontantTotal);

      // Ajout dynamique de ligne produit
      $("#ajouter-produit").click(function() {
        var nouvelleLigne = $(".produit-row").first().clone();
        nouvelleLigne.find("select").val("");
        nouvelleLigne.find(".quantite").val("");
        $("#produits-container").append(nouvelleLigne);
      });
    });
  </script>
</body>
</html>
