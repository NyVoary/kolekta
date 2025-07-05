<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,
                 com.example.demo.dto.response.CommandeClientResponse,
                 com.example.demo.dto.request.FilterCommandesRequest,
                 com.example.demo.entity.Status,
                 java.text.DecimalFormat,
                 java.text.DecimalFormatSymbols,
                 java.util.Locale" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Gestion des Commandes</title>
    <link rel="stylesheet" href="../../vendors/feather/feather.css">
    <link rel="stylesheet" href="../../vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="../../vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="../../css/vertical-layout-light/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
</head>

<body>
    <div class="container-fluid page-body-wrapper">
        <%@ include file="includes/head.jsp" %>

        <!-- Navbar -->
        <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
            <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
                <a class="navbar-brand brand-logo mr-5" href="/produits_liste"><img src="images/lokaly.png" class="mr-2" alt="logo" /></a>
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

        <!-- Main content -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="row">
                    <!-- Filtres -->
                    <div class="col-12 grid-margin stretch-card">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Filtres des Commandes</h4>
                                <form method="get" action="/historiquesCommandes" class="forms-sample">
                                    <%
                                        FilterCommandesRequest filters = (FilterCommandesRequest) request.getAttribute("filters");
                                        if (filters == null) filters = new FilterCommandesRequest();
                                    %>
                                    <div class="form-group row">
                                        <div class="col-md-3">
                                            <label>Statut</label>
                                            <select class="form-control" name="statut">
                                                <option value="">Tous les statuts</option>
                                                <%
                                                    List<Status> allStatus = (List<Status>) request.getAttribute("allStatus");
                                                    String currentStatut = filters.getStatut() != null ? filters.getStatut() : "";
                                                    if (allStatus != null) {
                                                        for (Status status : allStatus) {
                                                            String selected = status.getLibelle().equals(currentStatut) ? "selected" : "";
                                                %>
                                                <option value="<%= status.getLibelle() %>" <%= selected %>><%= status.getLibelle() %></option>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <label>Date début</label>
                                            <input type="date" class="form-control" name="dateFrom"
                                                value="<%= filters.getDateFrom() != null ? filters.getDateFrom() : "" %>">
                                        </div>
                                        <div class="col-md-3">
                                            <label>Date fin</label>
                                            <input type="date" class="form-control" name="dateTo"
                                                value="<%= filters.getDateTo() != null ? filters.getDateTo() : "" %>">
                                        </div>
                                        <div class="col-md-3 align-self-end">
                                            <button type="submit" class="btn btn-primary mr-2">Filtrer</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Liste des commandes -->
                    <div class="col-12 grid-margin stretch-card">
                        <%!
                            private String formatMoney(Double amount) {
                                DecimalFormat formatter = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.US));
                                return formatter.format(amount.doubleValue());
                            }
                        %>
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Liste des Commandes</h4>
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Numéro</th>
                                                <th>Date</th>
                                                <th>Total à payer</th>
                                                <th>Montant payé</th>
                                                <th>Reste à payer</th>
                                                <th>Livraison</th>
                                                <th>Statut</th>
                                                <th>Détails</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                List<CommandeClientResponse> commandes = (List<CommandeClientResponse>) request.getAttribute("commandes");
                                                if (commandes != null && !commandes.isEmpty()) {
                                                    for (CommandeClientResponse commande : commandes) {
                                                        Double reste = commande.getMontantRestant();
                                                        String resteClass = reste.compareTo(0.0) > 0 ? "text-danger" : "text-success";
                                                        String statut = commande.getStatutActuel();
                                                        String badgeClass = "badge-warning";
                                                        if ("LIVREE".equals(statut)) badgeClass = "badge-success";
                                                        else if ("ANNULEE".equals(statut)) badgeClass = "badge-danger";
                                            %>
                                            <tr>
                                                <td><%= commande.getIdCommande() %></td>
                                                <td><%= commande.getDateCommande() %></td>
                                                <td><%= formatMoney(commande.getMontantTotal()) %></td>
                                                <td><%= formatMoney(commande.getMontantPaye()) %></td>
                                                <td><span class="<%= resteClass %>"><%= formatMoney(reste) %> Ar</span></td>
                                                <td><%= commande.getVilleLivraison() %></td>
                                                <td><label class="badge <%= badgeClass %>"><%= statut %></label></td>
                                                <td>
                                                    <form action="/commande/commandes/<%= commande.getIdCommande() %>" method="get">
                                                        <input class="form-control" type="submit" value="Détails">
                                                    </form>
                                                </td>
                                            </tr>
                                            <% } } else { %>
                                            <tr>
                                                <td colspan="8" class="text-center">Aucune commande trouvée</td>
                                            </tr>
                                            <% } %>
                                        </tbody>
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
            </footer>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../../vendors/js/vendor.bundle.base.js"></script>
    <script src="../../js/off-canvas.js"></script>
    <script src="../../js/hoverable-collapse.js"></script>
    <script src="../../js/template.js"></script>
    <script src="../../js/settings.js"></script>
    <script src="../../js/todolist.js"></script>
</body>

</html>
