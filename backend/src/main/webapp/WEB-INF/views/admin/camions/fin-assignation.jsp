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

<%@ page import="com.example.demo.view.VCamionEtat" %>
<%
    VCamionEtat camion = (VCamionEtat) request.getAttribute("camion");
    String succes = (String) request.getAttribute("succes-assignation");
    String erreur = (String) request.getAttribute("error-assignation");
%>
<div class="main-panel">
  <h1>Fin de l'assignation</h1>
  <div class="content-wrapper">
    <div class="row">
      <div class="col-12 grid-margin">
        <div class="card">
          <div class="card-body">
            <form class="form-sample" method="post" action="/admin/assignations/finir-assignation">
              <p class="card-description">
                Informations du camion
              </p>
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Immatriculation</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" value="<%= camion.getImmatriculation() %>" readonly />
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Etat du camion</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" value="<%= camion.getEtatCamion() %>" readonly /> 
                    </div>
                  </div>
                </div>
              </div>
              <p class="card-description">
                Informations du chauffeur
              </p>
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Nom</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" value="<%= camion.getChauffeur_nom() %>" readonly />
                    </div>
                  </div>
                </div>
  
              </div>
             
              <p class="card-description">Fin d'assignation</p>
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Date de fin</label>
                    <div class="col-sm-8">
                      <input type="datetime-local" name="arrivee" class="form-control" required />
                    </div>
                  </div>
                </div>
              </div>
              <div class="text-end mt-4">
                <button type="submit" class="btn btn-primary">Finir l'assignation</button>
              </div>
            </form>

            <% if (erreur != null) { %>
              <div class="alert alert-danger mt-2"><%= erreur %></div>
            <% } %>

            <% if (succes != null) { %>
              <div class="alert alert-success mt-2"><%= succes %></div>
            <% } %>
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

