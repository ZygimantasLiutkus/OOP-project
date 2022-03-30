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

import commons.GameEntity;
import commons.LeaderboardEntry;
import commons.Message;
import commons.Player;
import commons.Question;
import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.image.Image;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * The ServerUtils class.
 */
public class ServerUtils {

  // = new Player("test"); for testing purposes. If we want to test client uncomment.
  public StompSession session;
  private String server = "http://localhost:8080/";
  private Player player = new Player("");
  private Player dummyPlayer = new Player("");

  /**
   * Connect method that sets up a connection with a websocket.
   *
   * @throws RuntimeException      if setting up the connection went wrong
   * @throws IllegalStateException if the stomp session got into a wrong state
   */
  public void connect() {
    String url = server.replaceAll("^(http|https)://", "ws://") + "/websocket";
    System.out.println(url);
    var client = new StandardWebSocketClient();
    var stomp = new WebSocketStompClient(client);
    stomp.setMessageConverter(new MappingJackson2MessageConverter());
    try {
      this.session = stomp.connect(url, new StompSessionHandlerAdapter() {
      }).get();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

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
   * Gets the global leaderboard entries from backend.
   *
   * @return a list of leaderboard entries.
   */
  public List<LeaderboardEntry> getGlobalLeaderboard() {
    return ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/leaderboard") //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .get(new GenericType<List<LeaderboardEntry>>() {
        });
  }

  /**
   * Gets the multiplayer leaderboard entries from backend.
   *
   * @return a list of leaderboard entries.
   */
  public List<LeaderboardEntry> getMultiplayerLeaderboard() {
    long id = player.getGameId();
    return ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/game/" + id + "/leaderboard") //
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
        .target(server).path("api/game/" + player.getGameId() + "/question/" + idx) //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .get(new GenericType<Question>() {
        });
    return q;
  }

  /**
   * Returns a game specified by the player's id.
   *
   * @return a created game
   */
  public GameEntity getGame() {
    return ClientBuilder.newClient(new ClientConfig()) //
        .target(server).path("api/game/" + player.getGameId()) //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .get(new GenericType<>() {
        });
  }

  /**
   * Gets the type of the game and gives it to the client.
   *
   * @return type of a game
   */
  public GameEntity.Type getType() {
    Long id = player.getGameId();
    return ClientBuilder.newClient(new ClientConfig())  //
        .target(server).path("api/game/" + id) //
        .request(APPLICATION_JSON) //
        .accept(APPLICATION_JSON) //
        .get(new GenericType<GameEntity>() {
        }).getType();
  }

  /**
   * Method to add a player to the waiting room (single player).
   *
   * @return the data of the respective player
   */
  public Player addSingleplayer() {
    Response response = ClientBuilder.newClient(new ClientConfig())
        .target(server).path("api/game/singleplayer")
        .request()
        .post(Entity.json(getDummyPlayer()));
    Player p = response.readEntity(GameEntity.class).getPlayers().get(0);
    setPlayer(p);
    return p;
  }

  /**
   * Method to change the status of the game.
   *
   * @param status a dummy game only with status
   */
  public void changeStatus(GameEntity status) {
    ClientBuilder.newClient(new ClientConfig())
        .target(server).path("api/game/" + player.getGameId())
        .request()
        .put(Entity.json(status));
  }

  /**
   * Setter for the dummy player that only gets a name.
   *
   * @param player the newly created player
   */
  public void setDummy(Player player) {
    this.dummyPlayer = player;
  }

  /**
   * Getter for the dummy player.
   *
   * @return a player that only contains a name
   */
  public Player getDummyPlayer() {
    return dummyPlayer;
  }

  /**
   * Method to add a player to a multiplayer game.
   *
   * @return the added player.
   */
  public Player addPlayer() {
    Response response = ClientBuilder.newClient(new ClientConfig())
        .target(server).path("api/game/addPlayer")
        .request()
        .post(Entity.json(getDummyPlayer()));
    if (response.getStatus() == 409) { // HTTP status 409 is CONFLICT
      return null;
    }
    List<Player> players = response.readEntity(GameEntity.class).getPlayers();
    for (Player p : players) {
      if (p.getName().equals(dummyPlayer.getName())) {
        setPlayer(p);
        return p;
      }
    }
    //Returning null because it's impossible for a name
    // set in the client to be nonexistent inside a lobby.
    return null;
  }

  /**
   * Method to set up a player to receive messages in game.
   *
   * @param dest     path for where to get messages from
   * @param consumer the player
   */
  public void registerForMessages(String dest, Consumer<Message> consumer) {
    session.subscribe(dest, new StompFrameHandler() {

      /**
       * Automatically generated getter for the payload type.
       *
       * @param headers the headers
       * @return the payload type
       */
      @Override
      public Type getPayloadType(StompHeaders headers) {
        return Message.class;
      }

      /**
       * Automatically generated method for frame handling.
       *
       * @param headers the headers
       * @param payload the content of the message
       */
      @Override
      public void handleFrame(StompHeaders headers, Object payload) {
        consumer.accept((Message) payload);
      }
    });
  }

  /**
   * Method to send a message.
   *
   * @param dest the path to send the message to
   * @param text the text to be sent
   */
  public void send(String dest, String text) {
    Message message = new Message(text, player.getName());
    session.send(dest + "/" + player.getGameId(), message);
  }

  /**
   * Update the list of players of a game.
   *
   * @param players the list of players
   */
  public void updatePlayer(List<Player> players) {
    ClientBuilder.newClient(new ClientConfig())
        .target(server).path("api/game/" + getGame().getId() + "/updatePlayer")
        .request()
        .put(Entity.json(players));
  }

  /**
   * Gets an image from the backend.
   *
   * @param path the path to the image
   * @return the specified image or a default image if the image doesn't exist
   */
  public javafx.scene.image.Image getImage(String path) {
    Image img;
    try {
      img = new javafx.scene.image.Image(
          server + "api/download/images/" + path);
      if (img.isError()) {
        throw new FileNotFoundException();
      }
    } catch (Exception e) {
      img = new javafx.scene.image.Image("client/images/defaultImage.png");
    }
    return img;
  }
}
