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
        <div class="content-wrapper">
              <div class="row">
              <div class="col-6 mx-auto grid-margin">
              <div class="card">
                <div class="card-body">
              <%-- Affiche le message s'il existe --%>
                <c:if test="${not empty message}">
                    <div class="alert alert-success mt-2">${message}</div>
                </c:if>
                  <h4 class="card-title">Formulaire Employes</h4>

                <c:if test="${not empty erreur}">
                    <div class="alert alert-danger mt-2">${erreur}</div>
                </c:if>

                <form class="forms-sample" method="post">
                    <div class="form-group">
                      <label for="titre">Titre</label>
                      <input type="text" name="titre" value="Le titre" class="form-control" id="titre" placeholder="Titre">
                    </div>
                    <div class="form-group">
                      <label for="description">Description</label>
                      <textarea name="description" class="form-control" id="description" placeholder="Desciption">La description</textarea>
                    </div>
                    <div class="form-group">
                      <label for="employe">Employe</label>
                      <select class="form-control" name="id_employe" id="employe">
                          <c:forEach var="employe" items="${employes}">
                            <option value="${employe.id_employe}">${employe.nom}</option>
                          </c:forEach>
                      </select>
                    </div>
                    <div class="form-group">
                      <label for="priorite">Priorite</label>
                      <select class="form-control" name="priorite" id="priorite">
                          <c:forEach var="priorite" items="${priorites}">
                            <option value="${priorite}">${priorite}</option>
                          </c:forEach>
                      </select>
                    </div>
                    <div class="form-group">
                      <label for="status">Status</label>
                      <select class="form-control" name="status" id="status">
                          <c:forEach var="statu" items="${status}">
                            <option value="${statu}">${statu}</option>
                          </c:forEach>
                      </select>
                    </div>
                    <div class="form-group">
                      <label for="date-debut">Date Debut</label>
                      <input type="datetime-local" class="form-control" value="2025-10-10 12:03" name="date_debut" id="date-debut">
                    </div>
                    <div class="form-group">
                      <label for="date-fin">Date Fin</label>
                      <input type="datetime-local" class="form-control" value="2025-10-10 12:03" name="date_fin" id="date-fin">
                    </div>
                    <button type="submit" class="btn btn-primary btn-rounded mr-2">Submit</button>
                  </form>
                </div>
              </div>
            </div>
        <!-- partial:partials/_footer.html -->
      <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>  
  </div>
</body>

</html>

