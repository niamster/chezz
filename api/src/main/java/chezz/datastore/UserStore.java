package chezz.datastore;

import java.util.*;

public interface UserStore {

  public boolean addUser(String username, UserMeta meta);

  public UserMeta getUser(String username);

  public List<String> getAllUsers();
}
