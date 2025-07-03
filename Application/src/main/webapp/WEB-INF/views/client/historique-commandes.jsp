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
    <title>Mon Panier</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/produits.css" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png" />
</head>
<body>
<div style="margin-top: 10vh;">
    <%@ include file="includes/navbar.jsp" %>
    <h2>Historique des commandes</h2>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Montant total</th>
                <th>Frais livraison</th>
                <th>Adresse</th>
                <th>Ville</th>
                <th>Téléphone</th>
                <th>Email</th>
                <th>Statut</th>
                <th>Progression</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="commande" items="${commandes}">
                <tr>
                    <td>${commande.id}</td>
                    <td>${commande.formatterDateCommande()}</td>
                    <td>${commande.montantTotal}</td>
                    <td>${commande.fraisLivraison}</td>
                    <td>${commande.adresseLivraison}</td>
                    <td>${commande.villeLivraison}</td>
                    <td>${commande.telephoneLivraison}</td>
                    <td>${commande.emailLivraison}</td>
                    <td>
                        <c:choose>
                            <c:when test="${commande.statutActuel != null}">
                                ${commande.statutActuel.libelle}
                            </c:when>
                            <c:otherwise>
                                Inconnu
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${commande.progression == -1}">
                                Annulée
                            </c:when>
                            <c:otherwise>
                                ${commande.progression}%
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <%@ include file="includes/footer.jsp" %>
</div>
</body>
</html>
