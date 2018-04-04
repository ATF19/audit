package com.alliacom.audit.repository;

import com.alliacom.audit.data.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TokenRepository extends JpaRepository<Token, Long> {
    void deleteAllByExpireDateLessThan(Date now);
    boolean existsByTokenEquals(String token);
}
