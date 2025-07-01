<%@ include file="../includes/head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <h1>Liste des produits:</h1>
        <div class="card-deck">
          <c:forEach var="produit" items="${produits}">
          <div class="card">
            <div class="card-body">
              <c:choose>
                <c:when test="${not empty produit.image}">
                  <img src="/images/produits/${produit.image}" alt="${produit.nom}" class="card-img-top" style="height: 200px; object-fit: cover;">
                </c:when>
                <c:otherwise>
                  <img src="/images/default-product.png" alt="Image par défaut" class="card-img-top" style="height: 200px; object-fit: cover;">
                </c:otherwise>
              </c:choose>
              <h5 class="card-title">${produit.nom}</h5>
              <p class="card-text">
                <strong>Fournisseur:</strong> ${produit.fournisseurs.nom}<br>
                <strong>Catégorie:</strong> ${produit.categories.libelle}<br>
                <strong>Prix de vente:</strong> ${produit.prix_vente} €<br>
                <strong>Unité:</strong> ${produit.unite_mesure}
              </p>
              <button type="button" class="btn btn-warning btn-modifier" 
                      data-toggle="modal" 
                      data-target="#modalModifier"
                      data-id="${produit.id}"
                      data-nom="${produit.nom}"
                      data-description="${produit.description}"
                      data-fournisseur="${produit.fournisseurs.id}"
                      data-categorie="${produit.categories.id}"
                      data-unite="${produit.unite_mesure}"
                      data-prix-achat="${produit.prix_achat}"
                      data-prix-vente="${produit.prix_vente}">
                Modifier
              </button>
              <form method="post" action="/admin/supprimer-produit" style="display:inline;" 
                    onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer ce produit ?');">
                <input type="hidden" name="id" value="${produit.id}">
                <button type="submit" class="btn btn-danger">Supprimer</button>
              </form>
            </div>
          </div>
          </c:forEach>
        </div>
        <!-- partial:partials/_footer.html -->
      <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>

  <!-- Modal de modification -->
  <div class="modal fade" id="modalModifier" tabindex="-1" role="dialog" aria-labelledby="modalModifierLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalModifierLabel">Modifier le produit</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <form method="post" action="/admin/modifier-produit" enctype="multipart/form-data">
          <div class="modal-body">
            <input type="hidden" id="produitId" name="id">
            <div class="form-group">
              <label for="produitNom">Nom du produit:</label>
              <input type="text" class="form-control" id="produitNom" name="nom" required>
            </div>
            <div class="form-group">
              <label for="produitFournisseur">Fournisseur:</label>
              <select class="form-control" id="produitFournisseur" name="fournisseur" required>
                <option value="">-- Sélectionner un fournisseur --</option>
                <c:forEach var="fournisseur" items="${fournisseurs}">
                  <option value="${fournisseur.id}">${fournisseur.nom}</option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <label for="produitDescription">Description:</label>
              <input type="text" class="form-control" id="produitDescription" name="description">
            </div>
            <div class="form-group">
              <label for="produitCategorie">Catégorie:</label>
              <select class="form-control" id="produitCategorie" name="categorie" required>
                <option value="">-- Sélectionner une catégorie --</option>
                <c:forEach var="categorie" items="${categories}">
                  <option value="${categorie.id}">${categorie.libelle}</option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <label for="produitUnite">Unité de mesure:</label>
              <select class="form-control" id="produitUnite" name="unite_mesure" required>
                <option value="kg">Par kilo</option>
                <option value="pc">Par pièce</option>
              </select>
            </div>
            <div class="form-group">
              <label for="produitPrixAchat">Prix d'achat:</label>
              <input type="number" step="0.01" class="form-control" id="produitPrixAchat" name="prix_achat" required>
            </div>
            <div class="form-group">
              <label for="produitPrixVente">Prix de vente:</label>
              <input type="number" step="0.01" class="form-control" id="produitPrixVente" name="prix_vente" required>
            </div>
            <div class="form-group">
              <label for="produitImage">Image:</label>
              <input type="file" class="form-control-file" id="produitImage" name="image" accept="image/*">
              <small class="form-text text-muted">Laissez vide pour conserver l'image actuelle</small>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
            <button type="submit" class="btn btn-primary">Modifier</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      $('.btn-modifier').click(function() {
        var id = $(this).data('id');
        var nom = $(this).data('nom');
        var description = $(this).data('description');
        var fournisseur = $(this).data('fournisseur');
        var categorie = $(this).data('categorie');
        var unite = $(this).data('unite');
        var prixAchat = $(this).data('prix-achat');
        var prixVente = $(this).data('prix-vente');
        
        $('#produitId').val(id);
        $('#produitNom').val(nom);
        $('#produitDescription').val(description);
        $('#produitFournisseur').val(fournisseur);
        $('#produitCategorie').val(categorie);
        $('#produitUnite').val(unite);
        $('#produitPrixAchat').val(prixAchat);
        $('#produitPrixVente').val(prixVente);
      });
    });
  </script>
</body>

</html>

