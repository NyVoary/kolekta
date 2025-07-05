package com.example.demo.repository;

import com.example.demo.entity.*;
// import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AchatRepository extends JpaRepository<Achat, Long> {
    List<Achat> findByClient(Client client);
    @Query("select * from (select A.id_commande,A.id_produit,cd.id_status from (select cp.id_commande,cp.id_produit from Commandes c join Commande_Produits cp on c.id_commande = cp.id_commande where c.id_client = ?) as A join Commande_Status cs on A.id_commmande = cs.id_commande) as B where B.id_status = ''Livrée';")
    default
    List<Achat> findHistoriqueAchat() {
        return null;
    }
}

