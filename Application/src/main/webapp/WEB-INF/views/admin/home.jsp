<jsp:useBean id="acces" class="com.example.demo.entity.Acces" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/head.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="com.example.demo.entity.AssignationsTaches"%>
<%@page import="com.example.demo.entity.StatusTaches"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
<link rel="stylesheet" href="/css/barChart.css">

<body>
  <div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row justify-content-center align-items-center" style="min-height:60vh;">
            <div class="col-md-8 text-center">
              <%-- <h1 class="display-4 mb-4">Bienvenue sur le tableau de bord</h1> --%>
              <h1 class="lead mb-4">Bonjour, <strong>${acces.nomUtilisateur}</strong> (rôle : <strong>${acces.role}</strong>)</h1>
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
          <div class="col-md-7 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <p class="card-title mb-0">Tâches à faire pour: <strong>${acces.nomUtilisateur}</strong> / <%= LocalDate.now() %></p>
                  <div class="table-responsive">
                    <table class="table table-striped table-borderless">
                      <thead>
                          <tr>
                              <th>Tâche et description</th>
                              <th>Durée</th>
                              <th>Date</th>
                              <th>Importance</th>
                              <th>Status</th>
                              <th>Action</th>
                          </tr>
                      </thead>
                      <tbody>
                          <%
                          List<AssignationsTaches> taches = (List<AssignationsTaches>) request.getAttribute("taches");
                          if (taches != null && !taches.isEmpty()) {
                              for (AssignationsTaches tache : taches) {
                          %>
                          <tr>
                              <td>
                                  <strong><%= tache.getTitre() %></strong><br>
                                  <small class="text-muted"><%= tache.getDescription() != null ? tache.getDescription() : "" %></small>
                              </td>
                              <td class="font-weight-bold">
                                  <%= tache.getDateDebut().toLocalTime() %> - <%= tache.getDateFin().toLocalTime() %>
                              </td>
                              <td>
                                  <%= tache.getDateDebut().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
                              </td>
                              <td>
                                  <%
                                  String prioriteLibelle = tache.getPriorite() != null ? tache.getPriorite().getLibelle() : "";
                                  String prioriteBadgeClass = "";
                                  if (prioriteLibelle != null) {
                                      switch(prioriteLibelle.toLowerCase()) {
                                          case "urgente": 
                                              prioriteBadgeClass = "badge-danger"; 
                                              break;
                                          case "haute": 
                                              prioriteBadgeClass = "badge-warning"; 
                                              break;
                                          case "moyenne": 
                                              prioriteBadgeClass = "badge-info"; 
                                              break;
                                          case "basse": 
                                              prioriteBadgeClass = "badge-secondary"; 
                                              break;
                                          default:
                                              prioriteBadgeClass = "badge-light";
                                      }
                                  }
                                  %>
                                  <span class="badge <%= prioriteBadgeClass %>">
                                      <%= prioriteLibelle %>
                                  </span>
                              </td>
                              <td class="font-weight-medium">
                                  <%
                                  String statutLibelle = tache.getStatus() != null ? tache.getStatus().getLibelle() : "";
                                  String statutBadgeClass = "";
                                  if (statutLibelle != null) {
                                      switch(statutLibelle.toLowerCase()) {
                                          case "en cours": 
                                              statutBadgeClass = "badge-primary";
                                              break;
                                          case "en attente": 
                                              statutBadgeClass = "badge-light";
                                              break;
                                          case "terminée": 
                                          case "termine": 
                                              statutBadgeClass = "badge-success";
                                              break;
                                          case "annulée": 
                                          case "annule": 
                                              statutBadgeClass = "badge-dark";
                                              break;
                                          default:
                                              statutBadgeClass = "badge-secondary";
                                      }
                                  }
                                  %>
                                  <span class="badge <%= statutBadgeClass %>">
                                      <%= statutLibelle %>
                                  </span>
                              </td>
                              <td>
                                  <button class="btn btn-sm btn-outline-primary" 
                                          data-toggle="modal" 
                                          data-target="#statusModal"
                                          data-tache-id="<%= tache.getIdTache() %>"
                                          data-current-status="<%= tache.getStatus() != null ? tache.getStatus().getIdStatus() : "" %>">
                                      Modifier
                                  </button>
                              </td>
                          </tr>
                          <%
                              }
                          } else {
                          %>
                          <tr>
                              <td colspan="6" class="text-center text-muted">Aucune tâche prévue pour aujourd'hui</td>
                          </tr>
                          <%
                          }
                          %>
                      </tbody>
                  </table>
                  </div>
              </div>
              
              <!-- Modal pour changer le statut -->
              <div class="modal fade" id="statusModal" tabindex="-1" role="dialog" aria-labelledby="statusModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="statusModalLabel">Modifier le statut</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <form action="/admin/changer-statut" method="POST" id="changerStatutForm">
                            <div class="modal-body">
                                <input type="hidden" id="tacheId" name="tacheId">
                                <input type="hidden" id="ancienStatusId" name="ancienStatusId">
                                <input type="hidden" name="modifieParId" value="${acces.idEmploye}">
                                
                                <div class="form-group">
                                    <label for="nouveauStatutId">Nouveau statut:</label>
                                    <select class="form-control" id="nouveauStatutId" name="nouveauStatutId" required>
                                        <% 
                                        List<StatusTaches> statusList = (List<StatusTaches>) request.getAttribute("status");
                                        if (statusList != null && !statusList.isEmpty()) {
                                            for (StatusTaches status : statusList) { 
                                        %>
                                            <option value="<%= status.getIdStatus() %>"><%= status.getLibelle() %></option>
                                        <% 
                                            }
                                        } else { 
                                        %>
                                            <option value="">Aucun statut disponible</option>
                                        <% 
                                        } 
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-primary">Enregistrer</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
              </div>
                </div>
              </div>
            </div>
        </div>
        <%@ include file="includes/footer.jsp" %>
      </div>
      <%-- <%@ include file="includes/sidebar.jsp" %> --%>
      <!-- partial -->
      <%-- <div class="main-panel">
        <div class="content-wrapper">
          <h1>Statistiques des produits</h1>
          
          <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
          </c:if>
          
          
          <div class="row">
            <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Statistiques générales des produits</h4>
                  <canvas id="barChart"></canvas>
                </div>
              </div>
            </div>
          </div>
          
          <div class="row">
            <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Top des produits vendus</h4>
                  <canvas id="topProductsChart"></canvas>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- partial:partials/_footer.html --> --%>
      <%-- <%@ include file="includes/footer.jsp" %> --%>
      <%-- </div> --%>
    </div>   
  </div>
  <!-- Chart.js -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

              
  <script>
    // Initialisation du modal de changement de statut
    $('#statusModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var tacheId = button.data('tache-id');
        var currentStatusId = button.data('current-status');
        
        $('#tacheId').val(tacheId);
        $('#ancienStatusId').val(currentStatusId);
        $('#nouveauStatutId').val(currentStatusId);
    });

    // Affichage des notifications
    <c:if test="${not empty success}">
        toastr.success('${success}');
    </c:if>
    <c:if test="${not empty error}">
        toastr.error('${error}');
    </c:if>

    // Scripts existants pour les graphiques...
    document.addEventListener('DOMContentLoaded', function() {
      // Vos scripts de graphiques existants...
    });
  </script>
  
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Récupération des données depuis le serveur
      const statistics = {
        <c:if test="${not empty statistics}">
          general: {
            totalProduits: ${statistics.generale.totalProduits},
            produitsRupture: ${statistics.generale.produitsRupture},
            produitsStockBas: ${statistics.generale.produitsStockBas},
            nouveauxProduits: ${statistics.generale.nouveauxProduits}
          },
          top3Plus: {
            noms: [<c:forEach var="nom" items="${statistics.top3Plus.noms}" varStatus="status">"${nom}"<c:if test="${!status.last}">, </c:if></c:forEach>],
            quantites: [<c:forEach var="qty" items="${statistics.top3Plus.quantites}" varStatus="status">${qty}<c:if test="${!status.last}">, </c:if></c:forEach>]
          },
          top3Moins: {
            noms: [<c:forEach var="nom" items="${statistics.top3Moins.noms}" varStatus="status">"${nom}"<c:if test="${!status.last}">, </c:if></c:forEach>],
            quantites: [<c:forEach var="qty" items="${statistics.top3Moins.quantites}" varStatus="status">${qty}<c:if test="${!status.last}">, </c:if></c:forEach>]
          }
        </c:if>
      };

      // ===========================================
      // PREMIER GRAPHIQUE - Statistiques générales
      // ===========================================
      const ctx1 = document.getElementById('barChart').getContext('2d');
      
      const generalLabels = ['Total Produits', 'Rupture Stock', 'Stock Bas', 'Nouveaux ce mois'];
      const generalData = [];
      
      <c:if test="${not empty statistics}">
        generalData.push(statistics.general.totalProduits || 0);
        generalData.push(statistics.general.produitsRupture || 0);
        generalData.push(statistics.general.produitsStockBas || 0);
        generalData.push(statistics.general.nouveauxProduits || 0);
      </c:if>

      new Chart(ctx1, {
        type: 'bar',
        data: {
          labels: generalLabels,
          datasets: [{
            label: 'Nombre',
            data: generalData,
            backgroundColor: [
              'rgba(54, 162, 235, 0.8)',   // Total produits
              'rgba(255, 99, 132, 0.8)',   // Rupture stock
              'rgba(255, 206, 86, 0.8)',   // Stock bas
              'rgba(75, 192, 192, 0.8)'    // Nouveaux
            ],
            borderColor: [
              'rgba(54, 162, 235, 1)',
              'rgba(255, 99, 132, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)'
            ],
            borderWidth: 2
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              beginAtZero: true,
              ticks: {
                precision: 0
              }
            }
          },
          plugins: {
            legend: {
              display: true,
              position: 'top'
            },
            title: {
              display: true,
              text: 'Vue d\'ensemble des statistiques produits'
            }
          }
        }
      });

      // ===========================================
      // DEUXIEME GRAPHIQUE - Top produits vendus
      // ===========================================
      const ctx2 = document.getElementById('topProductsChart').getContext('2d');
      
      // Labels et données directement depuis le serveur
      const topLabels = [];
      const topData = [];
      
      // Top 3 plus vendus (avec prefix +)
      <c:if test="${not empty statistics.top3Plus.noms}">
        <c:forEach var="nom" items="${statistics.top3Plus.noms}" varStatus="status">
          topLabels.push("+ ${nom}");
        </c:forEach>
      </c:if>
      
      <c:if test="${not empty statistics.top3Plus.quantites}">
        <c:forEach var="qty" items="${statistics.top3Plus.quantites}">
          topData.push(${qty});
        </c:forEach>
      </c:if>
      
      // Top 3 moins vendus (avec prefix -)
      <c:if test="${not empty statistics.top3Moins.noms}">
        <c:forEach var="nom" items="${statistics.top3Moins.noms}" varStatus="status">
          topLabels.push("- ${nom}");
        </c:forEach>
      </c:if>
      
      <c:if test="${not empty statistics.top3Moins.quantites}">
        <c:forEach var="qty" items="${statistics.top3Moins.quantites}">
          topData.push(${qty});
        </c:forEach>
      </c:if>

      new Chart(ctx2, {
        type: 'bar',
        data: {
          labels: topLabels,
          datasets: [{
            label: 'Quantité vendue',
            data: topData,
            backgroundColor: [
              'rgba(46, 125, 50, 0.8)',   // Top+ 1
              'rgba(67, 160, 71, 0.8)',   // Top+ 2
              'rgba(102, 187, 106, 0.8)', // Top+ 3
              'rgba(211, 47, 47, 0.8)',   // Top- 1
              'rgba(229, 57, 53, 0.8)',   // Top- 2
              'rgba(239, 83, 80, 0.8)'    // Top- 3
            ],
            borderColor: [
              'rgba(46, 125, 50, 1)',
              'rgba(67, 160, 71, 1)',
              'rgba(102, 187, 106, 1)',
              'rgba(211, 47, 47, 1)',
              'rgba(229, 57, 53, 1)',
              'rgba(239, 83, 80, 1)'
            ],
            borderWidth: 2
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              beginAtZero: true,
              ticks: {
                precision: 0
              }
            }
          },
          plugins: {
            legend: {
              display: true,
              position: 'top'
            },
            title: {
              display: true,
              text: 'Top 3 produits les plus vendus (+) et les moins vendus (-)'
            }
          }
        }
      });
    });
  </script>
</body>
</html>

