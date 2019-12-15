package chezz.users;

import chezz.datastore.InvalidUserException;
import chezz.datastore.UserInfo;
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
    try {
      return store.addUser(new UserInfo(username, email), hash);
    } catch (InvalidUserException exc) {
      return false;
    }
  }

  public boolean verifyUser(String username, String password) {
    UserMeta userMeta = store.getUserByName(username);
    if (userMeta == null) {
      return false;
    }
    return keyWhitener.verify(password, userMeta.hash);
  }

  public List<String> getAllUsers() {
    return store.getAllUsers();
  }

  public UserInfo getUserInfo(String username) {
    return store.getUserByName(username).userInfo;
  }
}
