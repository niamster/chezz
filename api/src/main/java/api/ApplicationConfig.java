package api;

import chezz.datastore.DataStoreConnectionInformation;
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
  public String appVersion() throws Exception {
    var input = getClass().getClassLoader().getResourceAsStream("app.properties");
    Properties prop = new Properties();
    prop.load(input);
    return prop.getProperty("version");
  }

  @Bean
  public UserStore userStore() {
    var userStore = config.getUserStore();
    logger.info("Using '{}' as user store", userStore);
    return new UserStoreSelector().getByName(userStore, new DataStoreConnectionInformation()).get();
  }

  @Bean
  public KeyWhitener keyWhitener() {
    var keyWhitener = config.getSecurityProperties().getKeyWhitener();
    logger.info("Using '{}' as key whitener", keyWhitener);
    return new KeyWhitenerSelector().getByName(keyWhitener).get();
  }

  @Bean
  public UserTokenStore userTokenStore() {
    var userTokenStore = config.getUserTokenStore();
    logger.info("Using '{}' as user token store", userTokenStore);
    return new UserTokenStoreSelector()
        .getByName(userTokenStore, new DataStoreConnectionInformation())
        .get();
  }

  @Bean
  public UserTokenGenerator userTokenGenerator() {
    var userTokenGenerator = config.getUserTokenGenerator();
    logger.info("Using '{}' as user token generator", userTokenGenerator);
    return new UserTokenGeneratorSelector().getByName(userTokenGenerator).get();
  }

  // XXX: maybe this is not the best place to init User Manager
  @Bean
  public UserManager userManager(UserStore userStore, KeyWhitener keyWhitener) {
    return new UserManager(userStore, keyWhitener);
  }
}
