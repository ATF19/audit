package com.alliacom.audit.repository;

import com.alliacom.audit.data.Clause;
import com.alliacom.audit.data.Exigence;
import com.alliacom.audit.data.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ExigenceRepository extends JpaRepository<Exigence, Long> {
    List<Exigence> findDistinctByClauseInAndResponsablesIn(List<Clause> clause, List<Responsable> responsables);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM exigence_responsable WHERE exigence_id = :exigenceId AND responsable_id = :responsableId", nativeQuery = true)
    void supprimerExigenceResponsableRelation(@Param("exigenceId") Long exigenceId, @Param("responsableId") Long responsableId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM exigence_responsable WHERE responsable_id = :responsableId", nativeQuery = true)
    void supprimerResponsables(@Param("responsableId") Long responsableId);
}
