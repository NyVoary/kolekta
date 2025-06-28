<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Produits disponibles</title>

    <!-- plugins:css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
    <!-- endinject -->

    <!-- inject:css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
    <!-- endinject -->

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/produits.css" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" />
</head>
<body>
    <div class="container-scroller">
        <!-- Messages de notification -->
        <c:if test="${not empty message}">
            <div class="message-box success" id="messageBox">
                <span class="message-text">${message}</span>
                <button class="close-btn" onclick="fermerMessage()">&times;</button>
            </div>
        </c:if>

        <c:if test="${not empty erreur}">
            <div class="message-box error" id="messageBox">
                <span class="message-text">${erreur}</span>
                <button class="close-btn" onclick="fermerMessage()">&times;</button>
            </div>
        </c:if>
        

        <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
            <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center"></div>

            <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
                <form method="get" action="/produits" class="form-inline">
                    <!-- Nom du produit -->
                    <ul class="navbar-nav mr-lg-2">
                        <li class="nav-item nav-search d-none d-lg-block">
                            <div class="input-group">
                                <input
                                    type="text"
                                    class="form-control"
                                    id="search"
                                    name="search"
                                    placeholder="Nom du produit"
                                   value="${search != null ? search : ''}"
                                >
                            </div>
                        </li>
                    </ul>

                    <!-- Catégorie avec dropdown -->
                    <div class="input-group mr-2">
                        <div class="dropdown">
                             <!-- Input caché qui contient la catégorie choisie -->
                            <input type="hidden" id="categorie" name="categorie" value="${categorie != null ? categorie : ''}">

                            <button type="button" class="btn btn-outline-info dropdown-toggle" id="dropdownCategorie" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                ${categorie != null && !categorie.isEmpty() ? categorie : "Sélectionnez une catégorie"}
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownCategorie">
                                <c:forEach var="cat" items="${categories}">
                                    <a class="dropdown-item" href="#" data-value="${cat.libelle}">
                                        ${cat.libelle}
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <script>
                        document.addEventListener('DOMContentLoaded', function() {
                            var dropdownItems = document.querySelectorAll('.dropdown-item[data-value]');
                            dropdownItems.forEach(function(item) {
                                item.addEventListener('click', function(e) {
                                    e.preventDefault();
                                    var value = this.getAttribute('data-value');
                                    document.getElementById('categorie').value = value;
                                    document.getElementById('dropdownCategorie').textContent = value;
                                });
                            });
                        });
                    </script>

                    <!-- Prix minimal -->
                    <ul class="navbar-nav mr-lg-2">
                        <li class="nav-item nav-search d-none d-lg-block">
                            <div class="input-group">
                                <input
                                    type="number"
                                    class="form-control"
                                    name="prixMin"
                                    placeholder="Prix minimal"
                                    step="0.01"
                                    value="${prixMin != null ? prixMin : ''}"
                                >
                            </div>
                        </li>
                    </ul>

                    <!-- Prix maximal -->
                    <ul class="navbar-nav mr-lg-2">
                        <li class="nav-item nav-search d-none d-lg-block">
                            <div class="input-group">
                                <input
                                    type="number"
                                    class="form-control"
                                    name="prixMax"
                                    placeholder="Prix maximal"
                                    step="0.01"
                                    value="${prixMax != null ? prixMax : ''}"
                                >
                            </div>
                        </li>
                    </ul>

                    <!-- Bouton de recherche -->
                    <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                        <i class="ti-search text-info"></i>                          
                    </button>
                </form>                

                <ul class="navbar-nav navbar-nav-right"></ul>
            </div>
        </nav>    

        <div class="container-fluid page-body-wrapper">
            <!-- partial:partials/_sidebar.html -->
            <nav class="sidebar sidebar-offcanvas" id="sidebar"></nav>

            <div class="main-panel">
                <div class="content-wrapper">
                    <h1>Liste des produits</h1>

                    <c:if test="${empty produits}">
                        <p>Aucun produit disponible.</p>
                    </c:if>

                    <div class="produits-container">
                        <c:forEach var="produit" items="${produits}">
                            <div class="produit">
                                <div class="produit-image">
                                    <img src="${produit.image}" alt="${produit.nom}" />
                                </div>
                                <div class="produit-nom">
                                    <h2>${produit.nom}</h2>
                                </div>
                                <div class="produit-description">
                                    <p>${produit.description}</p>
                                </div>
                                <div class="produit-prix">
                                    <p><strong>${produit.prixVente}</strong> Ar / ${produit.uniteMesure}</p>
                                </div>
                                <div class="produit-formulaire">
                                    <form method="post" action="/commande/ajouter">
                                        <input type="hidden" name="produitId" value="${produit.id}" />
                                        <div class="quantite-controls">
                                            <button type="button" onclick="changerQuantite(this, -1)">−</button>
                                            <input type="number" name="quantite" value="1" min="1" />
                                            <button type="button" onclick="changerQuantite(this, 1)">+</button>
                                        </div>
                                        <button type="submit" class="btn btn-success btn-sm">Ajouter au panier</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function changerQuantite(btn, delta) {
            const input = btn.parentElement.querySelector('input[name="quantite"]');
            let val = parseInt(input.value) || 1;
            input.value = Math.max(1, val + delta);
        }

        function fermerMessage() {
            const messageBox = document.getElementById('messageBox');
            if (messageBox) {
                messageBox.style.display = 'none';
            }
        }

        // Auto-fermeture du message après 5 secondes
        window.onload = function() {
            const messageBox = document.getElementById('messageBox');
            if (messageBox) {
                setTimeout(function() {
                    messageBox.style.opacity = '0';
                    setTimeout(function() {
                        messageBox.style.display = 'none';
                    }, 300);
                }, 5000);
            }
        };
    </script>

    <!-- plugins:js -->
    <script src="vendors/js/vendor.bundle.base.js"></script>
    <!-- endinject -->
    <!-- Plugin js for this page -->
    <script src="vendors/chart.js/Chart.min.js"></script>
    <script src="vendors/datatables.net/jquery.dataTables.js"></script>
    <script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
    <script src="js/dataTables.select.min.js"></script>
    
    <!-- End plugin js for this page -->
    <!-- inject:js -->
    <script src="js/off-canvas.js"></script>
    <script src="js/hoverable-collapse.js"></script>
    <script src="js/template.js"></script>
    <script src="js/settings.js"></script>
    <script src="js/todolist.js"></script>
    <!-- endinject -->
    <!-- Custom js for this page-->
    <script src="js/dashboard.js"></script>
    <script src="js/Chart.roundedBarCharts.js"></script>
    <!-- End custom js for this page-->
</body>
</html>