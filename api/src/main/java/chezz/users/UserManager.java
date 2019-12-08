package chezz.users;

import chezz.datastore.UserMeta;
import chezz.datastore.UserStore;
import java.util.*;

public class UserManager {

  private final UserStore store;
  private final KeyWhitener keyWhitener;

  public UserManager(UserStore store, KeyWhitener keyWhitener) {
    this.store = store;
    this.keyWhitener = keyWhitener;
  }

  public boolean addUser(String username, String email, String password) {
    String hash = keyWhitener.hash(password);
    return store.addUser(username, new UserMeta(email, hash));
  }

  public boolean verifyUser(String username, String password) {
    UserMeta meta = store.getUser(username);
    if (meta == null) {
      return false;
    }
    return keyWhitener.verify(password, meta.hash);
  }

  public List<String> getAllUsers() {
    return store.getAllUsers();
  }

  public UserMeta getUserInfo(String username) {
    return store.getUser(username);
  }
}
