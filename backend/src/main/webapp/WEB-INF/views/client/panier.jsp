<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.Double" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.example.demo.entity.Panier" %>
<%@ page import="com.example.demo.entity.PanierProduits" %>
<%@ page import="com.example.demo.entity.Produits" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    DecimalFormat formatter = new DecimalFormat("#,##0.00");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
  <title>Library</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/produits.css" />
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
</head>
<body>
<div style="margin-top: 10vh;">
    <%@ include file="includes/navbar.jsp" %>
         <!-- Affichage du message de succès de livraison -->
            <%
                String message = (String) request.getAttribute("message");
                if (message != null && !message.isEmpty()) {
            %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle"></i> <%= message %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <%
                }
            %>
    <div class="">
        <div class="">
            <div class="content-wrapper">
                <div class="panier-container">

                    <!-- =================== EN-TÊTE =================== -->
                    <div class="row mb-4">
                        <div class="col-12 text-center">
                            <h2><i class="fas fa-shopping-cart text-primary"></i> Mon Panier</h2>
                            <p class="text-muted">
                                <%
                                    Integer nombreArticles = (Integer) request.getAttribute("nombreArticles");
                                    if (nombreArticles != null && nombreArticles > 0) {
                                %>
                                    <%= nombreArticles %> article(s) dans votre panier
                                <%
                                    } else {
                                %>
                                    Votre panier est vide
                                <%
                                    }
                                %>
                            </p>
                        </div>
                    </div>

                    <!-- =================== MESSAGES FLASH =================== -->
                    <% String successMessage = (String) request.getAttribute("successMessage"); %>
                    <% if (successMessage != null) { %>
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle"></i> <%= successMessage %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    <% } %>

                    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                    <% if (errorMessage != null) { %>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-triangle"></i> <%= errorMessage %>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    <% } %>

                    <div class="row">
                        <!-- =================== LISTE DES PRODUITS =================== -->
                        <div class="col-lg-8">
                            <%
                                Panier panier = (Panier) request.getAttribute("panier");
                                if (panier != null && panier.getPanierProduits() != null && !panier.getPanierProduits().isEmpty()) {
                            %>
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h5>Articles dans votre panier</h5>
                                <form action="/panier/vider" method="post">
                                    <button type="submit" class="btn btn-outline-danger btn-sm"
                                            onclick="return confirm('Êtes-vous sûr de vouloir vider votre panier ?')">
                                        <i class="fas fa-trash"></i> Vider
                                    </button>
                                </form>
                            </div>

                            <% for (PanierProduits panierProduit : panier.getPanierProduits()) {
                                Produits produit = panierProduit.getProduit();
                            %>
                            <div class="panier-item mb-3">
                                <div class="row align-items-center">
                                    <div class="col-md-2 text-center">
                                        <% if (produit.getImage() != null && !produit.getImage().isEmpty()) { %>
                                            <img src="/images/produits/<%= produit.getImage() %>"
                                                 alt="<%= produit.getNom() %>" class="produit-image" />
                                        <% } else { %>
                                            <div class="produit-image d-flex align-items-center justify-content-center bg-light">
                                                <i class="fas fa-image text-muted"></i>
                                            </div>
                                        <% } %>
                                    </div>
                                    <div class="col-md-4">
                                        <h6><%= produit.getNom() %></h6>
                                        <p class="text-muted small"><%= produit.getDescription() %></p>
                                        <small class="text-info">Unité: <%= produit.getUniteMesure() %></small>
                                    </div>
                                    <div class="col-md-2 text-center">
                                        <strong><%= formatter.format(panierProduit.getPrixUnitaire()) %> Ar</strong>
                                    </div>
                                    <div class="col-md-2" style="">
                                        <div class="quantity-controls d-flex justify-content-center">
                                            <form action="/panier/modifier-quantite" method="post">
                                                <input type="hidden" name="idProduit" value="<%= produit.getId() %>">
                                                <input type="hidden" name="quantite" value="<%= panierProduit.getQuantite() - 1 %>">
                                                <button type="submit" class="quantity-btn" <%= panierProduit.getQuantite() <= 1 ? "disabled" : "" %>>
                                                    <%-- <i class="fas fa-minus"></i> --%>
                                                    -
                                                </button>
                                            </form>

                                            <form method="post" action="/panier/modifier-quantite">
                                                <input type="hidden" name="idProduit" value="<%= produit.getId() %>" />
                                                <input style="width:50px;" type="number" name="quantite" class="quantity-input" min="1"
                                                       value="<%= panierProduit.getQuantite() %>"
                                                       onchange="this.form.submit()" />
                                            </form>

                                            <form action="/panier/modifier-quantite" method="post">
                                                <input type="hidden" name="idProduit" value="<%= produit.getId() %>">
                                                <input type="hidden" name="quantite" value="<%= panierProduit.getQuantite() + 1 %>">
                                                <button type="submit" class="quantity-btn">
                                                    <%-- <i class="fas fa-plus"></i> --%>
                                                    +
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-md-2 text-end" style="">
                                        <div>
                                            <strong class="text-success">
                                                <%
                                                    double prixTotal = panierProduit.getPrixUnitaire() * panierProduit.getQuantite();
                                                %>
                                                <%= formatter.format(prixTotal) %> Ar
                                            </strong>
                                        </div>
                                        <form action="/panier/supprimer" method="post">
                                            <input type="hidden" name="idProduit" value="<%= produit.getId() %>" />
                                            <button type="submit" class="btn btn-outline-danger btn-sm"
                                                    onclick="return confirm('Supprimer ce produit du panier ?')">
                                                <%-- <i class="fas fa-trash"></i> --%>
                                                Delete
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                            <% } else { %>
                            <!-- =================== PANIER VIDE =================== -->
                            <div class="panier-vide text-center">
                                <i class="fas fa-shopping-cart fa-3x text-muted mb-2"></i>
                                <h4>Votre panier est vide</h4>
                                <p class="text-muted">Découvrez nos produits et commencez vos achats !</p>
                                <a href="/" class="btn btn-primary btn-lg">
                                    <i class="fas fa-store"></i> Voir nos produits
                                </a>
                            </div>
                            <% } %>
                        </div>

                        <!-- =================== RÉSUMÉ DE COMMANDE =================== -->
                        <div class="col-lg-4">
                            <div class="resume-commande p-3">
                                <h4 class="mb-3 text-uppercase"><i class="fas fa-receipt"></i> Résumé</h4>
                                <%
                                    Double montantHT = (Double) request.getAttribute("montantHT");
                                    Double tva = (Double) request.getAttribute("tva");
                                    Double montantTTC = (Double) request.getAttribute("montantTTC");
                                    Double fraisLivraison = (Double) request.getAttribute("fraisLivraison");
                                    Double montantTotal = (Double) request.getAttribute("montantTotal");
                                    if (montantHT == null) montantHT = 0.0;
                                    if (tva == null) tva = 0.0;
                                    if (montantTTC == null) montantTTC = 0.0;
                                    if (fraisLivraison == null) fraisLivraison = 0.0;
                                    if (montantTotal == null) montantTotal = 0.0;
                                %>
                                <div class="d-flex justify-content-between"><span>Sous-total HT:</span><strong><%= formatter.format(montantHT) %> Ar</strong></div>
                                <div class="d-flex justify-content-between"><span>TVA (20%):</span><strong><%= formatter.format(tva) %> Ar</strong></div>
                                <div class="d-flex justify-content-between"><span>Total TTC:</span><strong><%= formatter.format(montantTTC) %> Ar</strong></div>
                                <div class="d-flex justify-content-between mb-3"><span>Frais de livraison:</span><strong><%= formatter.format(fraisLivraison) %> Ar</strong></div>
                                <hr />
                                <div class="d-flex justify-content-between h5"><span>TOTAL:</span><span><%= formatter.format(montantTotal) %> Ar</span></div>

                                <!-- =================== BOUTONS ACTION =================== -->
                                <% if (nombreArticles != null && nombreArticles > 0) { %>
                                    <div class="text-center mt-3">
                                        <form action="/panier/valider" method="post">
                                        <button type="submit" class="btn btn-primary mr-2">
                                            <i class="fas fa-credit-card"></i> Valider la commande
                                        </button>
                                    </form>
                                    </div>
                                <% } else { %>
                                    <div class="text-center mt-3">
                                        <p class="mb-2">Ajoutez des produits à votre panier</p>
                                        <a href="/" class="btn btn-outline-light">
                                            <i class="fas fa-store"></i> Voir nos produits
                                        </a>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="includes/footer.jsp" %>
        </div>
    </div>
</div>

<script>
    setTimeout(function () {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(function (alert) {
            if (bootstrap.Alert) {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            }
        });
    }, 5000);
</script>
</body>
</html>
