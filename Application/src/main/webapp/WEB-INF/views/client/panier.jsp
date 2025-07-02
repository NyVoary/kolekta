<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.Panier" %>
<%@ page import="com.example.demo.entity.PanierProduit" %>
<%@ page import="com.example.demo.entity.Produit" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.DecimalFormat" %>

<%
    DecimalFormat formatter = new DecimalFormat("#,##0.00");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Mon Panier - Fournisseurs Agricoles</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/datatables.net-bs4/dataTables.bootstrap4.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" />
    <%@ include file="includes/head.jsp" %>
    <style>
        .panier-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .panier-item {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 15px;
            padding: 20px;
            transition: transform 0.2s;
        }
        .panier-item:hover {
            transform: translateY(-2px);
        }
        .produit-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
        }
        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .quantity-btn {
            background: #007bff;
            color: white;
            border: none;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: background 0.2s;
        }
        .quantity-btn:hover {
            background: #0056b3;
        }
        .quantity-input {
            width: 60px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 5px;
        }
        .resume-commande {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            padding: 25px;
            position: sticky;
            top: 20px;
        }
        .btn-commander {
            background: linear-gradient(45deg, #28a745, #20c997);
            border: none;
            padding: 12px 30px;
            border-radius: 25px;
            color: white;
            font-weight: bold;
            width: 100%;
            transition: transform 0.2s;
        }
        .btn-commander:hover {
            transform: scale(1.05);
            color: white;
        }
        .panier-vide {
            text-align: center;
            padding: 60px 20px;
        }
        .panier-vide i {
            font-size: 80px;
            color: #ddd;
            margin-bottom: 20px;
        }
        .alert-custom {
            border-radius: 10px;
            border: none;
            padding: 15px 20px;
        }
    </style>
</head>
<body class="bg-light">
<div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
    <!-- partial:partials/_navbar.html -->
    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
      <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
        <a class="navbar-brand brand-logo mr-5" href="index.html"><img src="images/logo.svg" class="mr-2" alt="logo"/></a>
        <a class="navbar-brand brand-logo-mini" href="index.html"><img src="images/logo-mini.svg" alt="logo"/></a>
      </div>
      <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
        <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
          <span class="icon-menu"></span>
        </button>
        <ul class="navbar-nav mr-lg-2">
          <li class="nav-item nav-search d-none d-lg-block">
            <div class="input-group">
              <div class="input-group-prepend hover-cursor" id="navbar-search-icon">
                <span class="input-group-text" id="search">
                  <i class="icon-search"></i>
                </span>
              </div>
              <input type="text" class="form-control" id="navbar-search-input" placeholder="Search now" aria-label="search" aria-describedby="search">
            </div>
          </li>
        </ul>
        <ul class="navbar-nav navbar-nav-right">
          <li class="nav-item dropdown">
            <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#" data-toggle="dropdown">
              <i class="icon-bell mx-0"></i>
              <span class="count"></span>
            </a>
            <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="notificationDropdown">
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
          </li>
          <li class="nav-item nav-profile dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
              <img src="images/faces/face28.jpg" alt="profile"/>
            </a>
            <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
              <a class="dropdown-item">
                <i class="ti-settings text-primary"></i>
                Settings
              </a>
              <a class="dropdown-item">
                <i class="ti-power-off text-primary"></i>
                Logout
              </a>
            </div>
          </li>
          <li class="nav-item nav-settings d-none d-lg-flex">
            <a class="nav-link" href="#">
              <i class="icon-ellipsis"></i>
            </a>
          </li>
        </ul>
        <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
          <span class="icon-menu"></span>
        </button>
      </div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:partials/_settings-panel.html -->
      <div class="theme-setting-wrapper">
        <div id="settings-trigger"><i class="ti-settings"></i></div>
        <div id="theme-settings" class="settings-panel">
          <i class="settings-close ti-close"></i>
          <p class="settings-heading">SIDEBAR SKINS</p>
          <div class="sidebar-bg-options selected" id="sidebar-light-theme"><div class="img-ss rounded-circle bg-light border mr-3"></div>Light</div>
          <div class="sidebar-bg-options" id="sidebar-dark-theme"><div class="img-ss rounded-circle bg-dark border mr-3"></div>Dark</div>
          <p class="settings-heading mt-2">HEADER SKINS</p>
          <div class="color-tiles mx-0 px-4">
            <div class="tiles success"></div>
            <div class="tiles warning"></div>
            <div class="tiles danger"></div>
            <div class="tiles info"></div>
            <div class="tiles dark"></div>
            <div class="tiles default"></div>
          </div>
        </div>
      </div>
      <div id="right-sidebar" class="settings-panel">
        <i class="settings-close ti-close"></i>
        <ul class="nav nav-tabs border-top" id="setting-panel" role="tablist">
          <li class="nav-item">
            <a class="nav-link active" id="todo-tab" data-toggle="tab" href="#todo-section" role="tab" aria-controls="todo-section" aria-expanded="true">TO DO LIST</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" id="chats-tab" data-toggle="tab" href="#chats-section" role="tab" aria-controls="chats-section">CHATS</a>
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
            <h4 class="px-3 text-muted mt-5 font-weight-light mb-0">Events</h4>
            <div class="events pt-4 px-3">
              <div class="wrapper d-flex mb-2">
                <i class="ti-control-record text-primary mr-2"></i>
                <span>Feb 11 2018</span>
              </div>
              <p class="mb-0 font-weight-thin text-gray">Creating component page build a js</p>
              <p class="text-gray mb-0">The total number of sessions</p>
            </div>
            <div class="events pt-4 px-3">
              <div class="wrapper d-flex mb-2">
                <i class="ti-control-record text-primary mr-2"></i>
                <span>Feb 7 2018</span>
              </div>
              <p class="mb-0 font-weight-thin text-gray">Meeting with Alisa</p>
              <p class="text-gray mb-0 ">Call Sarah Graves</p>
            </div>
          </div>
          <!-- To do section tab ends -->
          <div class="tab-pane fade" id="chats-section" role="tabpanel" aria-labelledby="chats-section">
            <div class="d-flex align-items-center justify-content-between border-bottom">
              <p class="settings-heading border-top-0 mb-3 pl-3 pt-0 border-bottom-0 pb-0">Friends</p>
              <small class="settings-heading border-top-0 mb-3 pt-0 border-bottom-0 pb-0 pr-3 font-weight-normal">See All</small>
            </div>
            <ul class="chat-list">
              <li class="list active">
                <div class="profile"><img src="images/faces/face1.jpg" alt="image"><span class="online"></span></div>
                <div class="info">
                  <p>Thomas Douglas</p>
                  <p>Available</p>
                </div>
                <small class="text-muted my-auto">19 min</small>
              </li>
              <li class="list">
                <div class="profile"><img src="images/faces/face2.jpg" alt="image"><span class="offline"></span></div>
                <div class="info">
                  <div class="wrapper d-flex">
                    <p>Catherine</p>
                  </div>
                  <p>Away</p>
                </div>
                <div class="badge badge-success badge-pill my-auto mx-2">4</div>
                <small class="text-muted my-auto">23 min</small>
              </li>
              <li class="list">
                <div class="profile"><img src="images/faces/face3.jpg" alt="image"><span class="online"></span></div>
                <div class="info">
                  <p>Daniel Russell</p>
                  <p>Available</p>
                </div>
                <small class="text-muted my-auto">14 min</small>
              </li>
              <li class="list">
                <div class="profile"><img src="images/faces/face4.jpg" alt="image"><span class="offline"></span></div>
                <div class="info">
                  <p>James Richardson</p>
                  <p>Away</p>
                </div>
                <small class="text-muted my-auto">2 min</small>
              </li>
              <li class="list">
                <div class="profile"><img src="images/faces/face5.jpg" alt="image"><span class="online"></span></div>
                <div class="info">
                  <p>Madeline Kennedy</p>
                  <p>Available</p>
                </div>
                <small class="text-muted my-auto">5 min</small>
              </li>
              <li class="list">
                <div class="profile"><img src="images/faces/face6.jpg" alt="image"><span class="online"></span></div>
                <div class="info">
                  <p>Sarah Graves</p>
                  <p>Available</p>
                </div>
                <small class="text-muted my-auto">47 min</small>
              </li>
            </ul>
          </div>
          <!-- chat tab ends -->
        </div>
      </div>
      <!-- partial -->
      <!-- partial:partials/_sidebar.html -->
      <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
          <li class="nav-item">
            <a class="nav-link" href="index.html">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title">Dashboard</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
              <i class="icon-layout menu-icon"></i>
              <span class="menu-title">UI Elements</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="ui-basic">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="pages/ui-features/buttons.html">Buttons</a></li>
                <li class="nav-item"> <a class="nav-link" href="pages/ui-features/dropdowns.html">Dropdowns</a></li>
                <li class="nav-item"> <a class="nav-link" href="pages/ui-features/typography.html">Typography</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#form-elements" aria-expanded="false" aria-controls="form-elements">
              <i class="icon-columns menu-icon"></i>
              <span class="menu-title">Form elements</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="form-elements">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"><a class="nav-link" href="pages/forms/basic_elements.html">Basic Elements</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#charts" aria-expanded="false" aria-controls="charts">
              <i class="icon-bar-graph menu-icon"></i>
              <span class="menu-title">Charts</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="charts">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="pages/charts/chartjs.html">ChartJs</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#tables" aria-expanded="false" aria-controls="tables">
              <i class="icon-grid-2 menu-icon"></i>
              <span class="menu-title">Tables</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="tables">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="pages/tables/basic-table.html">Basic table</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#icons" aria-expanded="false" aria-controls="icons">
              <i class="icon-contract menu-icon"></i>
              <span class="menu-title">Icons</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="icons">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="pages/icons/mdi.html">Mdi icons</a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
              <i class="icon-head menu-icon"></i>
              <span class="menu-title">User Pages</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="auth">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="pages/samples/login.html"> Login </a></li>
                <li class="nav-item"> <a class="nav-link" href="pages/samples/register.html"> Register </a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#error" aria-expanded="false" aria-controls="error">
              <i class="icon-ban menu-icon"></i>
              <span class="menu-title">Error pages</span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="error">
              <ul class="nav flex-column sub-menu">
                <li class="nav-item"> <a class="nav-link" href="pages/samples/error-404.html"> 404 </a></li>
                <li class="nav-item"> <a class="nav-link" href="pages/samples/error-500.html"> 500 </a></li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="pages/documentation/documentation.html">
              <i class="icon-paper menu-icon"></i>
              <span class="menu-title">Documentation</span>
            </a>
          </li>
        </ul>
      </nav>
      
    <div class="panier-container">
        <div class="row mb-4">
            <div class="col-12">
                <h2 class="text-center mb-0">
                    <i class="fas fa-shopping-cart text-primary"></i> Mon Panier
                </h2>
                <p class="text-center text-muted">
                    <%
                        Integer nombreArticles = (Integer) request.getAttribute("nombreArticles");
                        if (nombreArticles != null && nombreArticles > 0) {
                    %>
                        <%= nombreArticles %> article(s) dans votre panier
                    <%
                        } else {
                    %>
                        Votre panier est vide
                    <%
                        }
                    %>
                </p>
            </div>
        </div>

        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% if (successMessage != null) { %>
            <div class="alert alert-success alert-custom alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> <%= successMessage %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-danger alert-custom alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle"></i> <%= errorMessage %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="row">
            <div class="col-lg-8">
                <%
                    Panier panier = (Panier) request.getAttribute("panier");
                    if (panier != null && panier.getPanierProduits() != null && !panier.getPanierProduits().isEmpty()) {
                %>
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Articles dans votre panier</h5>
                    <form action="/panier/vider" method="post">
                        <button type="submit" class="btn btn-outline-danger btn-sm"
                                onclick="return confirm('Êtes-vous sûr de vouloir vider votre panier ?')">
                            <i class="fas fa-trash"></i> Vider le panier
                        </button>
                    </form>
                </div>

                <% for (PanierProduit panierProduit : panier.getPanierProduits()) {
                    Produit produit = panierProduit.getProduit();
                %>
                <div class="panier-item">
                    <div class="row align-items-center">
                        <div class="col-md-2 text-center">
                            <% if (produit.getImage() != null && !produit.getImage().isEmpty()) { %>
                                <img src="/images/produits/<%= produit.getImage() %>"
                                     alt="<%= produit.getNom() %>" class="produit-image" />
                            <% } else { %>
                                <div class="produit-image d-flex align-items-center justify-content-center bg-light">
                                    <i class="fas fa-image text-muted"></i>
                                </div>
                            <% } %>
                        </div>
                        <div class="col-md-4">
                            <h6 class="mb-1"><%= produit.getNom() %></h6>
                            <p class="text-muted small mb-0"><%= produit.getDescription() %></p>
                            <small class="text-info">Unité: <%= produit.getUniteMesure() %></small>
                        </div>
                        <div class="col-md-2 text-center">
                            <strong><%= formatter.format(panierProduit.getPrixUnitaire()) %> Ar</strong>
                        </div>
                        <div class="col-md-2">
                            <div class="quantity-controls justify-content-center">
                                <form action="/panier/modifier-quantite" method="post" style="display:inline;">
                                    <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>">
                                    <input type="hidden" name="quantite" value="<%= panierProduit.getQuantite() - 1 %>">
                                    <button type="submit" class="quantity-btn" <%= panierProduit.getQuantite() <= 1 ? "disabled" : "" %>>
                                        <i class="fas fa-minus"></i>
                                    </button>
                                </form>
                                <!-- Formulaire dédié pour l'input quantité -->
                                <form method="post" action="/panier/modifier-quantite" style="display:inline;">
                                    <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>" />
                                    <input type="number" name="quantite" class="quantity-input" min="1"
                                           value="<%= panierProduit.getQuantite() %>"
                                           onchange="this.form.submit()" />
                                </form>
                                <form action="/panier/modifier-quantite" method="post" style="display:inline;">
                                    <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>">
                                    <input type="hidden" name="quantite" value="<%= panierProduit.getQuantite() + 1 %>">
                                    <button type="submit" class="quantity-btn">
                                        <i class="fas fa-plus"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                        <div class="col-md-2 text-end">
                            <div class="mb-2">
                                <strong class="text-success">
                                    <%= formatter.format(panierProduit.getPrixUnitaire().multiply(new BigDecimal(panierProduit.getQuantite()))) %> Ar
                                </strong>
                            </div>
                            <form action="/panier/supprimer" method="post">
                                <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>" />
                                <button type="submit" class="btn btn-outline-danger btn-sm"
                                        onclick="return confirm('Supprimer ce produit du panier ?')">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } else { %>
                <div class="panier-vide">
                    <i class="fas fa-shopping-cart"></i>
                    <h4>Votre panier est vide</h4>
                    <p class="text-muted">Découvrez nos produits et commencez vos achats !</p>
                    <a href="/produits" class="btn btn-primary btn-lg">
                        <i class="fas fa-store"></i> Voir nos produits
                    </a>
                </div>
                <% } %>
            </div>

            <div class="col-lg-4">
                <div class="resume-commande">
                    <h5 class="mb-4"><i class="fas fa-receipt"></i> Résumé de la commande</h5>
                    <%
                        BigDecimal montantHT = (BigDecimal) request.getAttribute("montantHT");
                        BigDecimal tva = (BigDecimal) request.getAttribute("tva");
                        BigDecimal montantTTC = (BigDecimal) request.getAttribute("montantTTC");
                        BigDecimal fraisLivraison = (BigDecimal) request.getAttribute("fraisLivraison");
                        BigDecimal montantTotal = (BigDecimal) request.getAttribute("montantTotal");
                    %>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Sous-total HT:</span>
                        <strong><%= formatter.format(montantHT) %> Ar</strong>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>TVA (20%):</span>
                        <strong><%= formatter.format(tva) %> Ar</strong>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Total TTC:</span>
                        <strong><%= formatter.format(montantTTC) %> Ar</strong>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span>Frais de livraison:</span>
                        <strong><%= formatter.format(fraisLivraison) %> Ar</strong>
                    </div>
                    <hr class="bg-white" />
                    <div class="d-flex justify-content-between mb-4">
                        <span class="h5">TOTAL:</span>
                        <span class="h5"><%= formatter.format(montantTotal) %> Ar</span>
                    </div>

                    <% if (nombreArticles != null && nombreArticles > 0) { %>
                        <form action="/panier/valider" method="post">
                            <button type="submit" class="btn-commander">
                                <i class="fas fa-credit-card"></i> Valider la commande
                            </button>
                        </form>
                        <%-- ty lessy mila ovaina kel mba his-adapte anle url a --%>
                        <div class="mt-3 text-center">
                            <a href="/produits" class="btn btn-outline-light">
                                <i class="fas fa-arrow-left" style="color:white"></i> Continuer mes achats
                            </a>
                        </div>
                    <% } else { %>
                        <div class="text-center">
                            <p class="mb-3">Ajoutez des produits à votre panier pour continuer</p>
                            <a href="/produits" class="btn btn-outline-light">
                                <i class="fas fa-store"></i> Voir nos produits
                            </a>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function updateQuantity(idProduit, quantity) {
            if (quantity < 1) {
                quantity = 1;
            }

            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/panier/modifier-quantite';

            const inputIdProduit = document.createElement('input');
            inputIdProduit.type = 'hidden';
            inputIdProduit.name = 'idProduit';
            inputIdProduit.value = idProduit;

            const inputQuantite = document.createElement('input');
            inputQuantite.type = 'hidden';
            inputQuantite.name = 'quantite';
            inputQuantite.value = quantity;

            form.appendChild(inputIdProduit);
            form.appendChild(inputQuantite);
            document.body.appendChild(form);
            form.submit();
        }

        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
    </div>
</div>
      <%@ include file="includes/footer.jsp" %>
        <!-- partial -->
      </div>
      <!-- main-panel ends -->
    </div>   
    <!-- page-body-wrapper ends -->
  </div>
  <!-- container-scroller -->

  <!-- plugins:js -->
  <script src="${pageContext.request.contextPath}/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page -->
  <script src="${pageContext.request.contextPath}/vendors/chart.js/Chart.min.js"></script>
  <script src="${pageContext.request.contextPath}/vendors/datatables.net/jquery.dataTables.js"></script>
  <script src="${pageContext.request.contextPath}/vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
  <script src="${pageContext.request.contextPath}/js/dataTables.select.min.js"></script>

  <!-- End plugin js for this page -->
  <!-- inject:js -->
  <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
  <script src="${pageContext.request.contextPath}/js/hoverable-collapse.js"></script>
  <script src="${pageContext.request.contextPath}/js/template.js"></script>
  <script src="${pageContext.request.contextPath}/js/settings.js"></script>
  <script src="${pageContext.request.contextPath}/js/todolist.js"></script>
  <!-- endinject -->
  <!-- Custom js for this page-->
  <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
  <script src="${pageContext.request.contextPath}/js/Chart.roundedBarCharts.js"></script>
  <!-- End custom js for this page-->
</body>
</html>
