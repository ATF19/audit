package com.alliacom.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
public class AuditApplication extends SpringBootServletInitializer {



	public static void main(String[] args) {

		SpringApplication.run(AuditApplication.class, args);
	}
}
