package api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

  private String appVersion;

  public VersionController(String appVersion) {
    this.appVersion = appVersion;
  }

  @RequestMapping(APISecurity.PUBLIC_EP_PREFIX + "/version")
  public Version version() {
    return new Version(appVersion);
  }
}
