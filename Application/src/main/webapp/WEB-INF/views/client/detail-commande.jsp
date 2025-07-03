<%@ page import="com.example.demo.entity.Commandes, com.example.demo.entity.CommandeProduit, 
                  java.util.List, java.text.NumberFormat, java.util.Locale" %>
    <% Commandes commande=(Commandes) request.getAttribute("commande"); NumberFormat
        currencyFormat=NumberFormat.getCurrencyInstance(Locale.FRANCE); List<CommandeProduit> produits =
        commande.getProduits();
        %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <!-- Required meta tags -->
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <title>Details de commande</title>
            <!-- plugins:css -->
            <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/feather/feather.css">
            <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/ti-icons/css/themify-icons.css">
            <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/css/vendor.bundle.base.css">
            <!-- endinject -->
            <!-- Plugin css for this page -->
            <link rel="stylesheet"
                href="<%= request.getContextPath() %>/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
            <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/ti-icons/css/themify-icons.css">
            <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/select.dataTables.min.css">
            <!-- End plugin css for this page -->
            <!-- inject:css -->
            <link rel="stylesheet" href="<%= request.getContextPath() %>/css/vertical-layout-light/style.css">
            <!-- endinject -->
            <link rel="shortcut icon" href="<%= request.getContextPath() %>/images/favicon.png" />
        </head>

        <body>
            <div class="">
                <%@ include file="includes/navbar.jsp" %>
                <!-- partial -->
                    <!-- partial -->
                    <div class="" style="margin-top:10vh;">
                        <div class="content-wrapper">
                            <div class="row">
                                <div class="col-md-6 grid-margin stretch-card">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Details de la commande #<%= commande.getId() %>
                                            </h4>
                                            <div class="row">
                                                <div class="col-md-6 mb-4 stretch-card transparent">
                                                    <div class="card card-tale">
                                                        <div class="card-body">
                                                            <p class="mb-4">Date de commande</p>
                                                            <p class="fs-30 mb-2">
                                                                <%= commande.formatterDateCommande() %>
                                                            </p>
                                                            <p><%= commande.formatterDateCommandeAvecJour() %></p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 mb-4 stretch-card transparent">
                                                    <div class="card card-dark-blue">
                                                        <div class="card-body">
                                                            <p class="mb-4">Frais et Lieu de livraison</p>
                                                            <p class="fs-30 mb-2">
                                                                <%= commande.getFraisLivraison() %> Ar
                                                            </p>
                                                            <p>
                                                                <%= commande.getAdresseLivraison() %>, <%=
                                                                        commande.getVilleLivraison() %>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 mb-4 mb-lg-0 stretch-card transparent">
                                                    <div class="card card-light-blue">
                                                        <div class="card-body">
                                                            <p class="mb-4">Nombre de Produits</p>
                                                            <p class="fs-30 mb-2">
                                                                <%= produits.size() %>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 stretch-card transparent">
                                                    <div class="card card-light-danger">
                                                        <div class="card-body">
                                                            <p class="mb-4">Total</p>
                                                            <p class="fs-30 mb-2">
                                                                <%= commande.getMontantTotal() %> Ar
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 grid-margin stretch-card">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Statut de la commande</h4>
                                            <% Integer progress=(Integer) request.getAttribute("progression"); boolean
                                                isCancelled=progress !=null && progress==-1; boolean
                                                isPreparation=progress !=null && progress>=25;
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
                                                        <div
                                                            class="icon-box-lg rounded-circle <%= isPreparation ? bgActive : bgInactive %>">
                                                            <i
                                                                class="ti-package <%= isPreparation ? textActive : textInactive %>"></i>
                                                        </div>
                                                        <p class="mt-2">Preparation</p>
                                                    </div>
                                                    <div class="text-center">
                                                        <div
                                                            class="icon-box-lg rounded-circle <%= isExpedition ? bgActive : bgInactive %>">
                                                            <i
                                                                class="ti-truck <%= isExpedition ? textActive : textInactive %>"></i>
                                                        </div>
                                                        <p class="mt-2">Expedition</p>
                                                    </div>
                                                    <div class="text-center">
                                                        <div
                                                            class="icon-box-lg rounded-circle <%= isLivraison ? bgActive : bgInactive %>">
                                                            <i
                                                                class="ti-home <%= isLivraison ? textActive : textInactive %>"></i>
                                                        </div>
                                                        <p class="mt-2">Livraison</p>
                                                    </div>
                                                    <div class="text-center">
                                                        <div
                                                            class="icon-box-lg rounded-circle <%= isTermine ? bgActive : bgInactive %>">
                                                            <i
                                                                class="ti-check <%= isTermine ? textActive : textInactive %>"></i>
                                                        </div>
                                                        <p class="mt-2">Termine</p>
                                                    </div>
                                                </div>

                                                <% if (!isCancelled && progress !=null) { %>
                                                    <div class="progress">
                                                        <div class="progress-bar bg-primary" role="progressbar"
                                                            style="width: <%= progress %>%"
                                                            aria-valuenow="<%= progress %>" aria-valuemin="0"
                                                            aria-valuemax="100"></div>
                                                    </div>
                                                    <% } %>

                                                        <% if (isCancelled) { %>
                                                            <div class="text-center mt-3">
                                                                <div class="icon-box-lg rounded-circle bg-danger">
                                                                    <i class="ti-close text-white"></i>
                                                                </div>
                                                                <p class="mt-2 text-danger">Annulée</p>
                                                            </div>
                                                            <% } %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 grid-margin stretch-card">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Produits commandes</h4>
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
                                                        <% if (produits !=null) { for (CommandeProduit item : produits)
                                                            { %>
                                                            <tr>
                                                                <td class="py-1">
                                                                    <% if (item.getProduit() !=null &&
                                                                        item.getProduit().getImage() !=null &&
                                                                        !item.getProduit().getImage().isEmpty()) { %>
                                                                        <img src="<%= request.getContextPath() %>/images/produits/<%= item.getProduit().getImage() %>"
                                                                            alt="<%= item.getProduit().getNom() != null ? item.getProduit().getNom() : "" %>"
                                                                            width="50" height="50"
                                                                            class="img-rounded" />
                                                                        <% } else { %>
                                                                            <div class="bg-light rounded"
                                                                                style="width:50px;height:50px;display:flex;align-items:center;justify-content:center;">
                                                                                <i class="ti-image text-muted"></i>
                                                                            </div>
                                                                            <% } %>
                                                                </td>
                                                                <td>
                                                                    <%= item.getProduit() !=null ?
                                                                        item.getProduit().getNom() : "Produit inconnu"
                                                                        %>
                                                                </td>
                                                                <td>
                                                                    <%= item.getPrixUnitaire() %>
                                                                </td>
                                                                <td>
                                                                    <%= item.getQuantite() %>
                                                                </td>
                                                                <td>
                                                                    <%= item.getSousTotal() %>
                                                                </td>
                                                            </tr>
                                                            <% } } %>
                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <td colspan="4" class="text-right"><strong>Total :</strong>
                                                            </td>
                                                            <td><strong>
                                                                    <%= commande.getMontantTotal() %>
                                                                </strong></td>
                                                        </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <footer class="footer">
                            <div class="d-sm-flex justify-content-center justify-content-sm-between">
                                <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright ©
                                    2021. Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap
                                        admin
                                        template</a> from BootstrapDash.
                                    All rights reserved.</span>
                                <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted &
                                    made
                                    with <i class="ti-heart text-danger ml-1"></i></span>
                            </div>
                            <div class="d-sm-flex justify-content-center justify-content-sm-between">
                                <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Distributed
                                    by
                                    <a href="https://www.themewagon.com/" target="_blank">Themewagon</a></span>
                            </div>
                        </footer>
                    </div>
                </div>
                <!-- main-panel ends -->
            </div>
            <!-- page-body-wrapper ends -->

            <script src="<%= request.getContextPath() %>/vendors/js/vendor.bundle.base.js"></script>
            <!-- endinject -->
            <!-- Plugin js for this page -->
            <script src="<%= request.getContextPath() %>/vendors/chart.js/Chart.min.js"></script>
            <script src="<%= request.getContextPath() %>/vendors/datatables.net/jquery.dataTables.js"></script>
            <script src="<%= request.getContextPath() %>/vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
            <script src="<%= request.getContextPath() %>/js/dataTables.select.min.js"></script>
            <!-- End plugin js for this page -->
            <!-- inject:js -->
            <script src="<%= request.getContextPath() %>/js/off-canvas.js"></script>
            <script src="<%= request.getContextPath() %>/js/hoverable-collapse.js"></script>
            <script src="<%= request.getContextPath() %>/js/template.js"></script>
            <script src="<%= request.getContextPath() %>/js/settings.js"></script>
            <script src="<%= request.getContextPath() %>/js/todolist.js"></script>
            <!-- endinject -->
            <!-- Custom js for this page-->
            <script src="<%= request.getContextPath() %>/js/dashboard.js"></script>
            <script src="<%= request.getContextPath() %>/js/Chart.roundedBarCharts.js"></script>
        </body>

        </html>