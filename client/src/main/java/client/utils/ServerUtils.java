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

package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.GenericType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

/**
 * The ServerUtils class.
 */
public class ServerUtils {

  private String server = "http://localhost:8080/";
  private Player player = new Player("");
  // = new Player("test"); for testing purposes. If we want to test client uncomment.

  /**
   * Sets the server to connect to in later requests.
   *
   * @param server the ip address (and port) to connect to
   * @return true if the server is a game server, false otherwise
   */
  public boolean setServer(String server) {
    if (!(server.startsWith("http://") || server.startsWith("https://"))) {
      server = "http://" + server;
    }
    if (!server.endsWith("/")) {
      server = server + "/";
    }

    Invocation.Builder req = ClientBuilder.newClient(new ClientConfig()) //
        .target(server) //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON);

    try {
      String resp = req.get(new GenericType<>() {
      });

      if (resp.equals("Hello world!")) {
        this.server = server;
        return true;
      }

    } catch (Exception e) {
      return false;
    }

    return false;
  }

  /**
   * Getting quotes the hard way. Don't use this!
   *
   * @throws IOException throws an exception if the connection can't be made
   */
  public void getQuotesTheHardWay() throws IOException {
    var url = new URL(server + "/api/quotes");
    var is = url.openConnection().getInputStream();
    var br = new BufferedReader(new InputStreamReader(is));
    String line;
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }
  }

  /**
   * Gets the quotes from the backend.
   *
   * @return a list of quotes
   */
  public List<Quote> getQuotes() {
    return ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/quotes") //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .get(new GenericType<List<Quote>>() {
        });
  }

  /**
   * Sends a quote to the backend to process.
   *
   * @param quote the quote that should be added
   * @return the quote that was added
   */
  public Quote addQuote(Quote quote) {
    return ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/quotes") //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
  }

  /**
   * Gets the leaderboard entries from backend.
   *
   * @return a list of leaderboard entries.
   */
  public List<LeaderboardEntry> getLeaderboardEntries() {
    return ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/leaderboard") //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .get(new GenericType<List<LeaderboardEntry>>() {
        });
  }

  /**
   * Sends a leaderboard entry to the backend to process.
   *
   * @param leaderboardEntry a leaderboard entry that should be added.
   * @return the leaderboard entry that was added.
   */
  public LeaderboardEntry addLeaderboardEntry(LeaderboardEntry leaderboardEntry) {
    return ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/leaderboard") //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .post(Entity.entity(leaderboardEntry, APPLICATION_JSON), LeaderboardEntry.class);
  }

  /**
   * Returns the player.
   *
   * @return the player
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Sets the player.
   *
   * @param player the player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Gives 0 points to the player if no answer was selected.
   * If true, there is no answer
   *
   * @return returns a boolean to indicate whether the user selected an answer.
   */
  public boolean noAnswer() {
    return this.player.getSelectedAnswer().equals("0");
  }

  /**
   * Resets the chosen answer of the player for the next round.
   */
  public void resetAnswer() {
    this.player.setSelectedAnswer("0");
  }

  /**
   * Requests a question and gives it to the client.
   *
   * @param idx the index of the question
   * @return a question from a poll
   */
  public Question getQuestion(String idx) {

    Question q = ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/game/1/question/" + idx) //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .get(new GenericType<Question>() {
        });
    return q;
  }

}