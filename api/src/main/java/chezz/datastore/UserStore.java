package chezz.datastore;

import java.util.*;

public interface UserStore {

  boolean addUser(UserInfo userInfo, String hash) throws InvalidUserException;

  UserMeta getUserById(String userId);

  UserMeta getUserByName(String username);

  List<String> getAllUsers();
}
