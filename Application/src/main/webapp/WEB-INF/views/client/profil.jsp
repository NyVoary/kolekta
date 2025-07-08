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
            <title>Library</title>
            <link rel="stylesheet" href="../../vendors/feather/feather.css">
            <link rel="stylesheet" href="../../vendors/ti-icons/css/themify-icons.css">
            <link rel="stylesheet" href="../../vendors/css/vendor.bundle.base.css">
            <link rel="stylesheet" href="../../css/vertical-layout-light/style.css">
            <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

        </head>

        <body>
            <div class="container-fluid page-body-wrapper">
                <%@ include file="includes/head.jsp" %>

                    <!-- Navbar -->
                    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
                        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
                            <a class="navbar-brand brand-logo mr-5" href="/produits_liste"><img src="images/lokaly.png"
                                    class="mr-2" alt="logo" /></a>
                            <a class="navbar-brand brand-logo-mini" href="/produits_liste"><img
                                    src="images/logo-mini.svg" alt="logo" /></a>
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
                            <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center"
                                type="button" data-toggle="offcanvas">
                                <span class="icon-menu"></span>
                            </button>
                        </div>
                    </nav>

                    <!-- Sidebar -->
                    <nav class="sidebar sidebar-offcanvas shadow-sm" id="sidebar">
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
                        <div class="container-fluid my-4">
                            <div class="row">
                                <!-- Sidebar gauche -->
                                <div class="col-md-4 col-lg-3 mb-4">
                                    <div class="bg-white rounded shadow-sm p-4 text-center">
                                        <div class="position-relative d-inline-block mb-3" width="200" height="200">
                                            <img src="" class="rounded-circle mb-2" alt="Photo de profil" width="200"
                                                height="200">
                                            <a href="#"
                                                class="position-absolute bottom-0 end-0 bg-white rounded-circle p-1 border shadow-sm">
                                                <i class="bi bi-pencil text-primary"></i>
                                            </a>
                                        </div>
                                        <h5 class="fw-bold mb-0">${client.prenoms} ${client.nom}</h5>
                                        <small class="text-muted d-block mb-3">Utilisateur</small>
                                        <ul class="nav flex-column text-start">
                                            <li class="nav-item mb-2">
                                                <div class="bg-white shadow-sm rounded px-3 ">
                                                    <a class="nav-link active d-flex align-items-center text-dark" href="#">
                                                        <i class="bi bi-person me-2"></i>
                                                        Personal Information
                                                    </a>
                                                </div>
                                            </li>

                                            <li class="nav-item mb-2">
                                                <a class="nav-link" href="#" onclick="togglePasswordSection()">
                                                    <i class="bi bi-lock me-2"></i>Login & Password
                                                </a>
                                            </li>



                                            <!-- Section masquée initialement -->
                                            <div id="passwordSection" class="bg-white rounded shadow-sm p-3"
                                                style="display: none;">
                                                <form action="/monprofil/update-password" method="post">
                                                    <div class="mb-3 position-relative">
                                                        <label class="form-label">Nouveau mot de passe</label>
                                                        <div class="input-group">
                                                            <input type="password" class="form-control"
                                                                id="passwordInput" value="${client.mdp}" name="password"
                                                                required>
                                                            <span class="input-group-text">
                                                                <i class="bi bi-eye-slash" id="togglePassword"
                                                                    style="cursor: pointer;"></i>
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div class="d-flex justify-content-end">
                                                        <button type="submit" class="btn btn-warning text-white">Mettre
                                                            à jour</button>
                                                    </div>
                                                </form>
                                            </div>


                                            <li class="nav-item mt-3">
                                                <form action="/logout" method="get">
                                                    <button type="submit" class="btn btn-outline-danger w-100">
                                                        <i class="bi bi-box-arrow-right me-1"></i> Log Out
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </div>

                                <!-- Formulaire principal -->
                                <div class="col-md-8 col-lg-9">
                                    <div class="bg-white rounded shadow-sm p-4">
                                        <h5 class="fw-bold mb-4">Informations personnelles</h5>

                                        <form action="/monprofil/update" method="post">
                                            <!-- Genre -->
                                            <div class="mb-3">
                                                <label class="form-label d-block">Genre</label>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="sexe" value="M"
                                                        ${client.sexe=='M' ? 'checked' : '' }>
                                                    <label class="form-check-label">Masculin</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="sexe" value="F"
                                                        ${client.sexe=='F' ? 'checked' : '' }>
                                                    <label class="form-check-label">Feminin</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="sexe" value="A"
                                                        ${client.sexe=='A' ? 'checked' : '' }>
                                                    <label class="form-check-label">Autre</label>
                                                </div>
                                            </div>

                                            <div class="row g-3">
                                                <div class="col-md-6">
                                                    <label class="form-label">Prenoms</label>
                                                    <input type="text" class="form-control" name="prenom"
                                                        value="${client.prenoms}" required>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Nom</label>
                                                    <input type="text" class="form-control" name="nom"
                                                        value="${client.nom}" required>
                                                </div>
                                                <div class="col-md-8">
                                                    <label class="form-label">Email</label>
                                                    <div class="input-group">
                                                        <input type="email" class="form-control" name="email"
                                                            value="${client.email}" readonly>
                                                        <span class="input-group-text text-success bg-light border-0"><i
                                                                class="bi bi-check-circle-fill"></i> Verified</span>
                                                    </div>
                                                </div>
                                                <div class="col-md-12">
                                                    <label class="form-label">Adresse</label>
                                                    <input type="text" class="form-control" name="adresse"
                                                        value="${client.adresse}">
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Numéro de téléphone</label>
                                                    <input type="text" class="form-control" name="telephone"
                                                        value="${client.telephone}">
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Date de naissance</label>
                                                    <input type="date" class="form-control" name="dtn"
                                                        value="${client.dtn}">
                                                </div>
                                            </div>

                                            <!-- Boutons -->
                                            <div class="d-flex justify-content-end mt-4">
                                                <button type="reset" class="btn btn-outline-secondary me-2">Annuler
                                                    les modifications</button>
                                                <button type="submit" class="btn btn-primary text-white">Enregistrer
                                                    les modifications</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!-- Footer -->
                        <footer class="footer">
                            <div class="d-sm-flex justify-content-center justify-content-sm-between">
                                <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">
                                    Copyright © 2021.
                                    Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin
                                        template</a>.
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
            <script src="${pageContext.request.contextPath}/js/script.js"></script>

        </body>

        </html>