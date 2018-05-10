package com.alliacom.audit.repository;

import com.alliacom.audit.data.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmailEquals(String email);
    List<Utilisateur> findAllByEmailContains(String email);
}
