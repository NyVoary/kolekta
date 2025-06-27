<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Historique des Achats</title>
</head>
<body>
<h2>Historique des Achats</h2>
<table border="1">
    <thead>
    <tr>
        <th>Produit</th>
        <th>Montant</th>
        <th>Date d'achat</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="achat" items="${historiqueAchats}">
        <tr>
            <td>${achat.produit}</td>
            <td>${achat.montant}</td>
            <td>${achat.dateAchat}</td>
            <td>${achat.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>