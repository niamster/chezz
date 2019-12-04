package api;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  private final Logger logger = LogManager.getLogger(UserStoreMem.class);

  @Autowired private ConfigProperties config;

  @Bean
  public UserStore userStore() {
    return new UserStoreMem();
  }

  @Bean
  public KeyWhitener keyWhitener() {
    String keyWhitener = config.getSecurityProperties().getKeyWhitener();
    logger.info("Using '{}' as key whitener", keyWhitener);
    switch (keyWhitener) {
      case "bcrypt":
        return new KeyWhitenerBCrypt();
      case "argon2":
        return new KeyWhitenerArgon2();
      default:
        throw new ServiceConfigurationError(
            String.format("Unknown key whitener '%s'", keyWhitener));
    }
  }

  @Bean
  public UserTokenStore userTokenStore() {
    return new UserTokenStoreMem();
  }

  @Bean
  public UserTokenGenerator userTokenGenerator() {
    return new UserTokenGeneratorSimple();
  }

  // XXX: maybe this is not the best place to init User Manager
  @Bean
  public UserManager userManager(UserStore userStore, KeyWhitener keyWhitener) {
    return new UserManager(userStore, keyWhitener);
  }
}
