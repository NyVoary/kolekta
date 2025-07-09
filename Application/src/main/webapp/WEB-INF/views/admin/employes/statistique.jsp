<%@ include file="../includes/head.jsp" %>
<body>
  <div class="container-scroller">
    <%@ include file="../includes/navbar.jsp" %>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <%@ include file="../includes/sidebar.jsp" %>
      <!-- partial -->
      <div class="main-panel">

            <div class="content-wrapper">
              <div class="row">
                <div class="col-10 mx-auto grid-margin">
                  <div class="card">
                    <div class="card-body"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div>
                        <h4 class="card-title">Statistique Tache</h4>
                        <canvas id="barChart" width="1484" height="700" style="display: block; width: 1484px; height: 700px;" class="chartjs-render-monitor"></canvas>
                      </div>
                  </div>
                </div>
            </div>

      <%@ include file="../includes/footer.jsp" %>
      </div>
    </div>   
  </div>

  <script src="${pageContext.request.contextPath}/js/dataStat.js"></script>

</body>

</html>

