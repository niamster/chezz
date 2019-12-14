package api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("security")
public class ConfigSecurityProperties {

  @Value("${key_whitener:bcrypt}")
  private String keyWhitener;

  public String getKeyWhitener() {
    return keyWhitener;
  }
}
