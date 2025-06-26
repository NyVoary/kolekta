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

    <h1>Liste des produits</h1>

    <form method="get" action="/produits">
        <label for="search">Nom :</label>
        <input type="text" id="search" name="search" placeholder="Rechercher par nom..." value="${search}" />

        <label for="categorie">Catégorie :</label>
        <select id="categorie" name="categorie">
            <option value="">-- Toutes les catégories --</option>
            <c:forEach var="cat" items="${categories}">
                <option value="${cat.nom}" ${cat.nom == categorie ? 'selected' : ''}>${cat.nom}</option>
            </c:forEach>
        </select>

        <label for="prixMin">Prix Minimal :</label>
        <input type="number" step="0.01" id="prixMin" name="prixMin" placeholder="ex : 1000" value="${prixMin}" />

        <label for="prixMax">Prix Maximal :</label>
        <input type="number" step="0.01" id="prixMax" name="prixMax" placeholder="ex : 1500" value="${prixMax}" />

        <button type="submit">Rechercher</button>
    </form>

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