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

package server.api;

import commons.Quote;
import java.util.List;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.QuoteRepository;

/**
 * The Quote controller.
 */
@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

  private final Random random;
  private final QuoteRepository repo;

  /**
   * Initializes the QuoteController with the given / injected Random and QuoteRepository.
   *
   * @param random the Random to use
   * @param repo   the QuoteRepository to use
   */
  public QuoteController(Random random, QuoteRepository repo) {
    this.random = random;
    this.repo = repo;
  }

  /**
   * Returns if the supplied string is null or empty.
   *
   * @param s the string to check
   * @return true iff the string is null or empty
   */
  private static boolean isNullOrEmpty(String s) {
    return s == null || s.isEmpty();
  }

  /**
   * Returns all quotes from the repository.
   *
   * @return a list of all the quotes from the repository
   */
  @GetMapping(path = {"", "/"})
  public List<Quote> getAll() {
    return repo.findAll();
  }

  /**
   * Returns the ResponseEntity of the quote of the get request to /api/quotes/{id}.
   *
   * @param id the id of the quote
   * @return the ResponseEntity of the quote that was asked for
   */
  @GetMapping("/{id}")
  public ResponseEntity<Quote> getById(@PathVariable("id") long id) {
    if (id < 0 || !repo.existsById(id)) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(repo.getById(id));
  }

  /**
   * Returns the ResponseEntity of the added quote of the post request to /api/quotes.
   *
   * @param quote the quote that needs to be added
   * @return the ResponseEntity of the added quote
   */
  @PostMapping(path = {"", "/"})
  public ResponseEntity<Quote> add(@RequestBody Quote quote) {

    if (quote.person == null || isNullOrEmpty(quote.person.firstName)
        || isNullOrEmpty(quote.person.lastName)
        || isNullOrEmpty(quote.quote)) {
      return ResponseEntity.badRequest().build();
    }

    Quote saved = repo.save(quote);
    return ResponseEntity.ok(saved);
  }

  /**
   * Returns a random quote from the repository.
   *
   * @return a random quote wrapped in a ResponseEntity
   */
  @GetMapping("rnd")
  public ResponseEntity<Quote> getRandom() {
    var idx = random.nextInt((int) repo.count());
    return ResponseEntity.ok(repo.getById((long) idx));
  }
}