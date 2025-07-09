<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paiements Fournisseurs</title>
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
            <div class="navbar-menu-wrapper d-flex align-items-center">
                <!-- Partie centrale -->
                <div class="d-flex justify-content-center flex-grow-1">
                    <form method="get" action="/admin/paiement-fournisseurs" class="form-inline">
                        <!-- Date de début -->
                        <ul class="navbar-nav mr-lg-2">
                            <li class="nav-item nav-search d-none d-lg-block">
                                <div class="input-group">
                                    <input
                                        type="date"
                                        class="form-control"
                                        id="dateDebut"
                                        name="dateDebut"
                                        value="${dateDebut != null ? dateDebut : ''}"
                                    >
                                </div>
                            </li>
                        </ul>

                        <!-- Date de fin -->
                        <ul class="navbar-nav mr-lg-2">
                            <li class="nav-item nav-search d-none d-lg-block">
                                <div class="input-group">
                                    <input
                                        type="date"
                                        class="form-control"
                                        id="dateFin"
                                        name="dateFin"
                                        value="${dateFin != null ? dateFin : ''}"
                                    >
                                </div>
                            </li>
                        </ul>

                        <!-- Fournisseur avec dropdown -->
                        <div class="input-group mr-2">
                            <div class="dropdown">
                                <input type="hidden" id="nomFournisseur" name="nomFournisseur" value="${nomFournisseur != null ? nomFournisseur : ''}">

                                <button type="button" class="btn btn-outline-info dropdown-toggle" id="dropdownFournisseur" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ${nomFournisseur != null && !nomFournisseur.isEmpty() ? nomFournisseur : "Fournisseur"}
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownFournisseur">
                                    <c:forEach var="fournisseur" items="${fournisseurs}">
                                        <a class="dropdown-item" href="#" data-value="${fournisseur.nom}">
                                            ${fournisseur.nom}
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
                                        document.getElementById('nomFournisseur').value = value;
                                        document.getElementById('dropdownFournisseur').textContent = value;
                                    });
                                });
                            });
                        </script>

                        <!-- Bouton de recherche -->
                        <button type="submit" class="btn btn-outline-secondary btn-rounded btn-icon">
                            <i class="ti-search text-info"></i>                          
                        </button>
                    </form> 
                </div>
            </div>
        </nav>  

        <div class="container-fluid page-body-wrapper">
            <div class="main-panel">
                <div class="content-wrapper">
                    <h1>Historique des paiements fournisseurs</h1>

                    <c:if test="${empty paiements}">
                        <p>Aucun paiement trouvé.</p>
                    </c:if>

                    <c:if test="${not empty paiements}">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Fournisseur</th>
                                    <th>Date</th>
                                    <th>Montant</th>
                                    <th>Mode de paiement</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="paiement" items="${paiements}">
                                    <tr>
                                        <td>${paiement.id}</td>
                                        <td>${paiement.fournisseur.nom}</td>
                                        <td>${paiement.datePaiementFormatted}</td>
                                        <td><fmt:formatNumber value="${paiement.montant}" type="currency" currencyCode="MGA" /></td>
                                        <td>${paiement.modePaiement}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <script>
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