package com.alliacom.audit.repository;

import com.alliacom.audit.data.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    @Query(value = "SELECT * FROM questionnaire q WHERE q.responsable_id = :responsable_id", nativeQuery = true)
    List<Questionnaire> allByResponsable(@Param("responsable_id") Long responsable_id);
}
