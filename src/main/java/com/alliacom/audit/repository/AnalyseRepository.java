package com.alliacom.audit.repository;

import com.alliacom.audit.data.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyseRepository extends JpaRepository<Analyse, Long> {
}
