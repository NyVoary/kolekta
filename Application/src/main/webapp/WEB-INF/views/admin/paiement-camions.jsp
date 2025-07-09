<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
            <form class="row mb-4 justify-content-center" method="get" action="/admin/paiement-camions">
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

                <!-- Immatriculation -->
                <div class="col-12 col-md-3 mb-2">
                    <label for="immatriculation">Camion :</label>
                    <select name="immatriculation" id="immatriculation" class="form-control form-control-lg">
                        <option value="">Tous les camions</option>
                        <c:forEach var="camion" items="${camions}">
                            <option value="${camion.immatriculation}" 
                                ${immatriculation == camion.immatriculation ? 'selected' : ''}>
                                ${camion.immatriculation}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-12 col-md-2 mb-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary btn-lg w-100 rounded-pill shadow">Rechercher</button>
                </div>
            </form>

            <style>
                .stat-card {
                    box-shadow: 0 6px 24px 0 rgba(79,140,255,0.10), 0 1.5px 6px 0 rgba(0,0,0,0.06);
                    border-radius: 1.5rem;
                    margin-bottom: 2rem;
                    background: #fff;
                    border: 1.5px solid #e6eaf3;
                    transition: box-shadow 0.2s, border 0.2s;
                    min-height: 170px;
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    text-align: left;
                }
                .stat-card:not(:last-child) {
                    margin-right: 1.5rem;
                }
                .stat-card h6 {
                    text-align: center;
                    font-size: 1.05rem;
                    font-weight: 600;
                    letter-spacing: 0.01em;
                    color: #6c757d;
                    margin-bottom: 0.7rem;
                    margin-top: 0.2rem;
                    width: 100%;
                    display: block;
                }
                .stat-card h2 {
                    text-align: center;
                    font-size: 2.2rem;
                    font-weight: 700;
                    margin-bottom: 0.2rem;
                    letter-spacing: 0.01em;
                    width: 100%;
                    display: block;
                }
                .stat-card ul, .stat-card ol {
                    margin-top: 0.7rem;
                    margin-bottom: 0;
                    padding-left: 1.2rem;
                    text-align: left;
                }
                .stat-card span.text-muted {
                    text-align: left;
                    display: block;
                }
                .stat-card .center-list {
                    text-align: center;
                    padding-left: 0;
                    margin-left: 0;
                }
                .stat-row {
                    margin-left: 0;
                    margin-right: 0;
                    gap: 2rem;
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: center;
                }
                
                /* Style pour le tableau */
                .table-container {
                    padding: 20px;
                    background: #fff;
                    border-radius: 10px;
                    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
                }
                
                .table-container h1 {
                    color: #333;
                    margin-bottom: 20px;
                    font-weight: 600;
                }
                
                table {
                    width: 100%;
                    border-collapse: separate;
                    border-spacing: 0;
                    border-radius: 10px;
                    overflow: hidden;
                    box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
                }
                
                th {
                    background-color: #4e73df;
                    color: white;
                    font-weight: 600;
                    padding: 15px;
                    text-align: left;
                }
                
                td {
                    padding: 12px 15px;
                    border-bottom: 1px solid #f0f0f0;
                }
                
                tr:nth-child(even) {
                    background-color: #f8f9fa;
                }
                
                tr:hover {
                    background-color: #f1f3f9;
                }
                
                @media (max-width: 991px) {
                    .stat-row { gap: 1rem; }
                    .stat-card { margin-right: 0; margin-bottom: 1.2rem; }
                }
            </style>

            <div class="table-container">
                <h1>Historique des paiements camions</h1>
    
                <c:if test="${empty paiements}">
                    <div class="alert alert-info">Aucun paiement trouvé.</div>
                </c:if>
    
                <c:if test="${not empty paiements}">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
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