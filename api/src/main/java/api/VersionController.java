package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private String appVersion;

    public VersionController() {
        try (InputStream input = new FileInputStream("src/main/resources/app.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            appVersion = prop.getProperty("version");
        } catch (IOException ex) {
            appVersion = "0.0.0";
        }
    }

    @RequestMapping(APISecurity.PUBLIC_EP_PREFIX + "/version")
    public Version version() {
        return new Version(appVersion);
    }
}