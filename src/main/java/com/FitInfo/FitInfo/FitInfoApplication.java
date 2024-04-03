package com.FitInfo.FitInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FitInfoApplication {

  public static void main(String[] args) {
    SpringApplication.run(FitInfoApplication.class, args);
  }

}
