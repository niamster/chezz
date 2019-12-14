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

  @Value("${user_token_store:mem}")
  private String userTokenStore;

  public String getUserTokenStore() {
    return userTokenStore;
  }

  @Value("${user_token_generator:simple}")
  private String userTokenGenerator;

  public String getUserTokenGenerator() {
    return userTokenGenerator;
  }

  public ConfigSecurityProperties getSecurityProperties() {
    return securityProperties;
  }
}
