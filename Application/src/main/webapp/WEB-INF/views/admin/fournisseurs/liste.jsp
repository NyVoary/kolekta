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
        <h1>Liste des fournisseurs</h1>
  
        <c:forEach var="fournisseur" items="${fournisseurs}">
          <div class="card mb-4" style="box-shadow:0 2px 12px rgba(60,72,100,0.07);border-radius:14px;padding:24px;">
            <div style="display:flex;align-items:center;gap:24px;">
              <div style="flex:1;">
                <h3 style="margin:0 0 8px 0;">${fournisseur.nom}</h3>
                <div><b>Adresse :</b> ${fournisseur.adresse}</div>
                <div><b>Téléphone :</b> ${fournisseur.telephone}</div>
                <div><b>Email :</b> ${fournisseur.email}</div>
              </div>
              <div>
                <img src="/static/images/${fournisseur.image}" alt="Image fournisseur" style="width:80px;height:80px;object-fit:cover;border-radius:50%;border:2px solid #e2e8f0;">
              </div>
            </div>
            <div style="margin-top:18px;">
              <b>Produits :</b>
              <ul style="margin:8px 0 0 18px;padding:0;list-style:disc;">
                <c:forEach var="produit" items="${produitsParFournisseur[fournisseur.id]}">
                  <li style="margin-bottom:2px;">
                    <span style="font-weight:600;">${produit.nom}</span> - ${produit.uniteMesure} - ${produit.prixVente} €
                  </li>
                </c:forEach>
                <c:if test="${empty produitsParFournisseur[fournisseur.id]}">
                  <li style="color:#a0aec0;">Aucun produit pour ce fournisseur.</li>
                </c:if>
              </ul>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
</body>
</html>

