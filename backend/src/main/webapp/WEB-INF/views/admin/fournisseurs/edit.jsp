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
          <h1 class="h3">Modifier un fournisseur</h1>
          <a href="/admin/fournisseurs/liste" class="btn btn-outline-secondary rounded-pill shadow">Retour à la liste</a>
        </div>
        <div class="card p-4 shadow-lg bg-light" style="max-width:900px;width:95vw;margin:auto;border-radius:2rem;">
          <form action="/admin/fournisseurs/update" method="post" autocomplete="off" aria-label="Formulaire de modification fournisseur">
            <input type="hidden" name="id" value="${fournisseur.id}" />
            <div class="form-group mb-4 position-relative">
              <label for="nom">Nom <span class="text-danger">*</span></label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-user"></i></span></div>
                <input type="text" id="nom" name="nom" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" required value="${fournisseur.nom}" autofocus aria-required="true" aria-describedby="nomHelp" />
              </div>
              <small id="nomHelp" class="form-text text-muted">Ex : Société ABC</small>
            </div>
            <div class="form-group mb-4 position-relative">
              <label for="adresse">Adresse <span class="text-danger">*</span></label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-location-pin"></i></span></div>
                <input type="text" id="adresse" name="adresse" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" required value="${fournisseur.adresse}" aria-required="true" aria-describedby="adresseHelp" />
              </div>
              <small id="adresseHelp" class="form-text text-muted">Adresse complète du fournisseur</small>
            </div>
            <div class="form-group mb-4 position-relative">
              <label for="telephone">Téléphone</label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-mobile"></i></span></div>
                <input type="text" id="telephone" name="telephone" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" value="${fournisseur.telephone}" aria-describedby="telHelp" />
              </div>
              <small id="telHelp" class="form-text text-muted">Format : 032 12 345 67</small>
            </div>
            <div class="form-group mb-4 position-relative">
              <label for="email">Email</label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-email"></i></span></div>
                <input type="email" id="email" name="email" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" value="${fournisseur.email}" aria-describedby="emailHelp" />
              </div>
              <small id="emailHelp" class="form-text text-muted">exemple@domaine.com</small>
            </div>
            <div class="form-group mb-4 position-relative">
              <label for="image">Image</label>
              <div class="input-group">
                <div class="input-group-prepend"><span class="input-group-text"><i class="ti-image"></i></span></div>
                <input type="text" id="image" name="image" class="form-control form-control-lg" style="width:100%;min-width:350px;max-width:700px;" value="${fournisseur.image}" aria-describedby="imgHelp" />
              </div>
              <small id="imgHelp" class="form-text text-muted">Nom du fichier (ex : logo.png)</small>
            </div>
            <button type="submit" class="btn btn-gradient-primary btn-lg w-100 rounded-pill py-3 shadow">Enregistrer les modifications</button>
            <c:if test="${not empty success}">
              <div class="alert alert-success mt-3 text-center" role="alert">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
              <div class="alert alert-danger mt-3 text-center" role="alert">${error}</div>
            </c:if>
          </form>
        </div>
      </div>
    </div>
      <%@ include file="../includes/footer.jsp" %>
  </div>
</body>
</html>
