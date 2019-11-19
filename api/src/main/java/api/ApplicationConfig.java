package api;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
class ApplicationConfig {
    @Bean
    public UserStore userStore() {
        return new UserStoreMem();
    }

    @Bean
    public KeyWhitener keyWhitener() {
        return new KeyWhitenerArgon2();
    }
}