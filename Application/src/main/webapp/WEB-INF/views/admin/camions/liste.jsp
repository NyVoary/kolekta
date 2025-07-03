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
        <h1>Liste camion:</h1>
        <div class="content-wrapper">
          <div class="row">
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.view.VCamionEtat" %>
          
<%
    List<VCamionEtat> camions = (List<VCamionEtat>) request.getAttribute("camions");
%>
<div class="col-lg-12 grid-margin stretch-card">
  <div class="card">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Immatriculation</th>
              <th>Capacite (t)</th>
              <th>Nom du chauffeur</th>
              <th>Telephone du chauffeur</th>
              <th>Etat</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <%
              if (camions != null && !camions.isEmpty()) {
                int index = 1;
                for (VCamionEtat camion : camions) {
            %>
              <tr>
                <td><%= index++ %></td>
                <td><%= camion.getImmatriculation() %></td>
                <td><%= camion.getCapacite() %></td>
                <td><%= camion.getChauffeur_nom() %></td>
                <td><%= camion.getChauffeur_telephone() %></td>
                <% if("Disponnible".compareToIgnoreCase(camion.getEtatCamion()) == 0) { %>
                  <td><label class="badge badge-success"><%= camion.getEtatCamion() %></label></td>
                <% } else { %>
                  <td><label class="badge badge-warning"><%= camion.getEtatCamion() %></label></td>
                <% } %>
                <% if("Disponnible".compareToIgnoreCase(camion.getEtatCamion()) == 0) { %>
                  <td></td>
                <% } else { %>
                  <td><a href="/admin/finir-trajet?id=<%= camion.getId_assignation_camion() %>" class="btn btn-outline-info btn-sm ">Fin du trajet <i class="ti-check"></i></a></td>
                <% } %>
              </tr>
            <%
                }
              } else {
            %>
              <tr>
                <td colspan="5" class="text-center text-danger">Aucun resultat</td>
              </tr>
            <%
              }
            %>
          </tbody>
        </table>
      </div>
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

