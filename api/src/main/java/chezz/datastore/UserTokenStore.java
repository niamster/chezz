package chezz.datastore;

import java.util.*;

public interface UserTokenStore {

  Optional<String> getUser(String token);

  void setUserToken(String username, String token) throws InvalidUserTokenException;

  void removeToken(String token) throws InvalidUserTokenException;
}
