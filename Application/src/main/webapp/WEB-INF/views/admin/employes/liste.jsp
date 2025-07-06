<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <!-- partial -->
      <div class="main-panel">

      <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">List Employe</h4>
                    <c:choose>
                        <c:when test="${empty employes}">
                          <h1 style="text-align: center; padding: 20px 0;">Aucun(s) employé(s)</h1>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Image</th>
                                  <th>Nom</th>
                                  <th>Date De Naissance</th>
                                  <th>Sexe</th>
                                  <th>Salaire</th>
                                  <th>Action</th>
                                </tr>
                              </thead>
                              <tbody>
                                <c:forEach var="employe" items="${employes}">
                                    <tr>
                                      <td>
                                        <img src="${pageContext.request.contextPath}/${employe.image}" alt="" width="20">
                                      </td>
                                      <td>${employe.nom}</td>
                                      <td>${employe.dtn}</td>
                                      <td>${employe.sexe}</td>
                                      <td>${employe.salaire}</td>
                                      <td>
                                        <a style="margin-right: 20px" class="btn btn-success btn-rounded btn-fw" href="/admin/employes/update/${employe.id_employe}">Update</a>
                                        <a class="btn btn-danger btn-rounded btn-fw" href="/admin/employes/remove/${employe.id_employe}">Remove</a>
                                      </td>
                                    </tr>
                                </c:forEach>
                              </tbody>
                            </table>
                          </div>
                        </c:otherwise>
                    </c:choose>
                </div>
              </div>
            </div>
      <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>
</body>

</html>

