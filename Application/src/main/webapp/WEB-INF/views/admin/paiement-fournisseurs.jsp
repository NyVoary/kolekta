<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../includes/head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/liste-produit.css">

<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
            <form class="row mb-4 justify-content-center" method="get" action="/admin/paiement-fournisseurs">
                <!-- Date de début -->
                <div class="col-12 col-md-3 mb-2">
                    <label for="dateDebut">Date début :</label>
                    <input type="date" class="form-control form-control-lg" name="dateDebut" id="dateDebut" value="${dateDebut}" />
                </div>

                <!-- Date de fin -->
                <div class="col-12 col-md-3 mb-2">
                    <label for="dateFin">Date fin :</label>
                    <input type="date" class="form-control form-control-lg" name="dateFin" id="dateFin" value="${dateFin}" />
                </div>

                <!-- Fournisseur -->
                <div class="col-12 col-md-3 mb-2">
                    <label for="nomFournisseur">Fournisseur :</label>
                    <select name="nomFournisseur" id="nomFournisseur" class="form-control form-control-lg">
                        <option value="">Tous les fournisseurs</option>
                        <c:forEach var="fournisseur" items="${fournisseurs}">
                            <option value="${fournisseur.nom}" 
                                ${nomFournisseur == fournisseur.nom ? 'selected' : ''}>
                                ${fournisseur.nom}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-12 col-md-2 mb-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary btn-lg w-100 rounded-pill shadow">Rechercher</button>
                </div>
            </form>

            <style>
                .table-container {
                    padding: 20px;
                    background: #fff;
                    border-radius: 10px;
                    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
                    margin: 20px 0;
                }
                
                .table-container h1 {
                    color: #333;
                    margin-bottom: 20px;
                    font-weight: 600;
                    padding-bottom: 10px;
                    border-bottom: 1px solid #eee;
                }
                
                .table-responsive {
                    overflow-x: auto;
                }
                
                table {
                    width: 100%;
                    border-collapse: separate;
                    border-spacing: 0;
                    border-radius: 10px;
                    overflow: hidden;
                    box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
                    font-size: 0.9rem;
                }
                
                th {
                    background-color: #4e73df;
                    color: white;
                    font-weight: 600;
                    padding: 15px;
                    text-align: left;
                    position: sticky;
                    top: 0;
                }
                
                td {
                    padding: 12px 15px;
                    border-bottom: 1px solid #f0f0f0;
                    vertical-align: middle;
                }
                
                tr:nth-child(even) {
                    background-color: #f8f9fa;
                }
                
                tr:hover {
                    background-color: #f1f3f9;
                }
                
                .currency {
                    text-align: right;
                    font-family: 'Courier New', monospace;
                }
                
                .alert-info {
                    background-color: #d1ecf1;
                    color: #0c5460;
                    padding: 10px 15px;
                    border-radius: 4px;
                    border: 1px solid #bee5eb;
                    margin: 20px 0;
                }
                
                @media (max-width: 768px) {
                    .form-control-lg {
                        font-size: 0.9rem;
                    }
                    
                    th, td {
                        padding: 8px 10px;
                        font-size: 0.85rem;
                    }
                }
            </style>

            <div class="table-container">
                <h1>Historique des paiements fournisseurs</h1>

                <c:if test="${empty paiements}">
                    <div class="alert alert-info">Aucun paiement trouvé.</div>
                </c:if>

                <c:if test="${not empty paiements}">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Fournisseur</th>
                                    <th>Date paiement</th>
                                    <th>Montant</th>
                                    <th>Mode paiement</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="paiement" items="${paiements}">
                                    <tr>
                                        <td>${paiement.id}</td>
                                        <td>${paiement.fournisseur.nom}</td>
                                        <td>${paiement.datePaiementFormatted}</td>
                                        <td class="currency"><fmt:formatNumber value="${paiement.montant}" type="currency" currencyCode="MGA"/></td>
                                        <td>${paiement.modePaiement}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
        <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>
  </div>
</body>
</html>