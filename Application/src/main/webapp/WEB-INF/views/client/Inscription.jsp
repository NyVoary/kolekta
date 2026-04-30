<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Library</title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- Plugin css for this page -->
  <!-- End plugin css for this page -->
  <!-- inject:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />
</head>

<body>
  <div class="container-scroller">
    <div class="container-fluid page-body-wrapper full-page-wrapper">
      <div class="content-wrapper d-flex align-items-center auth px-0">
        <div class="row w-100 mx-0">
          <div class="col-lg-4 mx-auto">
            <div class="auth-form-light text-left py-5 px-4 px-sm-5">
              <div class="brand-logo">
                <a class="navbar-brand brand-logo mr-5" href="/produits_liste"><img src="images/lokaly.png" class="mr-2" alt="logo" /></a>
              </div>
              <h4>Votre lien direct avec le frais local commence ici.</h4>
              <h6 class="font-weight-light">Remplir ces informations pour l'inscription des clients</h6>
              <form class="pt-3" method="post" action="/inscription-client" enctype="multipart/form-data">
                <div class="form-group">
                  <input type="text" class="form-control form-control-lg" name="nom" placeholder="Nom" required>
                </div>
                <div class="form-group">
                  <input type="text" class="form-control form-control-lg" name="prenoms" placeholder="Prenoms" required>
                </div>
                <div class="form-group">
                  <input type="date" class="form-control form-control-lg" name="dtn" placeholder="Date de naissance"
                    required>
                </div>
                <div class="form-group">
                  <select class="form-control form-control-lg" name="sexe" required>
                    <option value="">Sexe</option>
                    <option value="M">Masculin</option>
                    <option value="F">Feminin</option>
                  </select>
                </div>
                <div class="form-group">
                  <input type="email" class="form-control form-control-lg" name="email" placeholder="Email">
                </div>
                <div class="form-group">
                  <input type="tel" class="form-control form-control-lg" name="telephone" placeholder="Telephone"
                    required>
                </div>
                <div class="form-group">
                  <input type="text" class="form-control form-control-lg" name="adresse" placeholder="Adresse" required>
                </div>
                <div class="form-group">
                  <input type="password" class="form-control form-control-lg" name="mdp" placeholder="Mot de passe"
                    required>
                </div>
                <input type="file" name="image" class="file-upload-default">

                <div class="form-group">
                  <input type="file" name="image" class="file-upload-default">
                  <!--<div class="input-group col-xs-12">
                    <input type="text" class="form-control file-upload-info" disabled placeholder="Upload Image">
                    <span class="input-group-append">
                      <button class="file-upload-browse btn btn-primary" type="button">Upload</button>
                    </span>
                  </div>-->
                  <div class="my-2 d-flex justify-content-between align-items-center">
                    <p class="text-danger">
                      <% String erreur=(String) session.getAttribute("error-client"); if (erreur !=null) { %>
                        <%= erreur %>
                          <% session.removeAttribute("error-client"); } %>
                    </p>
                  </div>

                  <div class="mt-3">
                    <button
                      class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">S'inscrire</button>
                  </div>
                  <div class="my-2 d-flex justify-content-between align-items-center">
                    <p>Si vous avez de compte</p>
                  </div>
                  <div class="mb-2">
                    <a href="/login-client" class="btn btn-block btn-facebook auth-form-btn">
                      Se connecter
                    </a>
                  </div>

              </form>
            </div>
          </div>
        </div>
      </div>
      <!-- content-wrapper ends -->
    </div>
    <!-- page-body-wrapper ends -->
  </div>
  <!-- container-scroller -->
  <!-- plugins:js -->
  <script src="${pageContext.request.contextPath}/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page -->
  <!-- End plugin js for this page -->
  <!-- inject:js -->
  <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
  <script src="${pageContext.request.contextPath}/js/hoverable-collapse.js"></script>
  <script src="${pageContext.request.contextPath}/js/template.js"></script>
  <script src="${pageContext.request.contextPath}/js/settings.js"></script>
  <script src="${pageContext.request.contextPath}/js/todolist.js"></script>
  <!-- endinject -->
</body>

</html>