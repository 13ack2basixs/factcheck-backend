package com.hongwei.factcheck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Enable auditing -> filling up 'who' and 'when' when entities are created/modified
@Configuration
@EnableJpaAuditing
public class JpaConfig {}
