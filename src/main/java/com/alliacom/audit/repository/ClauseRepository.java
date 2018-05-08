package com.alliacom.audit.repository;

import com.alliacom.audit.data.Clause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClauseRepository extends JpaRepository<Clause, Long> {
}
