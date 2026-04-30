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
              <div class="col-12 grid-margin">
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
                  <c:choose>
                      <c:when test="${empty employe}">
                        <form class="form-sample" method="post" enctype="multipart/form-data">
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Nom</label>
                                <div class="col-sm-9">
                                  <input type="text" name="nom" placeholder="Ex. Jhon" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Prénom</label>
                                <div class="col-sm-9">
                                  <input type="text" name="prenoms" placeholder="Ex. Doe" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Genre</label>
                                <div class="col-sm-9">
                                  <select class="form-control" name="sexe">
                                    <c:forEach var="s" items="${sexes}">
                                        <option value="${s}">
                                            ${s}
                                        </option>
                                    </c:forEach>
                                  </select>
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Date Naissance</label>
                                <div class="col-sm-9">
                                  <input class="form-control" name="dtn" type="date">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Email</label>
                                <div class="col-sm-9">
                                  <input class="form-control" name="email" placeholder="Ex. JhonDoe@gmail.com" type="mail">
                                </div>
                              </div>
                            </div>
                          <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Téléphone</label>
                                <div class="col-sm-9">
                                  <input class="form-control" name="telephone" placeholder="Ex. +261340000000">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Mot de Passe</label>
                                <div class="col-sm-9">
                                  <input type="text" name="mdp" placeholder="Ex. mpd180" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Adresse</label>
                                <div class="col-sm-9">
                                  <input type="text" name="adresse" placeholder="Ex. Lot IA 10 Analakely" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Poste</label>
                                <div class="col-sm-9">
                                  <input type="text" name="post" placeholder="Ex. RH" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Salaire</label>
                                <div class="col-sm-9">
                                  <input type="number" name="salaire" placeholder="Ex. 5000" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Date Embauche</label>
                                <div class="col-sm-9">
                                  <input type="date" name="date_embauche" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Date Fin Embauche</label>
                                <div class="col-sm-9">
                                  <input type="date" name="date_fin_embauche" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Mode Payement</label>
                                <div class="col-sm-9">
                                  <select class="form-control" name="mode_paiement">
                                    <c:forEach var="p" items="${payments}">
                                        <option value="${p}" <c:if test="${employe.mode_paiement == p}">selected</c:if>>
                                            ${p}
                                        </option>
                                    </c:forEach>
                                  </select>
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Image</label>
                                <div class="col-sm-9">
                                  <input type="file" name="image" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group">
                                <button class="btn btn-primary btn-lg">Valider</button>
                              </div>
                            </div>
                          </div>
                        </form>
                      </c:when>
                      <c:otherwise>
                        <form class="form-sample" method="post" enctype="multipart/form-data">
                          <input type="hidden" name="id_employe" value="${employe.id_employe}">
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Nom</label>
                                <div class="col-sm-9">
                                  <input type="text" name="nom" placeholder="Ex. Jhon" value="${employe.nom}" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Prénom</label>
                                <div class="col-sm-9">
                                  <input type="text" name="prenoms" placeholder="Ex. Doe" value="${employe.prenoms}" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Genre</label>
                                <div class="col-sm-9">
                                  <select class="form-control" name="sexe">
                                    <c:forEach var="s" items="${sexes}">
                                        <option value="${s}" <c:if test="${employe.sexe == s}">selected</c:if>>
                                            ${s}
                                        </option>
                                    </c:forEach>
                                  </select>
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Date Naissance</label>
                                <div class="col-sm-9">
                                  <input class="form-control" name="dtn" value="${employe.dtn}" type="date">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Email</label>
                                <div class="col-sm-9">
                                  <input class="form-control" name="email" value="${employe.email}" placeholder="Ex. JhonDoe@gmail.com" type="mail">
                                </div>
                              </div>
                            </div>
                          <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Téléphone</label>
                                <div class="col-sm-9">
                                  <input class="form-control" name="telephone" value="${employe.telephone}" placeholder="Ex. +261340000000">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Mot de Passe</label>
                                <div class="col-sm-9">
                                  <input type="text" name="mdp" value="${employe.mdp}" placeholder="Ex. mpd180" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Adresse</label>
                                <div class="col-sm-9">
                                  <input type="text" name="adresse" value="${employe.adresse}" placeholder="Ex. Lot IA 10 Analakely" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Post</label>
                                <div class="col-sm-9">
                                  <input type="text" name="post" value="Baba" placeholder="Ex. PostMalone" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Salaire</label>
                                <div class="col-sm-9">
                                  <input type="number" name="salaire" value="1000" placeholder="${employe.salaire}" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Date Embauche</label>
                                <div class="col-sm-9">
                                  <input type="date" name="date_embauche" value="${employe.date_embauche}" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Date Fin Embauche</label>
                                <div class="col-sm-9">
                                  <input type="date" name="date_fin_embauche" value="${employe.date_fin_embauche}" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Mode Payement</label>
                                <div class="col-sm-9">                                  
                                  <select class="form-control" name="mode_paiement">
                                    <c:forEach var="p" items="${payments}">
                                        <option value="${p}" <c:if test="${employe.mode_paiement == p}">selected</c:if>>
                                            ${p}
                                        </option>
                                    </c:forEach>
                                  </select>
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                              <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Image</label>
                                <div class="col-sm-9">
                                  <input type="file" name="image" class="form-control">
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <div class="form-group">
                                <button class="btn btn-primary btn-lg">Valider</button>
                              </div>
                            </div>
                          </div>
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

