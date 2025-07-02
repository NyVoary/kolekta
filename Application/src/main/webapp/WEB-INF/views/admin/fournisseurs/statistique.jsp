<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row mb-4">
            <div class="col-12 text-center">
              <h2 class="font-weight-bold mb-3">Statistiques Fournisseurs</h2>
              <p class="text-muted">Vue d'ensemble des fournisseurs et de leurs produits</p>
            </div>
          </div>
          <div class="row mb-4">
            <div class="col-md-4 mb-3">
              <div class="card shadow-sm h-100">
                <div class="card-body text-center">
                  <h5 class="card-title">Nombre total de fournisseurs</h5>
                  <h2 class="text-primary font-weight-bold">${totalFournisseurs}</h2>
                </div>
              </div>
            </div>
            <div class="col-md-4 mb-3">
              <div class="card shadow-sm h-100">
                <div class="card-body text-center">
                  <h5 class="card-title">Nombre total de produits</h5>
                  <h2 class="text-success font-weight-bold">${totalProduits}</h2>
                </div>
              </div>
            </div>
            <div class="col-md-4 mb-3">
              <div class="card shadow-sm h-100">
                <div class="card-body text-center">
                  <h5 class="card-title">Top fournisseur (produits)</h5>
                  <h2 class="text-warning font-weight-bold">${topFournisseur.nom}</h2>
                  <p class="mb-0">${topFournisseur.nbProduits} produits</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row mb-4">
            <div class="col-12">
              <div class="card shadow-sm">
                <div class="card-body">
                  <h5 class="card-title">Répartition des produits par fournisseur</h5>
                  <canvas id="produitsParFournisseurChart" height="100"></canvas>
                  <c:if test="${empty fournisseursJson || fournisseursJson == '[]'}">
                    <div class="alert alert-info mt-3">Aucune donnée à afficher pour le graphique.</div>
                  </c:if>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-12 text-center">
              <a href="/admin/fournisseurs/liste" class="btn btn-outline-primary">Retour à la liste des fournisseurs</a>
            </div>
          </div>
        </div>
        <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>
  </div>
  <script src="/Affichage/Template/js/chart.js"></script>
  <script>
    // Données dynamiques passées depuis le contrôleur
    const fournisseurs = JSON.parse('${fournisseursJson}'.replace(/&quot;/g,'"'));
    if(fournisseurs.length > 0) {
      const labels = fournisseurs.map(f => f.nom);
      const data = fournisseurs.map(f => f.nbProduits);
      const ctx = document.getElementById('produitsParFournisseurChart').getContext('2d');
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: labels,
          datasets: [{
            label: 'Nombre de produits',
            data: data,
            backgroundColor: 'rgba(54, 162, 235, 0.6)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: { display: false },
            title: { display: false }
          },
          scales: {
            y: { beginAtZero: true }
          }
        }
      });
    }
  </script>
</body>
</html>

