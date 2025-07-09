<%@ page import="com.example.demo.entity.Clients" %>
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
        <div class="content-wrapper">
          <h2>Modifier un client</h2>

          <form action="/admin/update-client" method="post" enctype="multipart/form-data" class="forms-sample">
            <input type="hidden" name="idClient" value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getId() : "" %>" />

            <div class="form-group">
              <label for="nom">Nom</label>
              <input type="text" class="form-control" id="nom" name="nom" 
                     value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getNom() : "" %>" required>
            </div>

            <div class="form-group">
              <label for="prenoms">Pr&eacute;noms</label>
              <input type="text" class="form-control" id="prenoms" name="prenoms" 
                     value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getPrenoms() : "" %>" required>
            </div>

            <div class="form-group">
              <label for="dtn">Date de naissance</label>
              <input type="date" class="form-control" id="dtn" name="dtn" 
                     value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getDtn() : "" %>" required>
            </div>

            <div class="form-group">
              <label for="sexe">Sexe</label>
              <select class="form-control" id="sexe" name="sexe">
                  <option value="Masculin" <%= request.getAttribute("client") != null && ((Clients)request.getAttribute("client")).getSexe().equals("Masculin") ? "selected" : "" %>>Masculin</option>
                  <option value="Feminin" <%= request.getAttribute("client") != null && ((Clients)request.getAttribute("client")).getSexe().equals("Feminin") ? "selected" : "" %>>F&eacute;minin</option>
              </select>
            </div>

            <div class="form-group">
              <label for="email">Adresse email</label>
              <input type="email" class="form-control" id="email" name="email" 
                     value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getEmail() : "" %>">
            </div>

            <div class="form-group">
              <label for="telephone">T&eacute;l&eacute;phone</label>
              <input type="text" class="form-control" id="telephone" name="telephone" 
                     value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getTelephone() : "" %>" required>
            </div>

            <div class="form-group">
              <label for="adresse">Adresse</label>
              <input type="text" class="form-control" id="adresse" name="adresse" 
                     value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getAdresse() : "" %>" required>
            </div>

            <!-- <div class="form-group">
              <label for="mdp">Mot de passe</label>
              <input type="password" class="form-control" id="mdp" name="mdp" 
                     value="<%= request.getAttribute("client") != null ? ((Clients)request.getAttribute("client")).getMdp() : "" %>">
            </div>  -->

            <div class="form-group">
              <label>Image actuelle</label><br/>
              <% if (request.getAttribute("client") != null && ((Clients)request.getAttribute("client")).getImage() != null && !((Clients)request.getAttribute("client")).getImage().isEmpty()) { %>
                <img src="/images/uploads/<%= ((Clients)request.getAttribute("client")).getImage() %>" alt="Image" width="80" height="80" class="rounded-circle mb-2">
              <% } %>
            </div>

            <div class="form-group">
              <label>Changer l'image</label>
              <input type="file" name="imageFile" class="file-upload-default" id="imageFile">
              <div class="input-group col-xs-12">
                <input type="text" class="form-control file-upload-info" disabled placeholder="Upload Image">
                <span class="input-group-append">
                  <button class="file-upload-browse btn btn-primary" type="button">Upload</button>
                </span>
              </div>
            </div>

            <button type="submit" class="btn btn-primary mr-2">Mettre &agrave; jour</button>
            <a href="/admin/liste-client" class="btn btn-light">Annuler</a>
          </form>
        </div>
        <!-- partial:partials/_footer.html -->
        <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>
  <script src="/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page -->
  <script src="/vendors/typeahead.js/typeahead.bundle.min.js"></script>
  <script src="/vendors/select2/select2.min.js"></script>
  <!-- End plugin js for this page -->
  <!-- inject:js -->
  <script src="/js/off-canvas.js"></script>
  <script src="/js/hoverable-collapse.js"></script>
  <script src="/js/template.js"></script>
  <script src="/js/settings.js"></script>
  <script src="/js/todolist.js"></script>
  <!-- endinject -->
  <!-- Custom js for this page-->
  <script src="/js/file-upload.js"></script>
  <script src="/js/typeahead.js"></script>
  <script src="/js/select2.js"></script>
  <!-- End custom js for this page-->
</body>
</html>