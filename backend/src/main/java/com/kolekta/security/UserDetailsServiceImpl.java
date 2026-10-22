package com.kolekta.security;

import com.kolekta.entity.Acces;
import com.kolekta.entity.Clients;
import com.kolekta.repository.AccesRepository;
import com.kolekta.repository.ClientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccesRepository accesRepository;
    private final ClientsRepository clientsRepository;

    // username format: "emp:<nomUtilisateur>" ou "client:<email>"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith("emp:")) {
            String nomUtilisateur = username.substring(4);
            Acces acces = accesRepository.findByNomUtilisateur(nomUtilisateur)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + nomUtilisateur));
            return new CustomUserDetails(
                    acces.getIdAcces(),
                    acces.getNomUtilisateur(),
                    acces.getMotDePasse(),
                    acces.getRole().getLibelle(),
                    "EMPLOYE"
            );
        }
        if (username.startsWith("client:")) {
            String email = username.substring(7);
            Clients client = clientsRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Client introuvable : " + email));
            return new CustomUserDetails(
                    client.getIdClient(),
                    client.getEmail(),
                    client.getMdp(),
                    "client",
                    "CLIENT"
            );
        }
        throw new UsernameNotFoundException("Format de username invalide : " + username);
    }
}
