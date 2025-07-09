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

        <div class="" style="margin-top:2vh">
            <!-- partial:partials/_sidebar.html -->
            <div class="">
                <div class="">
                    <h1>Liste des produits</h1>

                    <!-- Skydash style notifications -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-success alert-dismissible" role="alert" id="messageBox">
                            <i class="ti-check-box"></i>
                            ${message}
                            <button type="button" class="btn-close" onclick="fermerMessage()">×</button>
                        </div>
                    </c:if>

                    <div class="produits-container">
                        <c:forEach var="produit" items="${produits}">
                            <div class="card product-card">
                        <%-- <c:if test="${produit.enPromotion}">
                            <span class="product-badge">PROMO</span>
                        </c:if> --%>
                        <img src="${pageContext.request.contextPath}/uploads/carottes.png" alt="${produit.nom}" class="product-img">
                        <div class="card-body">
                            <h4 class="card-title">${produit.nom}</h4>
                            <p class="card-text text-muted mb-3">${produit.description}</p>
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h5 class="text-primary mb-0">${produit.prixVente} Ar</h5>
                                <small class="text-muted">/${produit.uniteMesure}</small>
                            </div>
                            <form method="post" action="/commande/ajouter">
                                <input type="hidden" name="produitId" value="${produit.id}" />
                                <div class="d-flex align-items-center quantity-controls mb-3">
                                    <button type="button" class="btn" onclick="changerQuantite(this, -1)">-</button>
                                    <input type="number" name="quantite" value="1" min="1" class="form-control mx-2">
                                    <button type="button" class="btn" onclick="changerQuantite(this, 1)">+</button>
                                </div>
                                <button type="submit" class="btn btn-primary btn-block">
                                    <i class="ti-shopping-cart mr-2"></i> Ajouter au panier
                                </button>
                            </form>
                        </div>
                    </div>
                        </c:forEach>
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