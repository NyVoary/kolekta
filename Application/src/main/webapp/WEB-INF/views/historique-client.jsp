<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
  <title>Lokaly</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@400;700&family=Nunito:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Nunito', 'Manrope', Arial, sans-serif;
            background: linear-gradient(120deg, #e0e7ff 0%, #f8fafc 100%);
            min-height: 100vh;
            margin: 0;
        }
        .container {
            max-width: 1000px;
            margin: 40px auto;
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.15);
            padding: 40px 32px 32px 32px;
        }
        h2 {
            color: #2d3e50;
            margin-bottom: 30px;
            font-size: 2em;
            font-weight: 700;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background: #f9fafe;
            border-radius: 10px;
            overflow: hidden;
        }
        th, td {
            padding: 14px 12px;
            border-bottom: 1px solid #eaeaea;
            text-align: left;
            font-size: 1.08em;
        }
        th {
            background: #f1f3f6;
            color: #2d3e50;
            font-weight: 700;
        }
        tr:hover {
            background: #f0f4ff;
        }
        .filter-form {
            margin-bottom: 24px;
            display: flex;
            gap: 12px;
            align-items: center;
            flex-wrap: wrap;
        }
        .filter-form label {
            font-weight: 600;
            color: #4e73df;
        }
        .filter-form input[type="date"] {
            padding: 7px 12px;
            border: 1px solid #bfc9d1;
            border-radius: 5px;
            font-size: 1em;
        }
        .filter-form button {
            background: linear-gradient(90deg, #4e73df 0%, #6a82fb 100%);
            color: #fff;
            border: none;
            padding: 9px 22px;
            border-radius: 6px;
            font-size: 1em;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s, transform 0.2s;
        }
        .filter-form button:hover {
            background: linear-gradient(90deg, #2e59d9 0%, #4e73df 100%);
            transform: translateY(-2px) scale(1.03);
        }
        .empty-message {
            color: #b0b3b8;
            font-size: 1.1em;
            text-align: center;
            margin-top: 30px;
        }
        @media (max-width: 700px) {
            .container { padding: 16px 2px; }
            table, th, td { font-size: 0.95em; }
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Historique d'achat du client</h2>
    <form class="filter-form" method="get" action="/historique-client">
        <input type="hidden" name="idClient" value="${param.idClient}" />
        <label for="start">Du :</label>
        <input type="date" id="start" name="start" value="${param.start}">
        <label for="end">Au :</label>
        <input type="date" id="end" name="end" value="${param.end}">
        <button type="submit">Filtrer</button>
    </form>
    <c:if test="${empty historique}">
        <div class="empty-message">Aucune commande trouvée pour ce client.</div>
    </c:if>
    <c:forEach var="histo" items="${historique}">
        <table>
            <thead>
            <tr>
                <th>Date</th>
                <th>Montant total</th>
                <th>Frais livraison</th>
                <th>Adresse livraison</th>
                <th>Ville</th>
                <th>Téléphone</th>
                <th>Email</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${histo.commande.dateCommande}</td>
                <td>${histo.commande.montantTotal}</td>
                <td>${histo.commande.fraisLivraison}</td>
                <td>${histo.commande.adresseLivraison}</td>
                <td>${histo.commande.villeLivraison}</td>
                <td>${histo.commande.telephoneLivraison}</td>
                <td>${histo.commande.emailLivraison}</td>
            </tr>
            </tbody>
        </table>
        <c:if test="${not empty histo.produits}">
            <table>
                <thead>
                <tr>
                    <th>Produit</th>
                    <th>Quantité</th>
                    <th>Prix unitaire</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="prod" items="${histo.produits}">
                    <tr>
                        <td>${prod.idProduit}</td>
                        <td>${prod.quantite}</td>
                        <td>${prod.prixUnitaire}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <hr/>
    </c:forEach>
</div>
</body>
</html>
