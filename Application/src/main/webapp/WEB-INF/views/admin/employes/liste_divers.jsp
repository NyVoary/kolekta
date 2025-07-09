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
                  <h4 class="card-title">List Divers</h4>
                    <c:choose>
                        <c:when test="${empty accs}">
                          <h1 style="text-align: center; padding: 20px 0;">Aucun(s) diverss(s)</h1>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                            <table class="table table-hover">
                              <thead>
                                <tr>
                                  <th>Employe</th>
                                  <th>Nom</th>
                                  <th>Mot de Passe</th>
                                  <th>Role</th>
                                  <th>Action</th>
                                </tr>
                              </thead>
                              <tbody>
                                <c:forEach var="acc" items="${accs}">
                                    <tr>
                                      <td>${acc.employes.nom}</td>
                                      <td>${acc.nomUtilisateur}</td>
                                      <td>${acc.motDePasse}</td>
                                      <td>${acc.role}</td>
                                      <td>
                                        <a style="margin-right: 20px" class="btn btn-success btn-rounded btn-fw" href="/admin/divers/update/${acc.idAcces}">Update</a>
                                        <a class="btn btn-danger btn-rounded btn-fw" href="/admin/divers/remove/${acc.idAcces}">Remove</a>
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

