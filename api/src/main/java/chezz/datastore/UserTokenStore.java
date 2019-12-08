package chezz.datastore;

public interface UserTokenStore {

  String getUser(String token);

  void setUserToken(String username, String token);

  void removeToken(String token);
}
