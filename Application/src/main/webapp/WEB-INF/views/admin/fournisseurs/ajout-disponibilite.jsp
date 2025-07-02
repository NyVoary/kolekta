<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h1 class="h3">Ajouter une disponibilité</h1>
          <a href="/admin/fournisseurs/liste" class="btn btn-outline-secondary">Retour à la liste</a>
        </div>
        <div class="card p-4 shadow-lg" style="max-width:900px;width:95vw;margin:auto;background:rgba(245,248,255,0.98);border-radius:2rem;">
          <form action="/admin/fournisseurs/ajout-disponibilite" method="post" autocomplete="off">
            <div class="form-group mb-4 position-relative">
              <label>Fournisseur <span class="text-danger">*</span></label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-user"></i></span></div>
                <select name="fournisseur.id" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" required>
                  <option value="" disabled selected>Choisir un fournisseur</option>
                  <c:forEach var="f" items="${fournisseurs}">
                    <option value="${f.id}">${f.nom}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group mb-4 position-relative">
              <label>Produit <span class="text-danger">*</span></label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-package"></i></span></div>
                <select name="produit.id" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" required>
                  <option value="" disabled selected>Choisir un produit</option>
                  <c:forEach var="p" items="${produits}">
                    <option value="${p.id}">${p.nom}</option>
                  </c:forEach>
                </select>
              </div>
              <a href="/admin/produits/ajout" class="btn btn-link p-0">Ajouter un produit</a>
            </div>
            <div class="form-group mb-4 position-relative">
              <label>Quantité <span class="text-danger">*</span></label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-plus"></i></span></div>
                <input type="number" name="quantite" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" min="1" required placeholder="Entrer la quantité" />
              </div>
            </div>
            <button type="submit" class="btn btn-gradient-primary btn-lg w-100 rounded-pill py-3">Ajouter la disponibilité</button>
            <c:if test="${not empty success}">
              <div class="alert alert-success mt-3">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
              <div class="alert alert-danger mt-3">${error}</div>
            </c:if>
          </form>
        </div>
      </div>
    </div>
  </div>
  <script>
    // Focus sur le premier champ
    document.addEventListener('DOMContentLoaded', function() {
      const selects = document.querySelectorAll('select');
      if(selects.length) selects[0].focus();
    });
  </script>
</body>
</html>

