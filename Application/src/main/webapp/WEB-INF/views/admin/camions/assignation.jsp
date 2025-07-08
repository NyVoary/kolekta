<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.view.VCamionEtat" %>
<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <div id="right-sidebar" class="settings-panel">
        <i class="settings-close ti-close"></i>
        <ul class="nav nav-tabs border-top" id="setting-panel" role="tablist">
          <li class="nav-item">
            <a class="nav-link active" id="todo-tab" data-toggle="tab" href="#todo-section" role="tab" aria-controls="todo-section" aria-expanded="true">TO DO LIST</a>
          </li>
        </ul>
        <div class="tab-content" id="setting-content">
          <div class="tab-pane fade show active scroll-wrapper" id="todo-section" role="tabpanel" aria-labelledby="todo-section">
            <div class="add-items d-flex px-3 mb-0">
              <form class="form w-100">
                <div class="form-group d-flex">
                  <input type="text" class="form-control todo-list-input" placeholder="Add To-do">
                  <button type="submit" class="add btn btn-primary todo-list-add-btn" id="add-task">Add</button>
                </div>
              </form>
            </div>
            <div class="list-wrapper px-3">
              <ul class="d-flex flex-column-reverse todo-list">
                <li>
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox">
                      Team review meeting at 3.00 PM
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li>
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox">
                      Prepare for presentation
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li>
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox">
                      Resolve all the low priority tickets due today
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li class="completed">
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox" checked>
                      Schedule meeting for next week
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
                <li class="completed">
                  <div class="form-check">
                    <label class="form-check-label">
                      <input class="checkbox" type="checkbox" checked>
                      Project review
                    </label>
                  </div>
                  <i class="remove ti-close"></i>
                </li>
              </ul>
            </div>
          </div>
          <!-- To do section tab ends -->
        </div>
      </div>
      <%@ include file="../includes/sidebar.jsp" %>
      <!-- partial -->
      <div class="main-panel">
        <h1>Assignation camion:</h1>
        <div class="content-wrapper">
          <div class="row">
            <%
                List<VCamionEtat> camions = (List<VCamionEtat>) request.getAttribute("camions");
            %>

            <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title">Nouvelle assignation de camion</h4>
                  <form method="post" action="/admin/assignations/ajout">
                    <div class="row">
                      <div class="form-group col-md-6">
                        <label>Camion</label>
                        <select name="idCamion" class="form-control" required>
                          <option value="">-- Choisir un camion --</option>
                          <% for(VCamionEtat c : camions) { %>
                            <option value="<%= c.getId() %>">
                              <%= c.getImmatriculation() %> - <%= c.getChauffeur_nom() %>
                            </option>
                          <% } %>
                        </select>
                      </div>
                        
                    <div class="form-group col-md-6">
                      <label>Type d'assignation</label>
                      <select name="type" class="form-control" required>
                        <option value="">-- Choisir un type --</option>
                        <option value="Collecte">Collecte</option>
                        <option value="Livraison">Livraison</option>
                      </select>
                    </div>
                    </div>
                    <div class="row">
                         <div class="form-group col-md-6">
                      <label>Date de depart</label>
                      <input type="datetime-local" name="depart" class="form-control" required />
                    </div>

                    <div class="form-group col-md-6">
                      <label>Date d'arrivee (facultatif)</label>
                      <input type="datetime-local" name="arrivee" class="form-control" />
                    </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                  </form>
                  <%
                    String erreur = (String) session.getAttribute("error-assignation");
                    if (erreur != null) {
                  %>
                    <div class="alert alert-danger mt-2"><%= erreur %></div>
                  <%
                      session.removeAttribute("error-assignation");
                    }
                  %>

                  <%
                    String succes = (String) session.getAttribute("succes-assignation");
                    if (succes != null) {
                  %>
                    <div class="alert alert-success mt-2"><%= succes %></div>
                  <%
                      session.removeAttribute("succes-assignation");
                    }
                  %>
                </div>
              </div>
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

