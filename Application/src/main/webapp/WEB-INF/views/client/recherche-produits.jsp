<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
    <link rel="stylesheet" href="../../static/css/style-home-search-client.css">
    </head>
</head>

<body>
    <div class="container-scroller">
        <%@ include file="includes/navbar.jsp" %>

        <div class="container-fluid page-body-wrapper">
            <div class="main-panel">
                <div class="content-wrapper">
                    <!-- Skydash style page header -->
                    <div class="page-header">
                        <div>
                            <h3 class="page-title">
                                <span class="page-title-icon bg-gradient-primary text-white">
                                    <i class="ti-package"></i>
                                </span>
                                Liste des produits
                            </h3>
                        </div>
                    </div>

                    <!-- Skydash style notifications -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-success alert-dismissible" role="alert" id="messageBox">
                            <i class="ti-check-box"></i>
                            ${message}
                            <button type="button" class="btn-close" onclick="fermerMessage()">×</button>
                        </div>
                    </c:if>

                    <c:if test="${not empty erreur}">
                        <div class="alert alert-danger alert-dismissible" role="alert" id="messageBox">
                            <i class="ti-alert"></i>
                            ${erreur}
                            <button type="button" class="btn-close" onclick="fermerMessage()">×</button>
                        </div>
                    </c:if>

                    <!-- Search bar - Skydash style -->
                    <div class="search-wrapper">
                        <form method="get" action="/recherche_produits" class="form-inline">
                            <!-- Nom du produit -->
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

                            <div class="input-group">
                                <select class="form-control" name="categorie" id="categorie">
                                    <option value="">Catégorie</option>
                                    <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.libelle}" ${categorie == cat.libelle ? 'selected' : ''}>
                                        ${cat.libelle}
                                    </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Prix minimal -->
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

                            <!-- Prix maximal -->
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

                            <!-- Bouton de recherche -->
                            <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                                <i class="ti-search text-info"></i>                          
                            </button>
                        </form>
                    </div>

                    <!-- Products section - Skydash style -->
                    <div class="row">
                        <div class="col-lg-12 grid-margin stretch-card">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title">
                                        <i class="ti-shopping-cart"></i>
                                        Tous les produits
                                    </h4>
                                    <p class="card-description">
                                        Gérer votre inventaire de produits
                                    </p>

                                    <c:choose>
                                        <c:when test="${empty produits}">
                                            <div class="empty-state">
                                                <i class="ti-package"></i>
                                                <h5>Aucun produit disponible</h5>
                                                <p>Ajoutez des produits pour commencer.</p>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="products-grid">
                                                <c:forEach var="produit" items="${produits}">
                                                    <div class="product-card">
                                                        <div class="product-image">
                                                            <img src="${produit.image}" alt="${produit.nom}" />
                                                        </div>
                                                        <div class="product-content">
                                                            <h5 class="product-name">${produit.nom}</h5>
                                                            <p class="product-description">
                                                                ${produit.description}</p>
                                                            <div class="product-price">
                                                                ${produit.prixVente} Ar / ${produit.uniteMesure}
                                                            </div>
                                                            <form method="post" action="/commande/ajouter">
                                                                <input type="hidden" name="produitId"
                                                                    value="${produit.id}" />
                                                                <div class="quantity-controls">
                                                                    <button type="button" class="quantity-btn"
                                                                        onclick="changerQuantite(this, -1)">
                                                                        <i class="ti-minus"></i>
                                                                    </button>
                                                                    <input type="number" name="quantite"
                                                                        value="1" min="1"
                                                                        class="form-control quantity-input" />
                                                                    <button type="button" class="quantity-btn"
                                                                        onclick="changerQuantite(this, 1)">
                                                                        <i class="ti-plus"></i>
                                                                    </button>
                                                                </div>
                                                                <button type="submit"
                                                                    class="btn btn-success btn-sm w-100">
                                                                    <i class="ti-shopping-cart"></i>
                                                                    Ajouter au panier
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Skydash style footer -->
                <footer class="footer">
                    <div class="d-sm-flex justify-content-center justify-content-sm-between">
                        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">
                            Copyright © 2023. Tous droits réservés.
                        </span>
                    </div>
                </footer>
            </div>
        </div>
    </div>

    <script>
        function changerQuantite(btn, delta) {
            const input = btn.parentElement.querySelector('input[name="quantite"]');
            let val = parseInt(input.value) || 1;
            val = Math.max(1, val + delta);
            input.value = val;
        }

        function fermerMessage() {
            const messageBox = document.getElementById('messageBox');
            if (messageBox) {
                messageBox.style.display = 'none';
            }
        }

        // Auto-hide messages after 5 seconds
        window.onload = function () {
            const messageBox = document.getElementById('messageBox');
            if (messageBox) {
                setTimeout(function () {
                    messageBox.style.opacity = '0';
                    setTimeout(function () {
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