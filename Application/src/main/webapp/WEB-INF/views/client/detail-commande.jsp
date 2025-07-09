<%@ page import="com.example.demo.entity.Commandes,
                 com.example.demo.entity.CommandeProduit,
                 java.util.List,
                 java.text.NumberFormat,
                 java.util.Locale" %>

<%
    Commandes commande = (Commandes) request.getAttribute("commande");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
    List<CommandeProduit> produits = commande.getProduits();
%>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Lokaly</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/feather/feather.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/js/select.dataTables.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/vertical-layout-light/style.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
</head>

<body>
    <div class="container-fluid page-body-wrapper">
        <%@ include file="includes/head.jsp" %>

        <!-- Navbar -->
        <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
            <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
                <a class="navbar-brand brand-logo mr-5" href="/produits_liste"><img src="images/logo.svg" class="mr-2" alt="logo" /></a>
                <a class="navbar-brand brand-logo-mini" href="/produits_liste"><img src="images/logo-mini.svg" alt="logo" /></a>
            </div>
            <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
                <ul class="navbar-nav navbar-nav-right">
                    <li class="nav-item nav-profile dropdown">
                        <form class="form-inline" action="/recherche_produits" method="get">
                            <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                                <i class="ti-search text-info"></i>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item nav-profile dropdown">
                        <form class="form-inline" action="/panier/monpanier" method="get">
                            <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                                <i class="ti-shopping-cart text-info"></i>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item nav-profile dropdown">
                        <form class="form-inline" action="/historiqueCommandes" method="get">
                            <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                                <i class="ti-user text-info"></i>
                            </button>
                        </form>
                    </li>
                    <li class="nav-item nav-profile dropdown">
                        <form class="form-inline" action="/logout" method="get">
                            <button type="submit" class="btn btn-outline-primary btn-rounded btn-icon">
                                <i class="ti-power-off"></i>
                            </button>
                        </form>
                    </li>
                </ul>
                <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                    <span class="icon-menu"></span>
                </button>
            </div>
        </nav>

        <!-- Sidebar -->
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

        <!-- Contenu principal -->
        <div class="main-panel">
            <div class="content-wrapper">

                <!-- Informations commande -->
                <div class="row">
                    <div class="col-md-6 grid-margin stretch-card">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Details de la commande #<%= commande.getId() %></h4>
                                <div class="row">
                                    <div class="col-md-6 mb-4 stretch-card transparent">
                                        <div class="card card-tale">
                                            <div class="card-body">
                                                <p class="mb-4">Date de commande</p>
                                                <p class="fs-30 mb-2"><%= commande.formatterDateCommande() %></p>
                                                <p><%= commande.formatterDateCommandeAvecJour() %></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-4 stretch-card transparent">
                                        <div class="card card-dark-blue">
                                            <div class="card-body">
                                                <p class="mb-4">Frais et Lieu de livraison</p>
                                                <p class="fs-30 mb-2"><%= commande.getFraisLivraison() %> Ar</p>
                                                <p><%= commande.getAdresseLivraison() %>, <%= commande.getVilleLivraison() %></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-4 stretch-card transparent">
                                        <div class="card card-light-blue">
                                            <div class="card-body">
                                                <p class="mb-4">Nombre de produits</p>
                                                <p class="fs-30 mb-2"><%= produits.size() %></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6 stretch-card transparent">
                                        <div class="card card-light-danger">
                                            <div class="card-body">
                                                <p class="mb-4">Total</p>
                                                <p class="fs-30 mb-2"><%= commande.getMontantTotal() %> Ar</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Statut de la commande -->
                    <div class="col-md-6 grid-margin stretch-card">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Statut de la commande</h4>

                                <%
                                    Integer progress = (Integer) request.getAttribute("progression");
                                    boolean isCancelled = progress != null && progress == -1;
                                    boolean isPreparation = progress != null && progress >= 25;
                                    boolean isExpedition = progress != null && progress >= 50;
                                    boolean isLivraison = progress != null && progress >= 75;
                                    boolean isTermine = progress != null && progress >= 100;

                                    String bgActive = "bg-primary";
                                    String bgInactive = "bg-light";
                                    String textActive = "text-white";
                                    String textInactive = "text-muted";
                                %>

                                <div class="d-flex justify-content-between mb-3">
                                    <div class="text-center">
                                        <div class="icon-box-lg rounded-circle <%= isPreparation ? bgActive : bgInactive %>">
                                            <i class="ti-package <%= isPreparation ? textActive : textInactive %>"></i>
                                        </div>
                                        <p class="mt-2">Preparation</p>
                                    </div>
                                    <div class="text-center">
                                        <div class="icon-box-lg rounded-circle <%= isExpedition ? bgActive : bgInactive %>">
                                            <i class="ti-truck <%= isExpedition ? textActive : textInactive %>"></i>
                                        </div>
                                        <p class="mt-2">Expedition</p>
                                    </div>
                                    <div class="text-center">
                                        <div class="icon-box-lg rounded-circle <%= isLivraison ? bgActive : bgInactive %>">
                                            <i class="ti-home <%= isLivraison ? textActive : textInactive %>"></i>
                                        </div>
                                        <p class="mt-2">Livraison</p>
                                    </div>
                                    <div class="text-center">
                                        <div class="icon-box-lg rounded-circle <%= isTermine ? bgActive : bgInactive %>">
                                            <i class="ti-check <%= isTermine ? textActive : textInactive %>"></i>
                                        </div>
                                        <p class="mt-2">Termine</p>
                                    </div>
                                </div>

                                <% if (!isCancelled && progress != null) { %>
                                    <div class="progress">
                                        <div class="progress-bar bg-primary" role="progressbar" style="width: <%= progress %>%;" aria-valuenow="<%= progress %>" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                <% } else if (isCancelled) { %>
                                    <div class="text-center mt-3">
                                        <div class="icon-box-lg rounded-circle bg-danger">
                                            <i class="ti-close text-white"></i>
                                        </div>
                                        <p class="mt-2 text-danger">Annulee</p>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Table produits -->
                <div class="row">
                    <div class="col-lg-12 grid-margin stretch-card">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Produits commanees</h4>
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Image</th>
                                                <th>Produit</th>
                                                <th>Prix unitaire</th>
                                                <th>Quantite</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% if (produits != null) {
                                                for (CommandeProduits item : produits) { %>
                                                    <tr>
                                                        <td class="py-1">
                                                            <% if (item.getProduit() != null && item.getProduit().getImage() != null && !item.getProduit().getImage().isEmpty()) { %>
                                                                <img src="<%= request.getContextPath() %>/images/produits/<%= item.getProduit().getImage() %>"
                                                                    alt="<%= item.getProduit().getNom() %>" width="50" height="50" class="img-rounded" />
                                                            <% } else { %>
                                                                <div class="bg-light rounded" style="width:50px;height:50px;display:flex;align-items:center;justify-content:center;">
                                                                    <i class="ti-image text-muted"></i>
                                                                </div>
                                                            <% } %>
                                                        </td>
                                                        <td><%= item.getProduit() != null ? item.getProduit().getNom() : "Produit inconnu" %></td>
                                                        <td><%= item.getPrixUnitaire() %></td>
                                                        <td><%= item.getQuantite() %></td>
                                                        <td><%= item.getSousTotal() %></td>
                                                    </tr>
                                            <% } } %>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <td colspan="4" class="text-right"><strong>Total :</strong></td>
                                                <td><strong><%= commande.getMontantTotal() %></strong></td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <!-- Footer -->
            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                    <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">
                        Copyright © 2021.
                        Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin template</a>.
                        All rights reserved.
                    </span>
                    <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">
                        Hand-crafted & made with <i class="ti-heart text-danger ml-1"></i>
                    </span>
                </div>
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                    <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">
                        Distributed by <a href="https://www.themewagon.com/" target="_blank">Themewagon</a>
                    </span>
                </div>
            </footer>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<%= request.getContextPath() %>/vendors/js/vendor.bundle.base.js"></script>
    <script src="<%= request.getContextPath() %>/vendors/chart.js/Chart.min.js"></script>
    <script src="<%= request.getContextPath() %>/vendors/datatables.net/jquery.dataTables.js"></script>
    <script src="<%= request.getContextPath() %>/vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
    <script src="<%= request.getContextPath() %>/js/dataTables.select.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/off-canvas.js"></script>
    <script src="<%= request.getContextPath() %>/js/hoverable-collapse.js"></script>
    <script src="<%= request.getContextPath() %>/js/template.js"></script>
    <script src="<%= request.getContextPath() %>/js/settings.js"></script>
    <script src="<%= request.getContextPath() %>/js/todolist.js"></script>
    <script src="<%= request.getContextPath() %>/js/dashboard.js"></script>
    <script src="<%= request.getContextPath() %>/js/Chart.roundedBarCharts.js"></script>
</body>

</html>
