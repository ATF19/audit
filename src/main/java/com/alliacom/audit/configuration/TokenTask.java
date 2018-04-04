package com.alliacom.audit.configuration;

import com.alliacom.audit.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

/**
 * This class is used to schedule the token expiration date verification task
 */

@Service
@Transactional
public class TokenTask {

    @Autowired
    private TokenRepository tokenRepository;

    @Scheduled(cron = "${token.expiration.verify.cron}")
    public void verifToken() {
        Date now = Date.from(Instant.now());
        tokenRepository.deleteAllByExpireDateLessThan(now);
    }
}
