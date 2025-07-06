<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../includes/head.jsp" %>
<style>
  .fournisseur-card {
    border-radius: 2rem;
    background: linear-gradient(135deg,#f8fafc 80%,#e0e7ef 100%);
    box-shadow: 0 6px 32px 0 rgba(79,140,255,0.18), 0 1.5px 6px 0 rgba(0,0,0,0.08);
    border: 3px solid;
    border-image: linear-gradient(120deg, #4f8cff 40%, #a0e9ff 100%) 1;
    transition: box-shadow 0.3s, transform 0.2s, border 0.4s;
    animation: cardFadeIn 0.7s cubic-bezier(.39,.575,.565,1.000);
    position: relative;
    overflow: hidden;
  }
  .fournisseur-card:hover {
    box-shadow: 0 12px 48px 0 rgba(79,140,255,0.28), 0 3px 12px 0 rgba(0,0,0,0.12);
    transform: translateY(-6px) scale(1.025);
    border-image: linear-gradient(120deg, #4f8cff 10%, #a0e9ff 80%, #ffb347 100%) 1;
  }
  @keyframes cardFadeIn {
    0% { opacity: 0; transform: scale(0.95) translateY(30px); }
    100% { opacity: 1; transform: scale(1) translateY(0); }
  }
  .fournisseur-card .card-img {
    border-radius: 50%;
    border: 3px solid #4f8cff;
    box-shadow: 0 2px 8px #b6d0ff;
    transition: border 0.3s;
  }
  .fournisseur-card:hover .card-img {
    border: 3px solid #ffb347;
  }
</style>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h1 class="h3">Liste des fournisseurs</h1>
        </div>
        <div class="row">
          <c:forEach var="fournisseur" items="${fournisseurs}">
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
              <div class="card fournisseur-card h-100 d-flex flex-column justify-content-between animate__animated animate__fadeIn">
                <div class="text-center pt-4 pb-2 flex-grow-1">
                  <h5 class="mt-3 mb-1 font-weight-bold text-primary">${fournisseur.nom}</h5>
                  <div class="text-muted small mb-2">${fournisseur.adresse}</div>
                  <div class="mb-2"><i class="ti-mobile"></i> ${fournisseur.telephone}</div>
                  <div class="mb-2"><i class="ti-email"></i> ${fournisseur.email}</div>
                </div>
                <hr class="my-2" style="border-top:1.5px solid #e2e8f0;width:90%;margin:auto;">
                <div class="px-3 pb-2">
                  <b>Produits :</b>
                  <c:choose>
                    <c:when test="${not empty produitsParFournisseur[fournisseur.id]}">
                      <ul class="mb-0 pl-0 text-left small list-group list-group-flush">
                        <c:forEach var="produit" items="${produitsParFournisseur[fournisseur.id]}">
                          <li class="list-group-item d-flex justify-content-between align-items-center px-2 py-1" style="background:transparent;border:none;">
                            <span><i class="ti-package text-info"></i> <b>${produit.nom}</b> <span class="text-muted">(${produit.uniteMesure})</span></span>
                            <span class="badge badge-pill badge-success">${produit.quantite} en stock</span>
                          </li>
                        </c:forEach>
                      </ul>
                    </c:when>
                    <c:otherwise>
                      <span class="text-muted">Aucun produit</span>
                    </c:otherwise>
                  </c:choose>
                </div>
                <div class="px-4 pb-4 pt-2 mt-auto">
                  <div class="btn-group w-100" role="group">
                    <a href="/admin/fournisseurs/edit?id=${fournisseur.id}" class="btn btn-warning btn-sm" title="Modifier"><i class="ti-pencil"></i></a>
                    <a href="/admin/fournisseurs/delete?id=${fournisseur.id}" class="btn btn-danger btn-sm" title="Supprimer" onclick="return confirm('Supprimer ce fournisseur ?');"><i class="ti-trash"></i></a>
                    <a href="/admin/fournisseurs/ajout-disponibilite" class="btn btn-info btn-sm" title="Ajouter disponibilité"><i class="ti-package"></i></a>
                  </div>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
        <div class="row mt-4">
          <div class="col-12 d-flex justify-content-center">
            <a href="/admin/fournisseurs/ajout" class="btn btn-primary btn-lg rounded-pill shadow px-5">
              <i class="ti-plus"></i> Ajouter un fournisseur
            </a>
          </div>
        </div>
      </div>
    </div>
      <%@ include file="../includes/footer.jsp" %>
  </div>
</body>
</html>

