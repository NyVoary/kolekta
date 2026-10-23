package com.kolekta.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/** Test unitaire du gestionnaire : on lui donne une exception, on regarde le ProblemDetail renvoyé. */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");

    @Test
    void conflitDeDonneesRetourne409SansMessageSql() {
        var ex = new DataIntegrityViolationException("could not execute statement",
                new RuntimeException("ERROR: duplicate key value violates unique constraint \"clients_email_key\""));

        ResponseEntity<ProblemDetail> reponse = handler.handleConflict(ex, request);

        assertThat(reponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        ProblemDetail corps = reponse.getBody();
        assertThat(corps).isNotNull();
        assertThat(corps.getStatus()).isEqualTo(409);
        assertThat(corps.getInstance()).hasToString("/api/test");
        assertThat(corps.getDetail()).doesNotContain("clients_email_key").doesNotContain("duplicate");
    }

    @Test
    void erreurInterneRetourne500SansDetailInterne() {
        var ex = new IllegalStateException("mot de passe de la base : hunter2");

        ResponseEntity<ProblemDetail> reponse = handler.handleGeneric(ex, request);

        assertThat(reponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(reponse.getBody().getDetail()).isEqualTo("Erreur interne du serveur");
        assertThat(reponse.getBody().toString()).doesNotContain("hunter2");
    }

    @Test
    void ressourceIntrouvableRetourne404() {
        ResponseEntity<ProblemDetail> reponse =
                handler.handleNotFound(new ResourceNotFoundException("Employe", 12L), request);

        assertThat(reponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(reponse.getBody().getDetail()).isEqualTo("Employe introuvable avec l'id : 12");
    }
}
