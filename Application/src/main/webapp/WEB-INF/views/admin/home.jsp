<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.Map, java.util.HashMap" %>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.demo.entity.AssignationTache" %>
<%@ page import="com.example.demo.entity.Acces" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="acces" class="com.example.demo.entity.Acces" scope="session" />

<%@ include file="includes/head.jsp" %>
<link rel="stylesheet" href="/css/barChart.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/lo.png" />

<body>
  <div class="container-scroller">
    <%@ include file="includes/navbar.jsp" %>
    <div class="container-fluid page-body-wrapper">
      <%@ include file="includes/sidebar.jsp" %>
      <div class="main-panel">
        <div class="content-wrapper">

          <div class="row justify-content-center align-items-center" style="min-height:60vh;">
            <div class="col-md-8 text-center">
              <h1 class="lead mb-4">Bonjour, <strong>${acces.nomUtilisateur}</strong> (rôle : <strong>${acces.role}</strong>)</h1>
              <div class="row justify-content-center">
                <div class="col-md-4 mb-3"><a href="/admin/liste-client" class="btn btn-outline-primary btn-lg w-100">Gérer les clients</a></div>
                <div class="col-md-4 mb-3"><a href="/admin/fournisseurs/liste" class="btn btn-outline-success btn-lg w-100">Gérer les fournisseurs</a></div>
                <div class="col-md-4 mb-3"><a href="/admin/liste-produit" class="btn btn-outline-info btn-lg w-100">Gérer les produits</a></div>
              </div>
              <div class="row justify-content-center">
                <div class="col-md-4 mb-3"><a href="/admin/liste-employe" class="btn btn-outline-warning btn-lg w-100">Gérer les employés</a></div>
                <div class="col-md-4 mb-3"><a href="/admin/liste-camion" class="btn btn-outline-secondary btn-lg w-100">Gérer les camions</a></div>
                <div class="col-md-4 mb-3"><a href="/admin/commande" class="btn btn-outline-dark btn-lg w-100">Commandes & Livraisons</a></div>
              </div>
            </div>
          </div>

          <!-- Récupérer la map des statuts modifiés en session -->
          <%
            Map<Integer, String> statutsModifies = (Map<Integer, String>) session.getAttribute("statutsModifies");
            if (statutsModifies == null) {
              statutsModifies = new HashMap<>();
              session.setAttribute("statutsModifies", statutsModifies);
            }
          %>

          <!-- Tableau des tâches -->
          <div class="col-md-12 grid-margin stretch-card">
            <div class="card">
              <div class="card-body">
                <p class="card-title mb-0">Tâches à faire pour : <strong>${acces.nomUtilisateur}</strong> / <%= LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></p>
                <div class="table-responsive">
                  <table class="table table-striped table-borderless">
                    <thead>
                      <tr>
                        <th>Tâche</th>
                        <th>Durée</th>
                        <th>Date</th>
                        <th>Importance</th>
                        <th>Statut</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                      <%
                        List<AssignationTache> taches = (List<AssignationTache>) request.getAttribute("taches");
                        if (taches != null && !taches.isEmpty()) {
                          for (AssignationTache t : taches) {
                            String priorite = t.getPriorite() != null ? t.getPriorite().name() : "";
                            String statusOriginal = t.getStatus() != null ? t.getStatus() : "";
                            // Utilise statut modifié si existe en session
                            String status = statutsModifies.containsKey(t.getId_tache()) ? statutsModifies.get(t.getId_tache()) : statusOriginal;

                            String badgePriorite = "badge-light";
                            switch (priorite.toLowerCase()) {
                              case "urgente": badgePriorite = "badge-danger"; break;
                              case "haute": badgePriorite = "badge-warning"; break;
                              case "moyenne": badgePriorite = "badge-info"; break;
                              case "basse": badgePriorite = "badge-secondary"; break;
                            }
                            String badgeStatus = "badge-secondary";
                            switch (status.toLowerCase()) {
                              case "en cours": badgeStatus = "badge-primary"; break;
                              case "en attente": badgeStatus = "badge-light"; break;
                              case "terminée":
                              case "termine":
                              case "terminee": badgeStatus = "badge-success"; break;
                              case "annulée":
                              case "annulee":
                              case "annule": badgeStatus = "badge-dark"; break;
                            }
                      %>
                        <tr>
                          <td><strong><%= t.getTitre() %></strong><br><small class="text-muted"><%= t.getDescription() != null ? t.getDescription() : "" %></small></td>
                          <td><%= t.getDate_debut().toLocalTime() %> - <%= t.getDate_fin().toLocalTime() %></td>
                          <td><%= t.getDate_debut().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></td>
                          <td><span class="badge <%= badgePriorite %>"><%= priorite %></span></td>
                          <td><span class="badge <%= badgeStatus %>"><%= status %></span></td>
                          <td>
                            <%-- <button class="btn btn-sm btn-outline-primary" data-toggle="modal" data-target="#statusModal"
                              data-tache-id="<%= t.getId_tache() %>" data-current-status="<%= status %>">Modifier</button> --%>
                          </td>
                        </tr>
                      <%
                          }
                        } else {
                      %>
                      <tr>
                        <td colspan="6" class="text-center text-muted">Aucune tâche prévue pour aujourd'hui</td>
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

          <!-- Modal modification statut -->
          <div class="modal fade" id="statusModal" tabindex="-1" role="dialog" aria-labelledby="statusModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="statusModalLabel">Modifier le statut</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Fermer">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <input type="hidden" id="tacheId" />
                  <input type="hidden" id="ancienStatusId" />
                  <input type="hidden" id="modifieParId" value="${acces.employes}" />
                  <label for="nouveauStatutId">Nouveau statut</label>
                  <select id="nouveauStatutId" class="form-control" required>
                    <c:forEach var="s" items="${assignationTacheStatuses}">
                      <option value="${s}">${s}</option>
                    </c:forEach>
                  </select>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                  <button id="btnSaveStatus" type="button" class="btn btn-primary">Enregistrer</button>
                </div>
              </div>
            </div>
          </div>

        </div>
        <%@ include file="includes/footer.jsp" %>
      </div>
    </div>
  </div>

  <!-- Inclusion des scripts nécessaires -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>

  <script>
    $('#statusModal').on('show.bs.modal', function (event) {
      var button = $(event.relatedTarget);
      var tacheId = button.data('tache-id');
      var currentStatus = button.data('current-status');

      $('#tacheId').val(tacheId);
      $('#ancienStatusId').val(currentStatus);
      $('#nouveauStatutId').val(currentStatus);
    });

    $('#btnSaveStatus').click(function() {
      var tacheId = $('#tacheId').val();
      var nouveauStatut = $('#nouveauStatutId').val();

      $.ajax({
        url: '/changer-statut-session', // servlet à créer
        method: 'POST',
        data: { tacheId: tacheId, nouveauStatut: nouveauStatut },
        success: function() {
          $('#statusModal').modal('hide');

          var row = $('button[data-tache-id="'+ tacheId +'"]').closest('tr');
          var badge = row.find('td:nth-child(5) > span.badge');
          badge.text(nouveauStatut);

          badge.removeClass('badge-secondary badge-primary badge-light badge-success badge-dark');
          switch (nouveauStatut.toLowerCase()) {
            case 'en cours': badge.addClass('badge-primary'); break;
            case 'en attente': badge.addClass('badge-light'); break;
            case 'terminée':
            case 'terminee': badge.addClass('badge-success'); break;
            case 'annulée':
            case 'annulee': badge.addClass('badge-dark'); break;
            default: badge.addClass('badge-secondary'); break;
          }

          $('button[data-tache-id="'+ tacheId +'"]')
            .data('current-status', nouveauStatut)
            .attr('data-current-status', nouveauStatut);
        },
        error: function() {
        }
      });
    });
  </script>
</body>
</html>
