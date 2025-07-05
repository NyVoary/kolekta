<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/head.jsp" %>
<%
    // Récupérer les dates du modèle
    Object dateRangeObj = request.getAttribute("dateRange");
    String dateDebut = "";
    String dateFin = "";
    boolean hasDates = false;
    
    if (dateRangeObj != null) {
        // Utiliser la réflexion pour accéder aux propriétés (simplifié pour l'exemple)
        try {
            java.lang.reflect.Method getDateDebut = dateRangeObj.getClass().getMethod("getDateDebut");
            java.lang.reflect.Method getDateFin = dateRangeObj.getClass().getMethod("getDateFin");
            
            Object debutObj = getDateDebut.invoke(dateRangeObj);
            Object finObj = getDateFin.invoke(dateRangeObj);
            
            if (debutObj != null && finObj != null) {
                dateDebut = debutObj.toString();
                dateFin = finObj.toString();
                hasDates = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Récupérer les statistiques
    Object statsResume = request.getAttribute("statsResume");
    Object statsVilles = request.getAttribute("statsVilles");
    Object statsSexe = request.getAttribute("statsSexe");
    Object topProduits = request.getAttribute("topProduits");
%>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row">
            <div class="col-md-12 grid-margin">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Statistiques Clients</h4>
                  
                  <!-- Nouvelle section ajoutée -->
                  <div class="row">
                    <div class="col-md-12">
                      <h5 class="text-muted">
                        <% if (!hasDates) { %>
                            Statistiques pour toutes les périodes
                        <% } else { %>
                            Statistiques du <%= dateDebut %> au <%= dateFin %>
                        <% } %>
                      </h5>
                    </div>
                  </div>
                  
                  <form method="post" action="/admin/statistiques-clients" class="forms-sample">
                    <div class="row">
                      <div class="col-md-3">
                        <div class="form-group">
                          <label for="dateDebut">Date Début</label>
                          <input type="date" class="form-control" id="dateDebut" 
                                 name="dateDebut" value="<%= dateDebut %>">
                        </div>
                      </div>
                      <div class="col-md-3">
                        <div class="form-group">
                          <label for="dateFin">Date Fin</label>
                          <input type="date" class="form-control" id="dateFin" 
                                 name="dateFin" value="<%= dateFin %>">
                        </div>
                      </div>
                      <div class="col-md-2">
                        <button type="submit" class="btn btn-primary mr-2 mt-4">Générer</button>
                      </div>
                    </div>
                  </form>
                  
                  <!-- Résumé complet -->
                  <div class="row mt-4">
                    <div class="col-md-3 stretch-card grid-margin">
                      <div class="card bg-gradient-primary card-img-holder text-white">
                        <div class="card-body">
                          <h4 class="font-weight-normal mb-3">
                            <%= hasDates ? "Nouveaux Clients" : "Clients Totaux" %>
                            <i class="mdi mdi-account-multiple float-right"></i>
                          </h4>
                          <h2 class="mb-5">
                            <% if (statsResume != null) { 
                                try {
                                    Object count = statsResume.getClass().getMethod("getCount").invoke(statsResume);
                                    out.print(count != null ? count : "0");
                                } catch (Exception e) {
                                    out.print("0");
                                    e.printStackTrace();
                                }
                            } else { 
                                out.print("0");
                            } %>
                          </h2>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-3 stretch-card grid-margin">
                      <div class="card bg-gradient-info card-img-holder text-white">
                        <div class="card-body">
                          <h4 class="font-weight-normal mb-3">Commandes
                            <i class="mdi mdi-cart-outline float-right"></i>
                          </h4>
                          <h2 class="mb-5">
                            <% if (statsResume != null) { 
                                try {
                                    Object nbCommandes = statsResume.getClass().getMethod("getNbCommandes").invoke(statsResume);
                                    out.print(nbCommandes != null ? nbCommandes : "0");
                                } catch (Exception e) {
                                    out.print("0");
                                    e.printStackTrace();
                                }
                            } else { 
                                out.print("0");
                            } %>
                          </h2>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-3 stretch-card grid-margin">
                      <div class="card bg-gradient-success card-img-holder text-white">
                        <div class="card-body">
                          <h4 class="font-weight-normal mb-3">CA Total
                            <i class="mdi mdi-currency-usd float-right"></i>
                          </h4>
                          <h2 class="mb-5">
                            <% if (statsResume != null) { 
                                try {
                                    Object montant = statsResume.getClass().getMethod("getMontantFormatted").invoke(statsResume);
                                    out.print(montant != null ? montant : "0 Ar");
                                } catch (Exception e) {
                                    out.print("0 Ar");
                                    e.printStackTrace();
                                }
                            } else { 
                                out.print("0 Ar");
                            } %>
                          </h2>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-3 stretch-card grid-margin">
                      <div class="card bg-gradient-warning card-img-holder text-white">
                        <div class="card-body">
                          <h4 class="font-weight-normal mb-3">Panier Moyen
                            <i class="mdi mdi-basket float-right"></i>
                          </h4>
                          <h2 class="mb-5">
                            <% if (statsResume != null) { 
                                try {
                                    Object panierMoyen = statsResume.getClass().getMethod("getPanierMoyenFormatted").invoke(statsResume);
                                    out.print(panierMoyen != null ? panierMoyen : "0 Ar");
                                } catch (Exception e) {
                                    out.print("0 Ar");
                                    e.printStackTrace();
                                }
                            } else { 
                                out.print("0 Ar");
                            } %>
                          </h2>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <!-- Répartition par sexe -->
                  <div class="row mt-4">
                    <div class="col-md-6 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">Répartition par Sexe</h4>
                          <canvas id="sexeChart"></canvas>
                        </div>
                      </div>
                    </div>
                    
                    <!-- Top produits -->
                    <div class="col-md-6 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">Top 5 Produits</h4>
                          <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Produit</th>
                                  <th>Quantité</th>
                                  <th>CA</th>
                                </tr>
                              </thead>
                              <tbody>
                                <% if (topProduits != null && topProduits instanceof java.util.List) { 
                                    java.util.List produitsList = (java.util.List) topProduits;
                                    for (Object produit : produitsList) { 
                                        String libelle = (String) produit.getClass().getMethod("getLibelle").invoke(produit);
                                        Long count = (Long) produit.getClass().getMethod("getCount").invoke(produit);
                                        String montant = (String) produit.getClass().getMethod("getMontantFormatted").invoke(produit);
                                %>
                                  <tr>
                                    <td><%= libelle %></td>
                                    <td><%= count %></td>
                                    <td><%= montant %></td>
                                  </tr>
                                <% } 
                                   } else { %>
                                  <tr>
                                    <td colspan="3" class="text-center">Aucun produit trouvé</td>
                                  </tr>
                                <% } %>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <!-- Répartition par ville -->
                  <div class="row mt-4">
                    <div class="col-md-12 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">Répartition par Ville</h4>
                          <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Ville</th>
                                  <th>Nombre Commandes</th>
                                  <th>Chiffre Affaires</th>
                                </tr>
                              </thead>
                              <tbody>
                                <% if (statsVilles != null && statsVilles instanceof java.util.List) { 
                                    java.util.List villesList = (java.util.List) statsVilles;
                                    for (Object ville : villesList) { 
                                        String libelle = (String) ville.getClass().getMethod("getLibelle").invoke(ville);
                                        Long count = (Long) ville.getClass().getMethod("getCount").invoke(ville);
                                        String montant = (String) ville.getClass().getMethod("getMontantFormatted").invoke(ville);
                                %>
                                  <tr>
                                    <td><%= libelle %></td>
                                    <td><%= count %></td>
                                    <td><%= montant %></td>
                                  </tr>
                                <% } 
                                   } else { %>
                                  <tr>
                                    <td colspan="3" class="text-center">Aucune donnée de ville disponible</td>
                                  </tr>
                                <% } %>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>
                    </div>
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
  
  <script src="/vendors/chart.js/Chart.min.js"></script>
  <script>
    // Données pour le graphique de répartition par sexe
    var sexeMasculin = 0;
    var sexeFeminin = 0;
    
    <% if (statsSexe != null && statsSexe instanceof java.util.List) { 
        java.util.List sexesList = (java.util.List) statsSexe;
        for (Object stat : sexesList) { 
            String libelle = (String) stat.getClass().getMethod("getLibelle").invoke(stat);
            Long count = (Long) stat.getClass().getMethod("getCount").invoke(stat);
            
            if ("Masculin".equals(libelle)) { %>
                sexeMasculin = <%= count %>;
            <% } else if ("Féminin".equals(libelle)) { %>
                sexeFeminin = <%= count %>;
            <% }
        } 
    } %>
    
    // Chart Sexe
    var sexeCtx = document.getElementById('sexeChart').getContext('2d');
    var sexeChart = new Chart(sexeCtx, {
      type: 'pie',
      data: {
        labels: ['Masculin', 'Féminin'],
        datasets: [{
          data: [sexeMasculin, sexeFeminin],
          backgroundColor: [
            'rgba(54, 162, 235, 0.5)',
            'rgba(255, 99, 132, 0.5)'
          ],
          borderColor: [
            'rgba(54, 162, 235, 1)',
            'rgba(255, 99, 132, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'bottom'
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                var label = context.label || '';
                var value = context.raw || 0;
                var total = context.dataset.data.reduce((a, b) => a + b, 0);
                var percentage = Math.round((value / total) * 100);
                return label + ': ' + value + ' (' + percentage + '%)';
              }
            }
          }
        }
      }
    });
  </script>

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
</body>
</html>