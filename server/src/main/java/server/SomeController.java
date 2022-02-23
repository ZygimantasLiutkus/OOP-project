package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * An example controller that returns "Hello world" when entering the / path.
 */
@Controller
@RequestMapping("/")
public class SomeController {

  /**
   * The / path of the server.
   *
   * @return "Hello world!"
   */
  @GetMapping("/")
  @ResponseBody
  public String index() {
    return "Hello world!";
  }
}