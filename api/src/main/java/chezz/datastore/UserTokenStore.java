package chezz.datastore;

public interface UserTokenStore {

  String getUser(String token);

  void setUserToken(String username, String token) throws InvalidUserTokenException;

  void removeToken(String token) throws InvalidUserTokenException;
}
