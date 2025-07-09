<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Skydash Admin | Products</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" />
        <link rel="stylesheet" href="../../static/css/style-home-client.css">
    </head>

    <body>
        <div class="container-scroller">
            <%@ include file="includes/navbar.jsp" %>

                <div class="container-fluid page-body-wrapper">
                    <div class="main-panel">
                        <div class="content-wrapper">
                            <!-- Exact Skydash page header -->
                            <div class="page-header">
                                <div>
                                    <h3 class="page-title">
                                        <span class="page-title-icon bg-gradient-primary text-white">
                                            <i class="ti-package"></i>
                                        </span>
                                        Products
                                    </h3>
                                    <nav aria-label="breadcrumb">
                                        <ul class="breadcrumb">
                                            <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                                            <li class="breadcrumb-item active" aria-current="page">Products</li>
                                        </ul>
                                    </nav>
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

                            <!-- Seasonal products - Skydash style -->
                            <c:if test="${not empty produitsSaison}">
                                <div class="seasonal-section">
                                    <h2>
                                        <i class="ti-star"></i>
                                        Featured Products
                                    </h2>
                                    <ul class="seasonal-list">
                                        <c:forEach var="produit" items="${produitsSaison}">
                                            <li>
                                                <i class="ti-arrow-right"></i>
                                                ${produit.nom}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>

                            <!-- Products section - Exact Skydash layout -->
                            <div class="row">
                                <div class="col-lg-12 grid-margin stretch-card">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">
                                                <i class="ti-shopping-cart"></i>
                                                Product List
                                            </h4>
                                            <p class="card-description">
                                                Manage your product inventory
                                            </p>

                                            <c:choose>
                                                <c:when test="${empty produits}">
                                                    <div class="empty-state">
                                                        <i class="ti-package"></i>
                                                        <h5>No products available</h5>
                                                        <p>Add some products to get started with your inventory.</p>
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
                                                                            Add to Cart
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

                        <!-- Exact Skydash footer -->
                        <footer class="footer">
                            <div class="d-sm-flex justify-content-center justify-content-sm-between">
                                <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">
                                    Copyright © 2023. Premium <a href="https://www.bootstrapdash.com/"
                                        target="_blank">Bootstrap admin template</a> from BootstrapDash. All rights
                                    reserved.
                                </span>
                                <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">
                                    Hand-crafted & made with <i class="ti-heart text-danger ml-1"></i>
                                </span>
                            </div>
                        </footer>
                    </div>
                </div>
        </div>

        <!-- Exact Skydash JavaScript -->
        <script src="${pageContext.request.contextPath}/vendors/js/vendor.bundle.base.js"></script>
        <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
        <script src="${pageContext.request.contextPath}/js/hoverable-collapse.js"></script>
        <script src="${pageContext.request.contextPath}/js/template.js"></script>
        <script src="${pageContext.request.contextPath}/js/settings.js"></script>
        <script src="${pageContext.request.contextPath}/js/todolist.js"></script>

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
    </body>

    </html>