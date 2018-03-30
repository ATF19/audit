package com.alliacom.audit.repository;

import com.alliacom.audit.data.Norme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NormeRepository extends JpaRepository<Norme, Long> {
}
