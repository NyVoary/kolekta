<jsp:useBean id="acces" class="com.example.demo.entity.Acces" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="includes/head.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
<link rel="stylesheet" href="/css/barChart.css">

<body>
  <div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row justify-content-center align-items-center" style="min-height:60vh;">
            <div class="col-md-8 text-center">
              <h1 class="lead mb-4">Bonjour, <strong>${acces.nomUtilisateur}</strong> (rôle : <strong>${acces.role}</strong>)</h1>
              <div class="row justify-content-center">
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-client" class="btn btn-outline-primary btn-lg w-100">Gérer les clients</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/fournisseurs/liste" class="btn btn-outline-success btn-lg w-100">Gérer les fournisseurs</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-produit" class="btn btn-outline-info btn-lg w-100">Gérer les produits</a>
                </div>
              </div>
              <div class="row justify-content-center">
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-employe" class="btn btn-outline-warning btn-lg w-100">Gérer les employés</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/liste-camion" class="btn btn-outline-secondary btn-lg w-100">Gérer les camions</a>
                </div>
                <div class="col-md-4 mb-3">
                  <a href="/admin/commande" class="btn btn-outline-dark btn-lg w-100">Commandes & Livraisons</a>
                </div>
              </div>
            </div>
          </div>

          <!-- Section des tâches améliorée -->
          <div class="col-md-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Tâches assignées</h4>
                  <p class="card-description">
                    Pour: <strong>${not empty nomEmploye ? nomEmploye : 'Non spécifié'}</strong>
                  </p>
                  <div class="table-responsive">
                    <table class="table table-striped table-borderless">
                      <thead>
                        <tr>
                          <th>Tâche</th>
                          <th>Description</th>
                          <th>Durée</th>
                          <th>Date début</th>
                          <th>Priorité</th>
                          <th>Statut</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:choose>
                          <c:when test="${not empty taches && !taches.isEmpty()}">
                            <c:forEach items="${taches}" var="tache">
                              <tr>
                                <td><strong>${tache.titre}</strong></td>
                                <td>
                                  <c:choose>
                                    <c:when test="${not empty tache.description}">
                                      ${fn:substring(tache.description, 0, 30)}${fn:length(tache.description) > 30 ? '...' : ''}
                                    </c:when>
                                    <c:otherwise>
                                      <span class="text-muted">Aucune description</span>
                                    </c:otherwise>
                                  </c:choose>
                                </td>
                                <td class="font-weight-bold">
                                  <c:if test="${not empty tache.dateDebut and not empty tache.dateFin}">
                                    <c:set var="dureeHeures" value="${java.time.Duration.between(tache.dateDebut, tache.dateFin).toHours()}"/>
                                    <c:set var="dureeMinutes" value="${java.time.Duration.between(tache.dateDebut, tache.dateFin).toMinutesPart()}"/>
                                    ${dureeHeures}h${dureeMinutes}
                                  </c:if>
                                </td>
                                <td>
                                  <c:if test="${not empty tache.dateDebut}">
                                    <fmt:formatDate value="${tache.dateDebut}" pattern="dd/MM/yyyy HH:mm"/>
                                  </c:if>
                                </td>
                                <td>
                                  <c:choose>
                                    <c:when test="${tache.priorite == 'Urgente'}">
                                      <span class="badge badge-danger">${tache.priorite}</span>
                                    </c:when>
                                    <c:when test="${tache.priorite == 'Haute'}">
                                      <span class="badge badge-warning">${tache.priorite}</span>
                                    </c:when>
                                    <c:when test="${tache.priorite == 'Moyenne'}">
                                      <span class="badge badge-info">${tache.priorite}</span>
                                    </c:when>
                                    <c:otherwise>
                                      <span class="badge badge-secondary">${tache.priorite}</span>
                                    </c:otherwise>
                                  </c:choose>
                                </td>
                                <td class="font-weight-medium">
                                  <c:choose>
                                    <c:when test="${tache.statusActuelle == 'Terminée'}">
                                      <div class="badge badge-success">Terminée</div>
                                    </c:when>
                                    <c:when test="${tache.statusActuelle == 'En cours'}">
                                      <div class="badge badge-primary">En cours</div>
                                    </c:when>
                                    <c:when test="${tache.statusActuelle == 'Annulée'}">
                                      <div class="badge badge-dark">Annulée</div>
                                    </c:when>
                                    <c:otherwise>
                                      <div class="badge badge-light">En attente</div>
                                    </c:otherwise>
                                  </c:choose>
                                </td>
                              </tr>
                            </c:forEach>
                          </c:when>
                          <c:otherwise>
                            <tr>
                              <td colspan="6" class="text-center">
                                <c:choose>
                                  <c:when test="${acces.idEmploye == null}">
                                    <span class="text-warning">Aucun employé associé à ce compte</span>
                                  </c:when>
                                  <c:otherwise>
                                    <span class="text-muted">Aucune tâche assignée actuellement</span>
                                  </c:otherwise>
                                </c:choose>
                              </td>
                            </tr>
                          </c:otherwise>
                        </c:choose>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
        </div>
        <%@ include file="includes/footer.jsp" %>
      </div>
    </div>   
  </div>
  
  <!-- Chart.js -->
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Récupération des données depuis le serveur
      const statistics = {
        <c:if test="${not empty statistics}">
          general: {
            totalProduits: ${statistics.generale.totalProduits},
            produitsRupture: ${statistics.generale.produitsRupture},
            produitsStockBas: ${statistics.generale.produitsStockBas},
            nouveauxProduits: ${statistics.generale.nouveauxProduits}
          },
          top3Plus: {
            noms: [<c:forEach var="nom" items="${statistics.top3Plus.noms}" varStatus="status">"${nom}"<c:if test="${!status.last}">, </c:if></c:forEach>],
            quantites: [<c:forEach var="qty" items="${statistics.top3Plus.quantites}" varStatus="status">${qty}<c:if test="${!status.last}">, </c:if></c:forEach>]
          },
          top3Moins: {
            noms: [<c:forEach var="nom" items="${statistics.top3Moins.noms}" varStatus="status">"${nom}"<c:if test="${!status.last}">, </c:if></c:forEach>],
            quantites: [<c:forEach var="qty" items="${statistics.top3Moins.quantites}" varStatus="status">${qty}<c:if test="${!status.last}">, </c:if></c:forEach>]
          }
        </c:if>
      };

      // Graphiques (conservés au cas où)
      if (document.getElementById('barChart')) {
        const ctx1 = document.getElementById('barChart').getContext('2d');
        // ... reste du code des graphiques ...
      }
    });
  </script>
</body>
</html>