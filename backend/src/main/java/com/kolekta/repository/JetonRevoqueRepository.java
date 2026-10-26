package com.kolekta.repository;

import com.kolekta.entity.JetonRevoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface JetonRevoqueRepository extends JpaRepository<JetonRevoque, String> {

    /** Supprime les jetons dont la date d'expiration est passée ; retourne le nombre de lignes supprimées. */
    @Modifying
    @Query("delete from JetonRevoque j where j.expireLe < :maintenant")
    int supprimerExpiresAvant(Instant maintenant);
}
