package com.scaleup.userdetailsservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.scaleup.userdetailsservice.db.repository")
@ComponentScan("com.scaleup.userdetailsservice")
public class AppConfig {

}