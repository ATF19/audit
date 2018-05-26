package com.alliacom.audit.repository;

import com.alliacom.audit.data.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnalyseRepository extends JpaRepository<Analyse, Long> {
    @Query(
            value = "SELECT a.id, a.created_at, a.rapport, a.updated_at, a.norme_id, a.utilisateur_id FROM analyse a INNER JOIN utilisateur u ON u.id = a.utilisateur_id AND u.email = :email",
            nativeQuery = true
    )
    List<Analyse> analysesByEmail(@Param("email") String email);
    
    @Query(
            value = "SELECT a.id, a.created_at, a.rapport, a.updated_at, a.norme_id, a.utilisateur_id FROM analyse a where a.utilisateur_id = :userId",
            nativeQuery = true
    )

    List<Analyse> analysesByUserId(@Param("userId") Long userId);
}
