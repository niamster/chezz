package chezz.datastore;

import java.util.*;

public interface UserStore {

  boolean addUser(UserInfo userInfo, String hash) throws InvalidUserException;

  Optional<UserMeta> getUserById(String userId);

  Optional<UserMeta> getUserByName(String username);

  List<String> getAllUsers();
}
