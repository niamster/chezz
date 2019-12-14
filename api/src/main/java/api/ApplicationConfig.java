package api;

import chezz.datastore.UserStore;
import chezz.datastore.UserStoreMem;
import chezz.datastore.UserStoreSelector;
import chezz.datastore.UserTokenStore;
import chezz.datastore.UserTokenStoreSelector;
import chezz.users.KeyWhitener;
import chezz.users.KeyWhitenerSelector;
import chezz.users.UserManager;
import chezz.users.UserTokenGenerator;
import chezz.users.UserTokenGeneratorSelector;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
  public String appVersion() {
    try (InputStream input = new FileInputStream("src/main/resources/app.properties")) {
      Properties prop = new Properties();
      prop.load(input);
      return prop.getProperty("version");
    } catch (IOException ex) {
      return "0.0.0";
    }
  }

  @Bean
  public UserStore userStore() {
    String userStore = config.getUserStore();
    logger.info("Using '{}' as user store", userStore);
    return new UserStoreSelector().getByName(userStore);
  }

  @Bean
  public KeyWhitener keyWhitener() {
    String keyWhitener = config.getSecurityProperties().getKeyWhitener();
    logger.info("Using '{}' as key whitener", keyWhitener);
    return new KeyWhitenerSelector().getByName(keyWhitener);
  }

  @Bean
  public UserTokenStore userTokenStore() {
    String userTokenStore = config.getUserTokenStore();
    logger.info("Using '{}' as user token store", userTokenStore);
    return new UserTokenStoreSelector().getByName(userTokenStore);
  }

  @Bean
  public UserTokenGenerator userTokenGenerator() {
    String userTokenGenerator = config.getUserTokenGenerator();
    logger.info("Using '{}' as user token generator", userTokenGenerator);
    return new UserTokenGeneratorSelector().getByName(userTokenGenerator);
  }

  // XXX: maybe this is not the best place to init User Manager
  @Bean
  public UserManager userManager(UserStore userStore, KeyWhitener keyWhitener) {
    return new UserManager(userStore, keyWhitener);
  }
}
