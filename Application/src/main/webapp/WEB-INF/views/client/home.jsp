<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Lokaly</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/produits.css" />
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            color: #333;
        }

        /* Navigation */
        .navbar {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            padding: 1rem 0;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
            box-shadow: 0 2px 20px rgba(0, 0, 0, 0.1);
        }

        .nav-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 1.8rem;
            font-weight: bold;
            color: #667eea;
            text-decoration: none;
        }

        .nav-menu {
            display: flex;
            list-style: none;
            gap: 2rem;
        }

        .nav-link {
            color: #333;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .nav-link:hover {
            color: #667eea;
        }

        /* Messages de notification */
        .message-box {
            position: fixed;
            top: 80px;
            right: 20px;
            padding: 15px 20px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            z-index: 1001;
            display: flex;
            align-items: center;
            gap: 10px;
            max-width: 400px;
            animation: slideIn 0.3s ease-out;
        }

        .message-box.success {
            background: linear-gradient(135deg, #4CAF50, #45a049);
            color: white;
        }

        .message-box.error {
            background: linear-gradient(135deg, #f44336, #d32f2f);
            color: white;
        }

        .close-btn {
            background: none;
            border: none;
            color: inherit;
            font-size: 1.2rem;
            cursor: pointer;
            padding: 5px;
        }

        @keyframes slideIn {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        /* Section produits du moment */
        .section-saison {
            margin-top: 100px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            padding: 3rem 0;
            text-align: center;
            color: white;
        }

        .section-saison h1 {
            font-size: 2.5rem;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        }

        .produits-saison {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 1rem;
            margin-top: 2rem;
        }

        .produit-saison-item {
            background: rgba(255, 255, 255, 0.2);
            padding: 0.8rem 1.5rem;
            border-radius: 25px;
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.3);
            transition: transform 0.3s ease;
        }

        .produit-saison-item:hover {
            transform: translateY(-5px);
        }

        /* Section principale */
        .main-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 4rem 2rem;
        }

        .main-title {
            text-align: center;
            font-size: 2.5rem;
            color: white;
            margin-bottom: 3rem;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        }

        .empty-state {
            text-align: center;
            color: white;
            font-size: 1.2rem;
            padding: 3rem;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 15px;
            backdrop-filter: blur(10px);
        }

        /* Grille de produits */
        .produits-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        }

        .produit {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            backdrop-filter: blur(10px);
        }

        .produit:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
        }

        .produit-image {
            height: 200px;
            overflow: hidden;
            position: relative;
        }

        .produit-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s ease;
        }

        .produit:hover .produit-image img {
            transform: scale(1.05);
        }

        .produit-content {
            padding: 1.5rem;
        }

        .produit-nom h2 {
            font-size: 1.5rem;
            color: #333;
            margin-bottom: 0.5rem;
        }

        .produit-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 1rem;
        }

        .produit-prix {
            font-size: 1.3rem;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 1.5rem;
        }

        .produit-formulaire {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .quantite-controls {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 1rem;
            margin-bottom: 1rem;
        }

        .quantite-controls button {
            background: #667eea;
            color: white;
            border: none;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            font-size: 1.2rem;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .quantite-controls button:hover {
            background: #5a6fd8;
            transform: scale(1.1);
        }

        .quantite-controls input {
            width: 60px;
            height: 40px;
            text-align: center;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: bold;
        }

        .btn-ajouter {
            background: linear-gradient(135deg, #4CAF50, #45a049);
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 25px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
        }

        .btn-ajouter:hover {
            background: linear-gradient(135deg, #45a049, #4CAF50);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(76, 175, 80, 0.3);
        }

    </style>
</head>

<body>
    <%-- <%@ include file="./inc/css.jsp"  %>
    <%@ include file="./inc/header.jsp" %> --%>
    <%@ include file="includes/navbar.jsp" %>
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

    <section class="section-saison">
        <h1><i class="fas fa-leaf"></i> Produits du moment</h1>
        <div class="produits-saison">
         <c:forEach var="produit" items="${produitsSaison}">
            <div class="produit-saison-item">${produit.nom}</div>
            </c:forEach>
    </section>

    <div>
    <h1>Liste des produits</h1>

    <c:if test="${empty produits}">
        <p>Aucun produit disponible.</p>
    </c:if>

    <div class="produits-container">
        <c:forEach var="produit" items="${produits}">
            <div class="produit">
                <div class="produit-image">
                    <img src="${pageContext.request.contextPath}${produit.image}" alt="${produit.nom}" />
                </div>
                <div class="produit-content">
                    <div class="produit-nom">
                        <h2>${produit.nom}</h2>
                    </div>
                    <div class="produit-description">
                        <p>${produit.description}</p>
                    </div>
                    <div class="produit-prix">
                        <p><strong>${produit.prixVente}</strong></strong> Ar /${produit.uniteMesure}</p>
                    </div>
                    <div class="produit-formulaire">
                    <form method="post" action="/commande/ajouter">
                    <input type="hidden" name="produitId" value="${produit.id}" />
                        <div class="quantite-controls">
                            <button type="button" onclick="changerQuantite(this, -1)">-</button>
                            <input type="number" name="quantite" value="1" min="1" />
                            <button type="button" onclick="changerQuantite(this, 1)">+</button>
                        </div>
                        <button type="submit" class="btn-ajouter"> <i class="fas fa-cart-plus"></i>Ajouter au panier</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    </div>
    <%@ include file="includes/footer.jsp" %>
    <%-- <%@ include file="./inc/footer.jsp"%>
    <%@ include file="./inc/js.jsp"  %> --%>
    <script src="${pageContext.request.contextPath}/vendors/js/vendor.bundle.base.js"></script>
    <!-- endinject -->
    <!-- Plugin js for this page -->
    <!-- End plugin js for this page -->
    <!-- inject:js -->
    <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
    <script src="${pageContext.request.contextPath}/js/hoverable-collapse.js"></script>
    <script src="${pageContext.request.contextPath}/js/template.js"></script>
    <script src="${pageContext.request.contextPath}/js/settings.js"></script>
    <script src="${pageContext.request.contextPath}/js/todolist.js"></script>
    <!-- endinject -->
    
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
</body>
</html>