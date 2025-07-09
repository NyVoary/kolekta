<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paiements Camions</title>
</head>
<body>
    <div>
        <!-- Messages de notification -->
        <c:if test="${not empty message}">
            <div style="background-color: #d4edda; padding: 10px; margin: 10px 0; border: 1px solid #c3e6cb;">
                <span>${message}</span>
                <button onclick="fermerMessage()" style="float: right;">&times;</button>
            </div>
        </c:if>

        <c:if test="${not empty erreur}">
            <div style="background-color: #f8d7da; padding: 10px; margin: 10px 0; border: 1px solid #f5c6cb;">
                <span>${erreur}</span>
                <button onclick="fermerMessage()" style="float: right;">&times;</button>
            </div>
        </c:if>

        <div style="padding: 20px; background-color: #f8f9fa;">
            <form method="get" action="/admin/paiement-camions">
                <!-- Date de début -->
                <div style="display: inline-block; margin-right: 10px;">
                    <label>Date début :</label>
                    <input type="datetime-local" name="dateDebut" value="${dateDebut}">
                </div>

                <!-- Date de fin -->
                <div style="display: inline-block; margin-right: 10px;">
                    <label>Date fin :</label>
                    <input type="datetime-local" name="dateFin" value="${dateFin}">
                </div>

                <!-- Immatriculation -->
                <div style="display: inline-block; margin-right: 10px;">
                    <label>Camion :</label>
                    <select name="immatriculation">
                        <option value="">Tous les camions</option>
                        <c:forEach var="camion" items="${camions}">
                            <option value="${camion.immatriculation}" 
                                ${immatriculation == camion.immatriculation ? 'selected' : ''}>
                                ${camion.immatriculation}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit">Rechercher</button>
            </form>
        </div>

        <div style="padding: 20px;">
            <h1>Historique des paiements camions</h1>

            <c:if test="${empty paiements}">
                <p>Aucun paiement trouvé.</p>
            </c:if>

            <c:if test="${not empty paiements}">
                <table border="1" style="width: 100%; border-collapse: collapse;">
                    <thead>
                        <tr style="background-color: #e9ecef;">
                            <th>ID</th>
                            <th>Camion</th>
                            <th>Date paiement</th>
                            <th>Montant</th>
                            <th>Mode paiement</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="paiement" items="${paiements}">
                            <tr>
                                <td>${paiement.id}</td>
                                <td>${paiement.commandeCamion.assignationCamion.camion.immatriculation}</td>
                                <td>${paiement.datePaiement}</td>
                                <td><fmt:formatNumber value="${paiement.montant}" type="currency" currencyCode="MGA"/></td>
                                <td>${paiement.modePaiement}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
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
            setTimeout(function() {
                const messageBox = document.getElementById('messageBox');
                if (messageBox) {
                    messageBox.style.display = 'none';
                }
            }, 5000);
        };
    </script>
</body>
</html>