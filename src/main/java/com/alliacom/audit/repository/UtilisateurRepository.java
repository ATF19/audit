package com.alliacom.audit.repository;

import com.alliacom.audit.data.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    public Utilisateur findByEmailEquals(String email);
}
