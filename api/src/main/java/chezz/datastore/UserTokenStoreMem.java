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

  @Override
  public Optional<String> getUser(String token) {
    rLock.lock();
    try {
      return Optional.ofNullable(userByToken.get(token));
    } finally {
      rLock.unlock();
    }
  }

  @Override
  public void setUserToken(String username, String token) throws InvalidUserTokenException {
    if (username == null || username.isEmpty()) {
      throw new InvalidUserTokenException("invalid username");
    }
    if (token == null || token.isEmpty()) {
      throw new InvalidUserTokenException("invalid token");
    }

    wLock.lock();
    try {
      var oldToken = tokenByUser.get(username);
      if (oldToken != null) {
        userByToken.remove(oldToken);
      }
      userByToken.put(token, username);
      tokenByUser.put(username, token);
    } finally {
      wLock.unlock();
    }
  }

  @Override
  public void removeToken(String token) throws InvalidUserTokenException {
    if (token == null || token.isEmpty()) {
      throw new InvalidUserTokenException("invalid token");
    }

    wLock.lock();
    try {
      userByToken.remove(token);
    } finally {
      wLock.unlock();
    }
  }
}
