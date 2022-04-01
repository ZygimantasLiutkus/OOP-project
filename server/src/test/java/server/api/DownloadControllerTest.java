package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileInputStream;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Tests for the download controller.
 */
public class DownloadControllerTest {

  private DownloadController sut = new DownloadController();

  /**
   * Tests if the controller is not null.
   */
  @Test
  public void nullTest() {
    assertNotNull(sut);
  }

  /**
   * Tests status code of non-existing resource.
   */
  @Test
  public void nonExistingTest() {
    MockHttpServletRequest request =
        new MockHttpServletRequest("download", "/api/download/not-existing-source");
    ResponseEntity<Resource> resp = sut.download(request);
    assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
  }

  /**
   * Tests the status code of an existing resource.
   */
  @Test
  public void existingTest() {
    MockHttpServletRequest request =
        new MockHttpServletRequest("download", "/api/download/application.properties");
    ResponseEntity<Resource> resp = sut.download(request);
    assertEquals(HttpStatus.OK, resp.getStatusCode());
    Resource actual = resp.getBody();
    assertNotNull(actual);

    try {
      FileInputStream inputStream =
          new FileInputStream("src/main/resources/application.properties");
      InputStreamResource expected = new InputStreamResource(inputStream);
      assertEquals(expected.contentLength(), actual.contentLength());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
