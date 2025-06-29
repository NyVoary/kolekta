<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historique d'achat</title>
    <link rel="stylesheet" href="/static/css/historique-achat.css"/>
</head>
<body class="sidebar-light">
<div class="container">
    <div class="page-header">
        <h1 class="page-title">
            <span class="icon">📋</span>
            Historique d'achat
        </h1>
    </div>

    <form method="get" action="/historique-achat" class="filter-form">
        <div class="filter-grid">
            <div class="form-group">
                <label for="dateDebut">Date début</label>
                <input type="date" id="dateDebut" name="dateDebut" class="form-control" value="${dateDebut}" />
            </div>
            <div class="form-group">
                <label for="dateFin">Date fin</label>
                <input type="date" id="dateFin" name="dateFin" class="form-control" value="${dateFin}" />
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    <i class="mdi mdi-filter-variant"></i> Filtrer
                </button>
            </div>
        </div>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <c:if test="${empty historique}">
        <div class="empty-state">
            <div class="empty-state-icon">🛒</div>
            <h3 class="empty-state-title">Aucun achat trouvé</h3>
            <p class="empty-state-text">Vous n'avez effectué aucun achat dans la période sélectionnée.</p>
        </div>
    </c:if>

    <c:if test="${not empty historique}">
        <div class="timeline">
            <c:forEach var="achat" items="${historique}">
                <div class="achat-card">
                    <div class="timeline-dot">
                        <i class="mdi mdi-check-circle"></i>
                    </div>
                    <div class="achat-header">
                        <div class="achat-date">
                            <i class="mdi mdi-calendar"></i> ${achat.dateCommande}
                        </div>
                        <div class="achat-montant">
                            <i class="mdi mdi-cash-multiple"></i> ${achat.montantTotal} €
                        </div>
                        <span class="badge badge-success">
                            <i class="mdi mdi-check"></i> Validée
                        </span>
                    </div>
                    <div>
                        <span style="font-weight: 600; color: var(--text-light);">Produits :</span>
                        <ul class="produit-list">
                            <c:forEach var="prod" items="${achat.produits}">
                                <li><i class="mdi mdi-cube-outline"></i> ${prod}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
</body>
</html>