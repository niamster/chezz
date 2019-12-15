package chezz.datastore;

import java.util.*;
import java.util.concurrent.locks.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserStoreMem implements UserStore {

  private final Logger logger = LogManager.getLogger(UserStoreMem.class);
  private final Map<String, UserMeta> metaById = new HashMap<>();
  private final Map<String, UserMeta> metaByUsername = new HashMap<>();
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock rLock = lock.readLock();
  private final Lock wLock = lock.writeLock();

  public boolean addUser(UserInfo userInfo, String hash) {
    String id = UUID.randomUUID().toString();
    wLock.lock();
    try {
      if (metaByUsername.containsKey(userInfo.username)) {
        return false;
      }
      UserMeta userMeta = new UserMeta(userInfo, hash, id);
      metaByUsername.put(userInfo.username, userMeta);
      metaById.put(id, userMeta);
      logger.info("User '{}' added", userInfo.username);
    } finally {
      wLock.unlock();
    }
    return true;
  }

  public UserMeta getUserById(String id) {
    rLock.lock();
    try {
      return metaById.get(id);
    } finally {
      rLock.unlock();
    }
  }

  public UserMeta getUserByName(String username) {
    rLock.lock();
    try {
      return metaByUsername.get(username);
    } finally {
      rLock.unlock();
    }
  }

  public List<String> getAllUsers() {
    rLock.lock();
    List<String> users = new Vector<String>(metaByUsername.size());
    try {
      for (String username : metaByUsername.keySet()) {
        users.add(username);
      }
    } finally {
      rLock.unlock();
    }
    return users;
  }
}
