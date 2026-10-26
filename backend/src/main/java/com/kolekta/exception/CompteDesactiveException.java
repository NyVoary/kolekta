package com.kolekta.exception;

/**
 * Identifiants corrects mais compte désactivé (employé sorti, client suspendu). Répond 403 et non 401 :
 * la personne est bien identifiée, mais n'a plus le droit d'accéder au système.
 */
public class CompteDesactiveException extends RuntimeException {
    public CompteDesactiveException(String message) {
        super(message);
    }
}
