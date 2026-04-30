<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  java.time.LocalDate now = java.time.LocalDate.now();
  java.time.LocalDate nextMonth = now.plusMonths(1);
  request.setAttribute("dateDebut", now.toString());
  request.setAttribute("dateFin", nextMonth.toString());
%>
<!DOCTYPE html>
<html>
<head>
  <title>Lokaly</title>
    <link rel="stylesheet" href="/static/css/historique-achat.css"/>
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
</head>
<body class="sidebar-light">

            <div class="container-fluid page-body-wrapper">
                <!-- partial:partials/_navbar.html -->
  <%@ include file="includes/head.jsp" %>
<nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
  <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
    <a class="navbar-brand brand-logo mr-5" href="/produits_liste"><img src="images/lokaly.png" class="mr-2" alt="logo" /></a>
    <a class="navbar-brand brand-logo-mini" href="/produits_liste"><img src="images/logo-mini.svg" alt="logo" /></a>
  </div>
  <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
    <ul class="navbar-nav mr-lg-2">
      <li class="nav-item nav-search d-none d-lg-block">
      </li>
    </ul> 
    <ul class="navbar-nav navbar-nav-right">
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
        <li class="nav-item nav-profile dropdown">
          <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" >
            <form class="form-inline" action="/historiqueCommandes" method="get">
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
    </ul>
    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
      data-toggle="offcanvas">
      <span class="icon-menu"></span>
    </button>
  </div>
</nav>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
                    <nav class="sidebar sidebar-offcanvas" id="sidebar">
                        <ul class="nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/monprofil">
                                    <i class="icon-grid menu-icon"></i>
                                    <span class="menu-title">Mon profil</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="/historiqueCommandes">
                                <%-- /client/${sessionScope['client-connected'].idClient}/commandes --%>
                                    <i class="icon-paper menu-icon"></i>
                                    <span class="menu-title">Commandes Clients</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="/historique-achat">
                                    <i class="icon-paper menu-icon"></i>
                                    <span class="menu-title">Historique de paiement</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
<div class="container">
    <div class="page-header">
        <h1 class="page-title">
            <span class="icon">📋</span>
            Historique de paiement
        </h1>
    </div>

    <form method="get" action="/historique-achat" class="filter-form">
        <div class="filter-grid">
            <div class="form-group">
            <label for="dateDebut">Date début</label>
            <input type="date" id="dateDebut" name="dateDebut" class="form-control" value="${dateDebut}" />
          </div>
          <div class="form-group">
            <label for="dateFin">Date fin</label>
            <input type="date" id="dateFin" name="dateFin" class="form-control" value="${dateFin}" />
          </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    <i class="mdi mdi-filter-variant"></i> Filtrer
                </button>
            </div>
        </div>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <c:if test="${empty historique}">
        <div class="empty-state">
            <div class="empty-state-icon">🛒</div>
            <h3 class="empty-state-title">Aucun achat trouvé</h3>
            <p class="empty-state-text">Vous n'avez effectué aucun achat dans la période sélectionnée.</p>
        </div>
    </c:if>

    <c:if test="${not empty historique}">
        <div class="timeline">
            <c:forEach var="achat" items="${historique}">
                <div class="achat-card">
                    <div class="timeline-dot">
                        <i class="mdi mdi-check-circle"></i>
                    </div>
                    <div class="achat-header">
                        <div class="achat-date">
                            <i class="mdi mdi-calendar"></i> ${achat.dateCommande}
                        </div>
                        <div class="achat-montant">
                            <i class="mdi mdi-cash-multiple"></i> ${achat.montantTotal} Ar
                        </div>
                        <span class="badge badge-success">
                            <i class="mdi mdi-check"></i> Validée
                        </span>
                    </div>
                    <div>
                        <span style="font-weight: 600; color: var(--text-light);">Produits :</span>
                        <ul class="produit-list">
                            <c:forEach var="prod" items="${achat.produits}">
                                <li><i class="mdi mdi-cube-outline"></i> ${prod}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
</div>
</body>
</html>