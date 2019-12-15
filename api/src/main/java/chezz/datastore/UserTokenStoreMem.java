package chezz.datastore;

import java.util.*;
import java.util.concurrent.locks.*;

public class UserTokenStoreMem implements UserTokenStore {

  private final Map<String, String> tokenByUser = new HashMap<>();
  private final Map<String, String> userByToken = new HashMap<>();
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock rLock = lock.readLock();
  private final Lock wLock = lock.writeLock();

  public UserTokenStoreMem(DataStoreConnectionInformation info) {}

  public String getUser(String token) {
    rLock.lock();
    try {
      return userByToken.get(token);
    } finally {
      rLock.unlock();
    }
  }

  public void setUserToken(String username, String token) {
    wLock.lock();
    try {
      String oldToken = tokenByUser.get(username);
      if (oldToken != null) {
        userByToken.remove(oldToken);
      }
      userByToken.put(token, username);
      tokenByUser.put(username, token);
    } finally {
      wLock.unlock();
    }
  }

  public void removeToken(String token) {
    if (token == null) {
      return;
    }
    wLock.lock();
    try {
      userByToken.remove(token);
    } finally {
      wLock.unlock();
    }
  }
}
