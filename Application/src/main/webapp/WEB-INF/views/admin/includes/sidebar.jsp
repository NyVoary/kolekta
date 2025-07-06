<!-- partial:partials/_sidebar.html -->
      <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title">Dashboard</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
              <i class="ti-tag menu-icon"></i>
              <span class="menu-title">Produits</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="ui-basic">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/ajout-produit">Ajout produit</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/ajout-produitMois">Ajout produit du mois</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/liste-produit">Liste des produits</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/stat-produit">Statistiques</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#form-elements" aria-expanded="false" aria-controls="form-elements">
              <i class="ti-menu-alt menu-icon"></i>
              <span class="menu-title">Categories</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="form-elements">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/ajout-categorie">Ajout categorie</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/liste-categorie">Liste</a></li>
                </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#charts" aria-expanded="false" aria-controls="charts">
              <i class="icon-head menu-icon"></i>
              <span class="menu-title">Clients</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="charts">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/ajout-client">Ajout client</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/liste-client">Listes</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/statistiques-clients">Statistiques</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#tables" aria-expanded="false" aria-controls="tables" id="sidebar-fournisseurs-link">
              <i class="icon-head menu-icon"></i>
              <span class="menu-title">Fournisseurs </span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="tables">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/ajout-fournisseur">Ajout fournisseur</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/fournisseurs/liste">Liste</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/paiement-fournisseur">Paiments</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/fournisseurs/ajout-disponibilite">Ajout disponibilite</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/fournisseurs/statistique">Statistique</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#icons" aria-expanded="false" aria-controls="icons">
              <i class="icon-head menu-icon"></i>
              <span class="menu-title">Employes</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="icons">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="/admin/ajout-employe">Ajout employe</a></li>
                <li class="nav-item"> <a class="nav-link" href="/admin/liste-employe">Liste</a></li>
                <li class="nav-item"> <a class="nav-link" href="/admin/divers-employe">Gestion divers</a></li>
                <li class="nav-item"> <a class="nav-link" href="/admin/tache-employe">Assignations taches</a></li>
                <li class="nav-item"> <a class="nav-link" href="/admin/stat-employe">Statistique</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
              <i class="ti-truck menu-icon"></i>
              <span class="menu-title">Camions</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="auth">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/ajout-camion">Ajout camion</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/liste-camion">Liste</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/paiement-camion">Paiments</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/assignation-camion">Assignations camions</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#error" aria-expanded="false" aria-controls="error">
              <i class="ti-shopping-cart-full menu-icon"></i>
              <span class="menu-title">Commandes&Livraisons</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="error">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/livraison">Livraisons</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/commande">Commandes</a></li>
                <li class="nav-item"> <a class="nav-link" href="${pageContext.request.contextPath}/admin/ajout-commande">Ajout commande</a></li>
              </ul>
            </div>
          </li>
        </ul>
      </nav>
      <script>
        // Ouvre automatiquement le menu Fournisseurs si on est sur une page du module Fournisseurs (liste, ajout, edit, etc.)
        document.addEventListener('DOMContentLoaded', function() {
          var path = window.location.pathname;
          if(path.includes('/fournisseurs') || path.includes('/ajout-fournisseur')) {
            var menu = document.getElementById('tables');
            var link = document.getElementById('sidebar-fournisseurs-link');
            if(menu && link) {
              menu.classList.add('show');
              link.setAttribute('aria-expanded', 'true');
            }
          }
        });
      </script>