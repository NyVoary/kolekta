<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <form class="row mb-4 justify-content-center" method="get" action="">
            <div class="col-md-3 mb-2">
              <label for="dateDebut">Date début</label>
              <input type="date" class="form-control form-control-lg" name="dateDebut" id="dateDebut" value="${dateDebut}" />
            </div>
            <div class="col-md-3 mb-2">
              <label for="dateFin">Date fin</label>
              <input type="date" class="form-control form-control-lg" name="dateFin" id="dateFin" value="${dateFin}" />
            </div>
            <div class="col-md-2 mb-2 d-flex align-items-end">
              <button type="submit" class="btn btn-gradient-primary btn-lg w-100">Filtrer</button>
            </div>
          </form>
          <div class="row mb-4">
            <div class="col-md-3 mb-3">
              <div class="card shadow-sm h-100 text-center p-3">
                <h6 class="text-muted">Nombre total de fournisseurs</h6>
                <h2 class="text-primary font-weight-bold">${totalFournisseurs}</h2>
              </div>
            </div>
            <div class="col-md-3 mb-3">
              <div class="card shadow-sm h-100 text-center p-3">
                <h6 class="text-muted">Nouveaux fournisseurs</h6>
                <h2 class="text-success font-weight-bold">${nouveauxFournisseurs.size()}</h2>
                <c:if test="${not empty nouveauxFournisseurs}">
                  <ul class="list-unstyled small mt-2">
                    <c:forEach var="f" items="${nouveauxFournisseurs}">
                      <li><i class="ti-user"></i> ${f.nom}</li>
                    </c:forEach>
                  </ul>
                </c:if>
              </div>
            </div>
            <div class="col-md-3 mb-3">
              <div class="card shadow-sm h-100 text-center p-3">
                <h6 class="text-muted">Moyenne produits/fournisseur</h6>
                <h2 class="text-info font-weight-bold">${moyenneProduits}</h2>
              </div>
            </div>
            <div class="col-md-3 mb-3">
              <div class="card shadow-sm h-100 text-center p-3">
                <h6 class="text-muted">Top fournisseurs (ventes)</h6>
                <c:if test="${not empty topFournisseurs}">
                  <ol class="list-unstyled mt-2">
                    <c:forEach var="tf" items="${topFournisseurs}">
                      <li><i class="ti-crown text-warning"></i> Fournisseur #${tf.idFournisseur} - ${tf.totalVendus} ventes</li>
                    </c:forEach>
                  </ol>
                </c:if>
                <c:if test="${empty topFournisseurs}">
                  <span class="text-muted">Aucune vente sur la période</span>
                </c:if>
              </div>
            </div>
          </div>
        </div>
        <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>
  </div>
</body>
</html>

