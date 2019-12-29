package api;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class APIAuthenticationFilter extends GenericFilterBean {

  private final APITokenAuthenticationService tokenAuthenticationService;

  public APIAuthenticationFilter(APITokenAuthenticationService tokenAuthenticationService) {
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    var authentication =
        tokenAuthenticationService.getAuthentication((HttpServletRequest) request).orElse(null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
