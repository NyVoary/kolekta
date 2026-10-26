package com.kolekta.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Toutes les erreurs de l'API sont renvoyées au format RFC 9457 (ProblemDetail, application/problem+json) :
 * type, title, status, detail, instance. ResponseEntityExceptionHandler traite déjà les erreurs standard de
 * Spring MVC (JSON illisible, route inconnue, méthode non supportée...) ; on ajoute nos exceptions métier.
 *
 * Règle : le client ne reçoit jamais de message interne ni de trace de pile ; la trace va dans les logs.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    /** Identifiants de connexion invalides ou compte désactivé (levée par AuthService). */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorized(UnauthorizedException ex, HttpServletRequest req) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), req);
    }

    /** Compte désactivé : 403 avec un code stable que le frontend utilise pour choisir son message. */
    @ExceptionHandler(CompteDesactiveException.class)
    public ResponseEntity<ProblemDetail> handleCompteDesactive(CompteDesactiveException ex, HttpServletRequest req) {
        ResponseEntity<ProblemDetail> reponse = build(HttpStatus.FORBIDDEN, ex.getMessage(), req);
        reponse.getBody().setProperty("code", "COMPTE_DESACTIVE");
        return reponse;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleForbidden(AccessDeniedException ex, HttpServletRequest req) {
        return build(HttpStatus.FORBIDDEN, "Accès refusé", req);
    }

    /** Contrainte de la base violée (doublon, clé étrangère...) : le message SQL n'est jamais renvoyé. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleConflict(DataIntegrityViolationException ex, HttpServletRequest req) {
        log.warn("Conflit de données sur {} : {}", req.getRequestURI(), ex.getMostSpecificCause().getMessage());
        return build(HttpStatus.CONFLICT, "Cette opération entre en conflit avec des données existantes", req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Erreur interne sur {}", req.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne du serveur", req);
    }

    /** 400 de validation : ajoute la liste des champs fautifs dans la propriété « errors ». */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(GlobalExceptionHandler::toError)
                .toList();
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Les données envoyées sont invalides");
        problem.setProperty("errors", errors);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private static Map<String, String> toError(FieldError error) {
        return Map.of("champ", error.getField(),
                "message", error.getDefaultMessage() != null ? error.getDefaultMessage() : "invalide");
    }

    private static ResponseEntity<ProblemDetail> build(HttpStatus status, String detail, HttpServletRequest req) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setInstance(URI.create(req.getRequestURI()));
        return ResponseEntity.status(status).body(problem);
    }
}
