package api;

import java.util.*;

interface UserStore {

  static class UserMeta {

    String email;
    String hash;

    UserMeta(String email, String hash) {
      this.email = email;
      this.hash = hash;
    }
  }

  public boolean addUser(String username, UserMeta meta);

  public UserMeta getUser(String username);

  public List<String> getAllUsers();
}
