package api;

import chezz.datastore.UserTokenStore;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class APITokenAuthenticationService {

  private final UserTokenStore userTokenStore;

  public APITokenAuthenticationService(UserTokenStore userTokenStore) {
    this.userTokenStore = userTokenStore;
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getParameter(APISecurity.USER_TOKEN_PARAM);
    if (token == null) {
      return null;
    }
    String username = userTokenStore.getUser(token);
    if (username == null) {
      return null;
    }
    return new UsernamePasswordAuthenticationToken(username, token, Collections.emptyList());
  }
}
