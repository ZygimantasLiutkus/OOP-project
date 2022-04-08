package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A controller that returns "This is a working game server" when entering the / path.
 */
@Controller
@RequestMapping("/")
public class ServerController {

  /**
   * The / path of the server.
   *
   * @return "This is a working game server"
   */
  @GetMapping("/")
  @ResponseBody
  public String index() {
    return "This is a working game server";
  }
}