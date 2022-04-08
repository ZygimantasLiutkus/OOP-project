package server.services;

import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Service which can provide a new Random.
 */
@Configuration
public class RandomService {

  /**
   * Returns a new Random.
   *
   * @return a new Random
   */
  @Bean
  public Random getRandom() {
    return new Random();
  }
}