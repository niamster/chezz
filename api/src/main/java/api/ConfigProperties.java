package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ConfigProperties {

    @Autowired
    private ConfigSecurityProperties securityProperties;

    public ConfigSecurityProperties getSecurityProperties() {
        return securityProperties;
    }
}