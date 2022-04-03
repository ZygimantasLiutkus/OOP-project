/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import server.services.ActivityService;

/**
 * The main class of the server application.
 */
@SpringBootApplication
@EntityScan(basePackages = {"commons", "server"})
public class Main {

  /**
   * The start point of the server application.
   *
   * @param args the command line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  /**
   * Imports activities from json file.
   *
   * @param activityService service for adding activities.
   * @return returns the added activities.
   */
  @Bean
  CommandLineRunner runner(ActivityService activityService) {
    return args -> {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      TypeReference<List<Activity>> typeReference = new TypeReference<List<Activity>>() {
      };

      InputStream inputStream =
          Activity.class.getClassLoader()
              .getResourceAsStream("Activities.json");
      try {
        List<Activity> activityList = mapper.readValue(inputStream, typeReference);
        activityService.save(activityList);
        System.out.println("Activities added");
      } catch (IOException e) {
        System.out.println("Unable to save activities" + e.getMessage());
      }
    };
  }
}