package com.alliacom.audit.repository;

import com.alliacom.audit.data.Clause;
import com.alliacom.audit.data.Exigence;
import com.alliacom.audit.data.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExigenceRepository extends JpaRepository<Exigence, Long> {
    List<Exigence> findDistinctByClauseInAndResponsablesIn(List<Clause> clause, List<Responsable> responsables);
}
