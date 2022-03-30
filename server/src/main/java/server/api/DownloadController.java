package server.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for downloading images.
 */
@RestController
@RequestMapping("/api/download")
public class DownloadController {

  /**
   * Sends the resource that was asked for.
   *
   * @param request the request that was sent
   * @return the resource that was asked for
   */
  @GetMapping("**")
  public ResponseEntity<Resource> download(HttpServletRequest request) {
    final String serverPath = "src/main/resources/";

    String fileName = request.getRequestURI().split("/api/download/")[1];
    String filePath = serverPath + fileName;
    try {
      FileInputStream inputStream = new FileInputStream(filePath);
      InputStreamResource resource = new InputStreamResource(inputStream);

      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(resource);
    } catch (FileNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }
}
