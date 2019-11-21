package api;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class APISecurity extends WebSecurityConfigurerAdapter {

    public static final String PUBLIC_EP_PREFIX = "/public";
    public static final String PROTECTED_EP_PREFIX = "/protected";
    public static final String USER_TOKEN_PARAM = "user_token";

    private final APITokenAuthenticationService tokenAuthenticationService;

    public APISecurity(UserTokenStore userTokenStore) {
        this.tokenAuthenticationService = new APITokenAuthenticationService(userTokenStore);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(PUBLIC_EP_PREFIX + "/**").permitAll()
                .antMatchers(PROTECTED_EP_PREFIX + "/**").authenticated()
                .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    public APIAuthenticationFilter authenticationFilter() throws Exception {
        return new APIAuthenticationFilter(tokenAuthenticationService);
    }
}
