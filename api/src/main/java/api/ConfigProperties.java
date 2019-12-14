package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ConfigProperties {

  @Autowired private ConfigSecurityProperties securityProperties;

  @Value("${user_store:mem}")
  private String userStore;

  public String getUserStore() {
    return userStore;
  }

  public ConfigSecurityProperties getSecurityProperties() {
    return securityProperties;
  }
}
