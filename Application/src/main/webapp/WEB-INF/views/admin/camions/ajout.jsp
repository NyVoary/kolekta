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
        <h1>Ajout camion:</h1>
        <div class="content-wrapper">
          <div class="row">
            <div class="col-12 grid-margin">
              <div class="card">
                <div class="card-body">
                  <!-- <h4 class="card-title">Ajout de Camion</h4> -->
                  <form class="form-sample" method="post" action="/admin/ajout-camion">
                    <p class="card-description">
                      Informations du camion
                    </p>
                    <div class="row">
                      <div class="col-md-6">
                        <div class="form-group row">
                          <label class="col-sm-4 col-form-label">Immatriculation</label>
                          <div class="col-sm-8">
                            <input type="text" name="immatriculation" class="form-control" placeholder="Ex: ABC-1234" required />
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="form-group row">
                          <label class="col-sm-4 col-form-label">Capacite (en tonnes)</label>
                          <div class="col-sm-8">
                            <input type="number" name="capacite" class="form-control" placeholder="Ex: 15" min="0" required />
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
                          <label class="col-sm-4 col-form-label">Nom du chauffeur</label>
                          <div class="col-sm-8">
                            <input type="text" name="chauffeur_nom" class="form-control" placeholder="Ex: Jean Rakoto" required />
                          </div>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="form-group row">
                          <label class="col-sm-4 col-form-label">Telephone</label>
                          <div class="col-sm-8">
                            <input type="tel" name="chauffeur_telephone" class="form-control" placeholder="Ex: 0321234567" required />
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="text-end mt-4">
                      <button type="submit" class="btn btn-primary">Ajouter le camion</button>
                    </div>
                  </form>
                  <%
                    String erreur = (String) session.getAttribute("error-camion");
                    if (erreur != null) {
                  %>
                    <div class="alert alert-danger mt-2"><%= erreur %></div>
                  <%
                      session.removeAttribute("error-camion");
                    }
                  %>

                  <%
                    String succes = (String) session.getAttribute("succes-camion");
                    if (succes != null) {
                  %>
                    <div class="alert alert-success mt-2"><%= succes %></div>
                  <%
                      session.removeAttribute("succes-camion");
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

