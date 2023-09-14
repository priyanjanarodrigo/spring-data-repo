package com.myorg.sdc.configuration;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ManagementConfiguration class includes management endpoints related configurations.
 */
@Configuration
public class ManagementConfiguration {

  /**
   * Mandatory HttpExchangeRepository Bean configuration in association with the configuration
   * included in application.yml related to enabling "httpexchanges" management endpoint. (Before
   * Spring Boot version 3, it was "httptrace" and the Bean returned a HttpTraceRepository object)
   *
   * @return HttpExchangeRepository
   */
  @Bean
  public HttpExchangeRepository httpTraceRepository() {
    return new InMemoryHttpExchangeRepository();
  }
}
