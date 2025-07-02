<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h1 class="h3">Liste des fournisseurs</h1>
          <a href="/admin/fournisseurs/ajout" class="btn btn-primary">
            <i class="ti-plus"></i> Ajouter un fournisseur
          </a>
        </div>
        <div class="card p-3 shadow-sm">
          <div class="table-responsive">
            <table class="table table-hover table-bordered bg-white rounded">
              <thead class="thead-light">
                <tr>
                  <th>Image</th>
                  <th>Nom</th>
                  <th>Adresse</th>
                  <th>Téléphone</th>
                  <th>Email</th>
                  <th>Produits</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="fournisseur" items="${fournisseurs}">
                  <tr>
                    <td><img src="/static/images/${fournisseur.image}" alt="Image fournisseur" style="width:48px;height:48px;object-fit:cover;border-radius:50%;border:2px solid #e2e8f0;"></td>
                    <td><b>${fournisseur.nom}</b></td>
                    <td>${fournisseur.adresse}</td>
                    <td>${fournisseur.telephone}</td>
                    <td>${fournisseur.email}</td>
                    <td>
                      <c:choose>
                        <c:when test="${not empty produitsParFournisseur[fournisseur.id]}">
                          <ul class="mb-0 pl-3">
                            <c:forEach var="produit" items="${produitsParFournisseur[fournisseur.id]}">
                              <li>${produit.nom} <span class="text-muted">(${produit.uniteMesure}, ${produit.prixVente} €)</span></li>
                            </c:forEach>
                          </ul>
                        </c:when>
                        <c:otherwise>
                          <span class="text-muted">Aucun produit</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <a href="/admin/fournisseurs/edit?id=${fournisseur.id}" class="btn btn-warning btn-sm" title="Modifier"><i class="ti-pencil"></i></a>
                      <a href="/admin/fournisseurs/delete?id=${fournisseur.id}" class="btn btn-danger btn-sm" title="Supprimer" onclick="return confirm('Supprimer ce fournisseur ?');"><i class="ti-trash"></i></a>
                      <a href="/admin/fournisseurs/ajout-disponibilite" class="btn btn-info btn-sm" title="Ajouter disponibilité"><i class="ti-package"></i></a>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>

