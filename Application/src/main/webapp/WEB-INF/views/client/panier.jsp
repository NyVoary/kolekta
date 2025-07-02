<jsp:useBean id="acces" class="com.example.demo.entity.Produits" scope="session" />
<%@ include file="includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial -->
      <div class="main-panel">
        <p>Panier</p>
        <!-- partial:partials/_footer.jsp -->
      <%@ include file="includes/footer.jsp" %>
      </div>
    </div>   
  </div>
</body>

</html>

