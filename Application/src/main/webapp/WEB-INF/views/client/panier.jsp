<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.Panier" %>
<%@ page import="com.example.demo.entity.PanierProduit" %>
<%@ page import="com.example.demo.entity.Produit" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.DecimalFormat" %>

<%
    DecimalFormat formatter = new DecimalFormat("#,##0.00");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Mon Panier - Fournisseurs Agricoles</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/datatables.net-bs4/dataTables.bootstrap4.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" />
    <style>
        .panier-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .panier-item {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 15px;
            padding: 20px;
            transition: transform 0.2s;
        }
        .panier-item:hover {
            transform: translateY(-2px);
        }
        .produit-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
        }
        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .quantity-btn {
            background: #007bff;
            color: white;
            border: none;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: background 0.2s;
        }
        .quantity-btn:hover {
            background: #0056b3;
        }
        .quantity-input {
            width: 60px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 5px;
        }
        .resume-commande {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            padding: 25px;
            position: sticky;
            top: 20px;
        }
        .btn-commander {
            background: linear-gradient(45deg, #28a745, #20c997);
            border: none;
            padding: 12px 30px;
            border-radius: 25px;
            color: white;
            font-weight: bold;
            width: 100%;
            transition: transform 0.2s;
        }
        .btn-commander:hover {
            transform: scale(1.05);
            color: white;
        }
        .panier-vide {
            text-align: center;
            padding: 60px 20px;
        }
        .panier-vide i {
            font-size: 80px;
            color: #ddd;
            margin-bottom: 20px;
        }
        .alert-custom {
            border-radius: 10px;
            border: none;
            padding: 15px 20px;
        }
    </style>
</head>
<body class="bg-light">
    <div class="panier-container">
        <div class="row mb-4">
            <div class="col-12">
                <h2 class="text-center mb-0">
                    <i class="fas fa-shopping-cart text-primary"></i> Mon Panier
                </h2>
                <p class="text-center text-muted">
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

        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% if (successMessage != null) { %>
            <div class="alert alert-success alert-custom alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> <%= successMessage %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-danger alert-custom alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle"></i> <%= errorMessage %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="row">
            <div class="col-lg-8">
                <%
                    Panier panier = (Panier) request.getAttribute("panier");
                    if (panier != null && panier.getPanierProduits() != null && !panier.getPanierProduits().isEmpty()) {
                %>
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Articles dans votre panier</h5>
                    <form action="/panier/vider" method="post">
                        <button type="submit" class="btn btn-outline-danger btn-sm"
                                onclick="return confirm('Êtes-vous sûr de vouloir vider votre panier ?')">
                            <i class="fas fa-trash"></i> Vider le panier
                        </button>
                    </form>
                </div>

                <% for (PanierProduit panierProduit : panier.getPanierProduits()) {
                    Produit produit = panierProduit.getProduit();
                %>
                <div class="panier-item">
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
                            <h6 class="mb-1"><%= produit.getNom() %></h6>
                            <p class="text-muted small mb-0"><%= produit.getDescription() %></p>
                            <small class="text-info">Unité: <%= produit.getUniteMesure() %></small>
                        </div>
                        <div class="col-md-2 text-center">
                            <strong><%= formatter.format(panierProduit.getPrixUnitaire()) %> Ar</strong>
                        </div>
                        <div class="col-md-2">
                            <div class="quantity-controls justify-content-center">
                                <form action="/panier/modifier-quantite" method="post" style="display:inline;">
                                    <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>">
                                    <input type="hidden" name="quantite" value="<%= panierProduit.getQuantite() - 1 %>">
                                    <button type="submit" class="quantity-btn" <%= panierProduit.getQuantite() <= 1 ? "disabled" : "" %>>
                                        <i class="fas fa-minus"></i>
                                    </button>
                                </form>
                                <!-- Formulaire dédié pour l'input quantité -->
                                <form method="post" action="/panier/modifier-quantite" style="display:inline;">
                                    <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>" />
                                    <input type="number" name="quantite" class="quantity-input" min="1"
                                           value="<%= panierProduit.getQuantite() %>"
                                           onchange="this.form.submit()" />
                                </form>
                                <form action="/panier/modifier-quantite" method="post" style="display:inline;">
                                    <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>">
                                    <input type="hidden" name="quantite" value="<%= panierProduit.getQuantite() + 1 %>">
                                    <button type="submit" class="quantity-btn">
                                        <i class="fas fa-plus"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                        <div class="col-md-2 text-end">
                            <div class="mb-2">
                                <strong class="text-success">
                                    <%= formatter.format(panierProduit.getPrixUnitaire().multiply(new BigDecimal(panierProduit.getQuantite()))) %> Ar
                                </strong>
                            </div>
                            <form action="/panier/supprimer" method="post">
                                <input type="hidden" name="idProduit" value="<%= produit.getIdProduit() %>" />
                                <button type="submit" class="btn btn-outline-danger btn-sm"
                                        onclick="return confirm('Supprimer ce produit du panier ?')">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } else { %>
                <div class="panier-vide">
                    <i class="fas fa-shopping-cart"></i>
                    <h4>Votre panier est vide</h4>
                    <p class="text-muted">Découvrez nos produits et commencez vos achats !</p>
                    <a href="/produits" class="btn btn-primary btn-lg">
                        <i class="fas fa-store"></i> Voir nos produits
                    </a>
                </div>
                <% } %>
            </div>

            <div class="col-lg-4">
                <div class="resume-commande">
                    <h5 class="mb-4"><i class="fas fa-receipt"></i> Résumé de la commande</h5>
                    <%
                        BigDecimal montantHT = (BigDecimal) request.getAttribute("montantHT");
                        BigDecimal tva = (BigDecimal) request.getAttribute("tva");
                        BigDecimal montantTTC = (BigDecimal) request.getAttribute("montantTTC");
                        BigDecimal fraisLivraison = (BigDecimal) request.getAttribute("fraisLivraison");
                        BigDecimal montantTotal = (BigDecimal) request.getAttribute("montantTotal");
                    %>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Sous-total HT:</span>
                        <strong><%= formatter.format(montantHT) %> Ar</strong>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>TVA (20%):</span>
                        <strong><%= formatter.format(tva) %> Ar</strong>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Total TTC:</span>
                        <strong><%= formatter.format(montantTTC) %> Ar</strong>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span>Frais de livraison:</span>
                        <strong><%= formatter.format(fraisLivraison) %> Ar</strong>
                    </div>
                    <hr class="bg-white" />
                    <div class="d-flex justify-content-between mb-4">
                        <span class="h5">TOTAL:</span>
                        <span class="h5"><%= formatter.format(montantTotal) %> Ar</span>
                    </div>

                    <% if (nombreArticles != null && nombreArticles > 0) { %>
                        <form action="/panier/valider" method="post">
                            <button type="submit" class="btn-commander">
                                <i class="fas fa-credit-card"></i> Valider la commande
                            </button>
                        </form>
                        <%-- ty lessy mila ovaina kel mba his-adapte anle url a --%>
                        <div class="mt-3 text-center">
                            <a href="/produits" class="btn btn-outline-light">
                                <i class="fas fa-arrow-left" style="color:white"></i> Continuer mes achats
                            </a>
                        </div>
                    <% } else { %>
                        <div class="text-center">
                            <p class="mb-3">Ajoutez des produits à votre panier pour continuer</p>
                            <a href="/produits" class="btn btn-outline-light">
                                <i class="fas fa-store"></i> Voir nos produits
                            </a>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function updateQuantity(idProduit, quantity) {
            if (quantity < 1) {
                quantity = 1;
            }

            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/panier/modifier-quantite';

            const inputIdProduit = document.createElement('input');
            inputIdProduit.type = 'hidden';
            inputIdProduit.name = 'idProduit';
            inputIdProduit.value = idProduit;

            const inputQuantite = document.createElement('input');
            inputQuantite.type = 'hidden';
            inputQuantite.name = 'quantite';
            inputQuantite.value = quantity;

            form.appendChild(inputIdProduit);
            form.appendChild(inputQuantite);
            document.body.appendChild(form);
            form.submit();
        }

        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>
