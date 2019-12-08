package chezz.datastore;

import java.util.*;

public interface UserStore {

  boolean addUser(String username, UserMeta meta);

  UserMeta getUser(String username);

  List<String> getAllUsers();
}
