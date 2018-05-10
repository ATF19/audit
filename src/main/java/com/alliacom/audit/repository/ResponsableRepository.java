package com.alliacom.audit.repository;

import com.alliacom.audit.data.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
    List<Responsable> findAllByTitreContains(String titre);
}
