<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lokaly - Produits</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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

        /* Footer */
        .footer {
            background: rgba(255, 255, 255, 0.1);
            color: white;
            text-align: center;
            padding: 2rem;
            backdrop-filter: blur(10px);
        }

        /* Responsive */
        @media (max-width: 768px) {
            .nav-menu {
                display: none;
            }

            .main-content {
                padding: 2rem 1rem;
            }

            .produits-container {
                grid-template-columns: 1fr;
            }

            .message-box {
                right: 10px;
                left: 10px;
                max-width: none;
            }
        }
    </style>
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
</head>
<body>
    <%@ include file="includes/navbar.jsp" %>

    <!-- Messages de notification -->
    <div class="message-box success" id="messageBox" style="display: none;">
        <i class="fas fa-check-circle"></i>
        <span class="message-text">Produit ajouté avec succès!</span>
        <button class="close-btn" onclick="fermerMessage()">&times;</button>
    </div>

    <!-- Section produits du moment -->
    <section class="section-saison">
        <h1><i class="fas fa-leaf"></i> Produits du moment</h1>
        <div class="produits-saison">
            <div class="produit-saison-item">Tomates cerises</div>
            <div class="produit-saison-item">Laitue bio</div>
            <div class="produit-saison-item">Carottes</div>
            <div class="produit-saison-item">Épinards frais</div>
            <div class="produit-saison-item">Radis</div>
        </div>
    </section>

    <!-- Contenu principal -->
    <main class="main-content">
        <h1 class="main-title">Liste des produits</h1>

        <div class="produits-container">
            <!-- Produit 1 -->
            <div class="produit">
                <div class="produit-image">
                    <img src="https://images.unsplash.com/photo-1592841200221-a6898f307baa?w=400&h=200&fit=crop" alt="Tomates bio" />
                </div>
                <div class="produit-content">
                    <div class="produit-nom">
                        <h2>Tomates bio</h2>
                    </div>
                    <div class="produit-description">
                        <p>Tomates biologiques cultivées localement, riches en vitamines et saveur authentique.</p>
                    </div>
                    <div class="produit-prix">
                        <p><strong>2,500</strong> Ar / kg</p>
                    </div>
                    <div class="produit-formulaire">
                        <div class="quantite-controls">
                            <button type="button" onclick="changerQuantite(this, -1)">-</button>
                            <input type="number" name="quantite" value="1" min="1" />
                            <button type="button" onclick="changerQuantite(this, 1)">+</button>
                        </div>
                        <button type="button" class="btn-ajouter" onclick="ajouterAuPanier(this)">
                            <i class="fas fa-cart-plus"></i>
                            Ajouter au panier
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produit 2 -->
            <div class="produit">
                <div class="produit-image">
                    <img src="https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=400&h=200&fit=crop" alt="Laitue fraîche" />
                </div>
                <div class="produit-content">
                    <div class="produit-nom">
                        <h2>Laitue fraîche</h2>
                    </div>
                    <div class="produit-description">
                        <p>Laitue croquante et fraîche, parfaite pour vos salades et sandwichs.</p>
                    </div>
                    <div class="produit-prix">
                        <p><strong>1,200</strong> Ar / pièce</p>
                    </div>
                    <div class="produit-formulaire">
                        <div class="quantite-controls">
                            <button type="button" onclick="changerQuantite(this, -1)">-</button>
                            <input type="number" name="quantite" value="1" min="1" />
                            <button type="button" onclick="changerQuantite(this, 1)">+</button>
                        </div>
                        <button type="button" class="btn-ajouter" onclick="ajouterAuPanier(this)">
                            <i class="fas fa-cart-plus"></i>
                            Ajouter au panier
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produit 3 -->
            <div class="produit">
                <div class="produit-image">
                    <img src="https://images.unsplash.com/photo-1582515073490-39981397c445?w=400&h=200&fit=crop" alt="Carottes biologiques" />
                </div>
                <div class="produit-content">
                    <div class="produit-nom">
                        <h2>Carottes biologiques</h2>
                    </div>
                    <div class="produit-description">
                        <p>Carottes sucrées et croquantes, cultivées sans pesticides dans nos jardins locaux.</p>
                    </div>
                    <div class="produit-prix">
                        <p><strong>1,800</strong> Ar / kg</p>
                    </div>
                    <div class="produit-formulaire">
                        <div class="quantite-controls">
                            <button type="button" onclick="changerQuantite(this, -1)">-</button>
                            <input type="number" name="quantite" value="1" min="1" />
                            <button type="button" onclick="changerQuantite(this, 1)">+</button>
                        </div>
                        <button type="button" class="btn-ajouter" onclick="ajouterAuPanier(this)">
                            <i class="fas fa-cart-plus"></i>
                            Ajouter au panier
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produit 4 -->
            <div class="produit">
                <div class="produit-image">
                    <img src="https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=400&h=200&fit=crop" alt="Épinards frais" />
                </div>
                <div class="produit-content">
                    <div class="produit-nom">
                        <h2>Épinards frais</h2>
                    </div>
                    <div class="produit-description">
                        <p>Épinards tendres et nutritifs, riches en fer et vitamines pour votre santé.</p>
                    </div>
                    <div class="produit-prix">
                        <p><strong>2,000</strong> Ar / botte</p>
                    </div>
                    <div class="produit-formulaire">
                        <div class="quantite-controls">
                            <button type="button" onclick="changerQuantite(this, -1)">-</button>
                            <input type="number" name="quantite" value="1" min="1" />
                            <button type="button" onclick="changerQuantite(this, 1)">+</button>
                        </div>
                        <button type="button" class="btn-ajouter" onclick="ajouterAuPanier(this)">
                            <i class="fas fa-cart-plus"></i>
                            Ajouter au panier
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <%@ include file="includes/footer.jsp" %>

    <script>
        function changerQuantite(btn, delta) {
            const input = btn.parentElement.querySelector('input[name="quantite"]');
            let val = parseInt(input.value) || 1;
            input.value = Math.max(1, val + delta);
        }

        function ajouterAuPanier(btn) {
            // Simulation d'ajout au panier
            const produitNom = btn.closest('.produit').querySelector('.produit-nom h2').textContent;
            const quantite = btn.closest('.produit').querySelector('input[name="quantite"]').value;
            
            // Afficher le message de succès
            const messageBox = document.getElementById('messageBox');
            const messageText = messageBox.querySelector('.message-text');
            messageText.textContent = `${produitNom} (${quantite}) ajouté au panier!`;
            messageBox.style.display = 'flex';
            
            // Animation du bouton
            btn.style.transform = 'scale(0.95)';
            setTimeout(() => {
                btn.style.transform = 'scale(1)';
            }, 150);
            
            // Auto-fermeture après 3 secondes
            setTimeout(() => {
                fermerMessage();
            }, 3000);
        }

        function fermerMessage() {
            const messageBox = document.getElementById('messageBox');
            if (messageBox) {
                messageBox.style.opacity = '0';
                setTimeout(function() {
                    messageBox.style.display = 'none';
                    messageBox.style.opacity = '1';
                }, 300);
            }
        }

        // Animation d'apparition progressive des produits
        window.addEventListener('load', function() {
            const produits = document.querySelectorAll('.produit');
            produits.forEach((produit, index) => {
                produit.style.opacity = '0';
                produit.style.transform = 'translateY(50px)';
                setTimeout(() => {
                    produit.style.transition = 'all 0.6s ease';
                    produit.style.opacity = '1';
                    produit.style.transform = 'translateY(0)';
                }, index * 100);
            });
        });
    </script>
</body>
</html>