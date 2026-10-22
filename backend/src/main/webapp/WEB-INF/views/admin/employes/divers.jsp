<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../includes/head.jsp" %>

<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
      <div class="container-fluid page-body-wrapper">
        <%@ include file="../includes/sidebar.jsp" %>
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
                  <h4 class="card-title">Formulaire Acces</h4>

                <c:if test="${not empty erreur}">
                    <div class="alert alert-danger mt-2">${erreur}</div>
                </c:if>
                  <c:choose>
                      <c:when test="${empty acc}">
                        <form class="forms-sample" method="post">
                          <div class="form-group">
                            <%-- <input type="hidden" name="idAcces" value="${acces.idAcces}"> --%>
                            <label for="employe">Employe</label>
                            <select class="form-control" name="idEmp" id="employe">
                              <option value="">Employes</option>
                                <c:forEach var="employe" items="${employes}">
                                  <option value="${employe.id_employe}">${employe.nom}</option>
                                </c:forEach>
                            </select>
                          </div>
                          <div class="form-group">
                            <label for="username">Nom D'utilisateur</label>
                            <input type="text" name="nom" value="Jhon Doe" class="form-control" id="username" placeholder="Ex. Jhon Doe">
                          </div>
                          <div class="form-group">
                            <label for="mdp">Mot de Passe</label>
                            <input name="mdp" class="form-control" id="mdp" placeholder="Ex. mpd123" value="mdp123">
                          </div>
                          <div class="form-group">
                            <label for="role">Role</label>
                            <select class="form-control" name="role" id="role">
                                <c:forEach var="role" items="${roles}">
                                  <option value="${role}">${role}</option>
                                </c:forEach>
                            </select>
                          </div>
                          <button type="submit" class="btn btn-primary btn-rounded mr-2">Submit</button>
                        </form>
                      </c:when>
                      <c:otherwise>
                        <form class="forms-sample" method="post">
                          <input type="hidden" name="id" value="${acc.idAcces}">
                          <div class="form-group">
                            <label for="employe">Employe</label>
                            <select class="form-control" name="idEmp" id="employe">
                              <option value="">Employes</option>
                                <c:forEach var="employe" items="${employes}">
                                  <option value="${employe.id_employe}" <c:if test="${employe.id_employe == acc.employes.id_employe}">selected</c:if>>${employe.nom}</option>
                                </c:forEach>
                            </select>
                          </div>
                          <div class="form-group">
                            <label for="username">Nom D'utilisateur</label>
                            <input type="text" name="nom" value="${acc.nomUtilisateur}" class="form-control" id="username" placeholder="Ex. Jhon Doe">
                          </div>
                          <div class="form-group">
                            <label for="mdp">Mot de Passe</label>
                            <input name="mdp" class="form-control" id="${acc.motDePasse}" placeholder="Ex. mpd123" value="mdp123">
                          </div>
                          <div class="form-group">
                            <label for="role">Role</label>
                            <select class="form-control" name="role" id="role">
                                <c:forEach var="role" items="${roles}">
                                  <option value="${role}" <c:if test="${role == acc.role}">selected</c:if>>${role}</option>
                                </c:forEach>
                            </select>
                          </div>
                          <button type="submit" class="btn btn-primary btn-rounded mr-2">Submit</button>
                        </form>
                      </c:otherwise>
                  </c:choose>

                </div>
              </div>
            </div>
              </div>
            </div>
          <%@ include file="../includes/footer.jsp" %>
          </div>
      </div>
  </div>
</body>

</html>

