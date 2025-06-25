<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Skydash Admin</title>
  
<%@ include file="../inc/css.jsp" %>


</head>

<body>
  <div class="container-scroller">
    <div class="container-fluid page-body-wrapper full-page-wrapper">
      <div class="content-wrapper d-flex align-items-center auth px-0">
        <div class="row w-100 mx-0">
          <div class="col-lg-4 mx-auto">
            <div class="auth-form-light text-left py-5 px-4 px-sm-5">
              <div class="brand-logo">
                <img src="${pageContext.request.contextPath}/images/logo.svg" alt="logo">
              </div>
              <h4>Bienvenue dans le Nom du site</h4>
              <h6 class="font-weight-light">Si vous avez un compte entrer les informations</h6>
              <form class="pt-3" method="post" action="/login-client">
                <div class="form-group">
                  <input type="email" class="form-control form-control-lg" name="email" id="exampleInputEmail1" placeholder="email">
                </div>
                <div class="form-group">
                  <input type="password" class="form-control form-control-lg" name="mdp" id="exampleInputPassword1" placeholder="mot de passe">
                </div>
                <div class="mt-3">
                  <button class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn" >Connection</button>
                </div>
                  <%
                    String erreur = (String) session.getAttribute("error-client");
                    if (erreur != null) {
                  %>
                    <div class="alert alert-danger mt-2"><%= erreur %></div>
                  <%
                      session.removeAttribute("error-client");
                    }
                  %>
                <div class="my-2 d-flex justify-content-between align-items-center">
                  <p>Si vous n'avez pas de compte</p>
                </div>
                <div class="mb-2">
                  <a href="/inscription-client" class="btn btn-block btn-facebook auth-form-btn">
                    <i class="ti-plus mr-2"></i>Creer un compte
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

  <%@ include file="../inc/js.jsp" %>

</body>

</html>
