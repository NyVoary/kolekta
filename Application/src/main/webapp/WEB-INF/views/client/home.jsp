<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Boutique en ligne | Librairie</title>
    <!-- Skydash Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
    
    <style>
        /* Hero Section */
        .hero-section {
            background: linear-gradient(rgba(58, 83, 155, 0.8), rgba(58, 83, 155, 0.8)), 
                        url('https://images.unsplash.com/photo-1495640388908-05fa85288e61?ixlib=rb-4.0.3') center/cover;
            height: 80vh;
            display: flex;
            align-items: center;
            color: white;
            margin-bottom: 50px;
            border-radius: 0 0 30px 30px;
        }
        
        .hero-content {
            max-width: 650px;
            text-shadow: 0 2px 4px rgba(0,0,0,0.3);
        }
        
        .hero-title {
            font-size: 3.5rem;
            font-weight: 700;
            margin-bottom: 20px;
        }
        
        .hero-subtitle {
            font-size: 1.5rem;
            margin-bottom: 30px;
        }
        
        .btn-hero {
            padding: 12px 30px;
            font-size: 1.1rem;
            border-radius: 30px;
            font-weight: 600;
            transition: all 0.3s;
        }
        
        /* Product Cards */
        .product-card {
            transition: transform 0.3s, box-shadow 0.3s;
            height: 100%;
            border: none;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
        
        .product-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.15);
        }
        
        .product-img {
            height: 200px;
            object-fit: cover;
            width: 100%;
        }
        
        .product-badge {
            position: absolute;
            top: 15px;
            right: 15px;
            background: #ff4d4d;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            font-weight: 600;
        }
        
        .section-title {
            position: relative;
            margin-bottom: 40px;
            font-weight: 700;
        }
        
        .section-title:after {
            content: "";
            position: absolute;
            bottom: -10px;
            left: 0;
            width: 80px;
            height: 4px;
            background: #3a539b;
            border-radius: 2px;
        }
        
        /* Quantity Controls */
        .quantity-controls .btn {
            padding: 0.25rem 0.75rem;
            background: #f5f7ff;
            border: 1px solid #e0e0e0;
        }
        
        .quantity-controls input {
            width: 50px;
            text-align: center;
            border: 1px solid #e0e0e0;
            font-weight: 600;
        }
        
        /* Message Box */
        .message-box {
            position: fixed;
            top: 100px;
            right: 30px;
            z-index: 1000;
            max-width: 350px;
            opacity: 0.95;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
            border: none;
            border-radius: 10px;
            animation: slideIn 0.5s ease-out;
        }
        
        @keyframes slideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 0.95; }
        }
    </style>
</head>

<body>
    <%@ include file="includes/navbar.jsp" %>
    
   <!-- Hero Section -->
<section class="hero-section">
    <div class="container">
        <div class="hero-content">
            <h1 class="hero-title">Bienvenue sur Lokaly</h1>
            <p class="hero-subtitle">Des fruits et legumes frais, locaux et de saison livres chez vous</p>
            <div class="d-flex gap-3">
                <a href="#featured-products" class="btn btn-light btn-hero">Voir nos produits</a>
            </div>
        </div>
    </div>
</section>


    <div class="container mb-5">
        <!-- Messages de notification -->
        <c:if test="${not empty message}">
            <div class="alert alert-success message-box alert-dismissible fade show" role="alert" id="messageBox">
                ${message}
                <button type="button" class="close" onclick="fermerMessage()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <c:if test="${not empty erreur}">
            <div class="alert alert-danger message-box alert-dismissible fade show" role="alert" id="messageBox">
                ${erreur}
                <button type="button" class="close" onclick="fermerMessage()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <!-- Featured Products Section -->
        <div class="row mb-5" id="featured-products">
            <div class="col-12">
                <h2 class="section-title">Produits du moment</h2>
            </div>
            
            <c:forEach var="produit" items="${produitsSaison}">
                <div class="col-lg-3 col-md-6 mb-4">
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
                </div>
            </c:forEach>
        </div>

        <!-- All Products Section -->
        <div class="row mt-5" id="all-products">
            <div class="col-12">
                <div class="d-flex justify-content-between align-items-center">
                    <h2 class="section-title">Tous nos produits</h2>
                    <div class="form-group mb-0">
                        <select class="form-control" id="sortProducts" onchange="sortProducts(this.value)">
                            <option value="default">Trier par</option>
                            <option value="price_asc">Prix: Croissant</option>
                            <option value="price_desc">Prix: Decroissant</option>
                            <option value="name">Nom</option>
                        </select>
                    </div>
                </div>
            </div>
            
            <c:if test="${empty produits}">
                <div class="col-12">
                    <div class="alert alert-info text-center">
                        <h4 class="mb-0">Aucun produit disponible pour le moment</h4>
                    </div>
                </div>
            </c:if>
            
            <c:forEach var="produit" items="${produits}">
                <div class="col-lg-3 col-md-6 mb-4">
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
                </div>
            </c:forEach>
        </div>
    </div>
    
    <%@ include file="includes/footer.jsp" %>
    
    <!-- Skydash Core JS -->
    <script src="${pageContext.request.contextPath}/vendors/js/vendor.bundle.base.js"></script>
    <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
    <script src="${pageContext.request.contextPath}/js/hoverable-collapse.js"></script>
    <script src="${pageContext.request.contextPath}/js/template.js"></script>
    
    <script>
        function changerQuantite(btn, delta) {
            const input = btn.closest('.quantity-controls').querySelector('input[name="quantite"]');
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

        // Fonction de tri (exemple)
        function sortProducts(criteria) {
            console.log("Trier par: " + criteria);
            // Implémentation réelle dépendrait du backend
            // window.location.href = '/produits?sort=' + criteria;
        }
        
        // Animation au défilement
        document.addEventListener('DOMContentLoaded', function() {
            const productCards = document.querySelectorAll('.product-card');
            
            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.style.opacity = "1";
                        entry.target.style.transform = "translateY(0)";
                    }
                });
            }, { threshold: 0.1 });
            
            productCards.forEach(card => {
                card.style.opacity = "0";
                card.style.transform = "translateY(20px)";
                card.style.transition = "opacity 0.6s ease, transform 0.6s ease";
                observer.observe(card);
            });
        });
    </script>
</body>
</html>