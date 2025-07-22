package com.project.ptmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PtManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(PtManagerApplication.class, args);
  }

}
