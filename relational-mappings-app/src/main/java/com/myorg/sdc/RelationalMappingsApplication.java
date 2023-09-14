package com.myorg.sdc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RelationalMappingsApplication is the main class which includes the main method through which this
 * Spring Boot application is started.
 */
@Slf4j
@SpringBootApplication
public class RelationalMappingsApplication {

  /**
   * Java main method.
   *
   * @param args String array arguments
   */
  public static void main(String[] args) {
    log.debug("RelationalMappingsApplication main method executed. Starting the application");
    SpringApplication.run(RelationalMappingsApplication.class, args);
  }
}
