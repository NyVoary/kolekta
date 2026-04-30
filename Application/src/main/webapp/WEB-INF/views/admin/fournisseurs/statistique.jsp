<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <form class="row mb-4 justify-content-center" method="get" action="">
            <div class="col-12 col-md-3 mb-2">
              <label for="dateDebut">Date début</label>
              <input type="date" class="form-control form-control-lg" name="dateDebut" id="dateDebut" value="${dateDebut}" />
            </div>
            <div class="col-12 col-md-3 mb-2">
              <label for="dateFin">Date fin</label>
              <input type="date" class="form-control form-control-lg" name="dateFin" id="dateFin" value="${dateFin}" />
            </div>
            <div class="col-12 col-md-2 mb-2 d-flex align-items-end">
              <button type="submit" class="btn btn-primary btn-lg w-100 rounded-pill shadow">Filtrer</button>
            </div>
          </form>
          <style>
            .stat-card {
              box-shadow: 0 6px 24px 0 rgba(79,140,255,0.10), 0 1.5px 6px 0 rgba(0,0,0,0.06);
              border-radius: 1.5rem;
              margin-bottom: 2rem;
              background: #fff;
              border: 1.5px solid #e6eaf3;
              transition: box-shadow 0.2s, border 0.2s;
              min-height: 170px;
              display: flex;
              flex-direction: column;
              justify-content: center;
              text-align: left;
            }
            .stat-card:not(:last-child) {
              margin-right: 1.5rem;
            }
            .stat-card h6 {
              text-align: center;
              font-size: 1.05rem;
              font-weight: 600;
              letter-spacing: 0.01em;
              color: #6c757d;
              margin-bottom: 0.7rem;
              margin-top: 0.2rem;
              width: 100%;
              display: block;
            }
            .stat-card h2 {
              text-align: center;
              font-size: 2.2rem;
              font-weight: 700;
              margin-bottom: 0.2rem;
              letter-spacing: 0.01em;
              width: 100%;
              display: block;
            }
            .stat-card ul, .stat-card ol {
              margin-top: 0.7rem;
              margin-bottom: 0;
              padding-left: 1.2rem;
              text-align: left;
            }
            .stat-card span.text-muted {
              text-align: left;
              display: block;
            }
            .stat-row {
              margin-left: 0;
              margin-right: 0;
              gap: 2rem;
              display: flex;
              flex-wrap: wrap;
              justify-content: center;
            }
            @media (max-width: 991px) {
              .stat-row { gap: 1rem; }
              .stat-card { margin-right: 0; margin-bottom: 1.2rem; }
            }
          </style>
          <div class="stat-row mb-4">
            <div class="col-12 col-md-3 mb-3 p-0">
              <div class="card stat-card h-100 p-3">
                <h6>Nombre total de fournisseurs</h6>
                <h2 class="text-primary">${totalFournisseurs}</h2>
              </div>
            </div>
            <div class="col-12 col-md-3 mb-3 p-0">
              <div class="card stat-card h-100 p-3" style="background: #f6fff8;">
                <h6>Nouveaux fournisseurs</h6>
                <h2 class="text-success">${nouveauxFournisseurs.size()}</h2>
                <c:if test="${not empty nouveauxFournisseurs}">
                  <ul class="list-unstyled small mt-2">
                    <c:forEach var="f" items="${nouveauxFournisseurs}">
                      <li><i class="ti-user"></i> ${f.nom}</li>
                    </c:forEach>
                  </ul>
                </c:if>
              </div>
            </div>
            <div class="col-12 col-md-3 mb-3 p-0">
              <div class="card stat-card h-100 p-3" style="background: #f0f7fa;">
                <h6>Moyenne produits/fournisseur</h6>
                <h2 class="text-info">
                  <fmt:formatNumber value="${moyenneProduits}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                </h2>
              </div>
            </div>
            <div class="col-12 col-md-3 mb-3 p-0">
              <div class="card stat-card h-100 p-3" style="background: #fffaf0;">
                <h6>Meilleures nombres de ventes:</h6>
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
      </div>
    </div>
      <%@ include file="../includes/footer.jsp" %>
  </div>
</body>
</html>

