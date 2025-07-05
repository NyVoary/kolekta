<!-- partial:partials/_navbar.html -->
  <%@ include file="head.jsp" %>
<nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
  <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
    <a class="navbar-brand brand-logo mr-5" href="/produits_liste"><img src="images/logo.svg" class="mr-2" alt="logo" /></a>
    <a class="navbar-brand brand-logo-mini" href="/produits_liste"><img src="images/logo-mini.svg" alt="logo" /></a>
  </div>
  <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
    <ul class="navbar-nav mr-lg-2">
      <li class="nav-item nav-search d-none d-lg-block">
      </li>
    </ul> 
    <ul class="navbar-nav navbar-nav-right">
    <%-- notifications  --%>
      <!-- <li class="nav-item dropdown">
        <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#" data-toggle="dropdown">
          <i class="icon-bell mx-0"></i>
          <span class="count"></span>
        </a>
        <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
          aria-labelledby="notificationDropdown">
          <p class="mb-0 font-weight-normal float-left dropdown-header">Notifications</p>
          <a class="dropdown-item preview-item">
            <div class="preview-thumbnail">
              <div class="preview-icon bg-success">
                <i class="ti-info-alt mx-0"></i>
              </div>
            </div>
            <div class="preview-item-content">
              <h6 class="preview-subject font-weight-normal">Application Error</h6>
              <p class="font-weight-light small-text mb-0 text-muted">
                Just now
              </p>
            </div>
          </a>
          <a class="dropdown-item preview-item">
            <div class="preview-thumbnail">
              <div class="preview-icon bg-warning">
                <i class="ti-settings mx-0"></i>
              </div>
            </div>
            <div class="preview-item-content">
              <h6 class="preview-subject font-weight-normal">Settings</h6>
              <p class="font-weight-light small-text mb-0 text-muted">
                Private message
              </p>
            </div>
          </a>
          <a class="dropdown-item preview-item">
            <div class="preview-thumbnail">
              <div class="preview-icon bg-info">
                <i class="ti-user mx-0"></i>
              </div>
            </div>
            <div class="preview-item-content">
              <h6 class="preview-subject font-weight-normal">New user registration</h6>
              <p class="font-weight-light small-text mb-0 text-muted">
                2 days ago
              </p>
            </div>
          </a>
        </div>
      </li> -->
      <li class="nav-item nav-profile dropdown">
        <a class="nav-link dropdown-toggle" href="/recherche_produits" data-toggle="dropdown" >
        <form class="form-inline" action="/recherche_produits" method="get">
          <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                            <i class="ti-search text-info"></i>                          
          </button>
          </form>
        </a>
      </li>
      <li class="nav-item nav-profile dropdown">
        <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" >
        <form class="form-inline" action="/panier/monpanier" method="get">
          <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                <i class="ti-shopping-cart text-info"></i>                     
          </button>
          </form>
        </a>
      </li>
      <!-- Afficher le profil seulement si le client est connecté -->
      <c:if test="${sessionScope['client-connected'] != null}">
        <li class="nav-item nav-profile dropdown">
          <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" >
            <form class="form-inline" action="/monprofil" method="get">
              <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                  <i class="ti-user text-info"></i>
              </button>
            </form>
          </a>
      </li>
      <li class="nav-item nav-profile dropdown">
        <form class="form-inline" action="/logout" method="get">
          <button type="submit" class="btn btn-outline-primary btn-rounded btn-icon">
              <i class="ti-power-off"></i>
          </button>
        </form>
      </li>
      </c:if>
      <!-- Optionnel : Afficher un bouton de connexion si pas connecté -->
      <c:if test="${sessionScope['client-connected'] == null}">
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/login-client">
            <i class="ti-user"></i>
            Se connecter
          </a>
        </li>
      </c:if>
    </ul>
    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
      data-toggle="offcanvas">
      <span class="icon-menu"></span>
    </button>
  </div>
</nav>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>