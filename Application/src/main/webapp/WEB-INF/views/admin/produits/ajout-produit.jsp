<%@ include file="../includes/head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ajout-produit.css">
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
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
      <%@ include file="../includes/sidebar.jsp" %>
      <!-- partial -->
      <div class="main-panel">
        <h1>Ajout produit:</h1>
        <form action="/admin/ajout-produit" method="post" enctype="multipart/form-data">
          <label for="nom">Nom du produit:</label>
          <input type="text" name="nom" id="nom" placeholder="Nom du produit" required>
          <label for="fournisseur">Fournisseur:</label>
          <select name="fournisseur" id="fournisseur" required>
            <option value="">-- Sélectionner un fournisseur --</option>
            <c:forEach var="fournisseur" items="${fournisseurs}">
              <option value="${fournisseur.id}">${fournisseur.nom}</option>
            </c:forEach>
          </select>
          <label for="description">Description :</label>
          <input type="text" name="description" id="description" placeholder="Description du produit">
          <label for="categorie">Categorie:</label>
          <select name="categorie" id="categorie" required>
            <option value="">-- Sélectionner une catégorie --</option>
            <c:forEach var="categorie" items="${categories}">
              <option value="${categorie.id}">${categorie.libelle}</option>
            </c:forEach>
          </select>
          <label for="unite_mesure">Unité de mesure:</label>
          <select name="unite_mesure" id="unite_mesure" required>
            <option value="kg">Par kilo</option>
            <option value="pc">Par piece</option>
          </select>
          <label for="prix_achat">Prix d'achat:</label>
          <input type="number" step="0.01" name="prix_achat" id="prix_achat" placeholder="Prix d'achat" required>
          <label for="prix_vente">Prix de vente:</label>
          <input type="number" step="0.01" name="prix_vente" id="prix_vente" placeholder="Prix de vente" required>
          <label for="image">Image:</label>
          <input type="file" name="image" id="image" accept="image/*">
          <button type="submit" class="btn btn-primary">Ajouter</button>
        </form>
        <!-- partial:partials/_footer.html -->
      <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>
</body>

</html>

