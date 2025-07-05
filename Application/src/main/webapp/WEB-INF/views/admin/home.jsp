<jsp:useBean id="acces" class="com.example.demo.entity.Acces" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/head.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="/css/barChart.css">

<body>
  <div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
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
      <%@ include file="includes/sidebar.jsp" %>
      <!-- partial -->
      <div class="main-panel">
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
        <!-- partial:partials/_footer.html -->
      <%@ include file="includes/footer.jsp" %>
      </div>
    </div>   
  </div>
  <!-- Chart.js -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  
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

