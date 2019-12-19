package chezz.datastore;

import chezz.game.Game;
import java.util.*;
import java.util.concurrent.locks.*;

public class GameStoreMem implements GameStore {

  private final Map<String, Game> gameById = new HashMap<>();
  private final Map<String, Set<String>> gamesByUserId = new HashMap<>();
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock rLock = lock.readLock();
  private final Lock wLock = lock.writeLock();

  public GameStoreMem(final DataStoreConnectionInformation info) {}

  @Override
  public List<Game> getGames(String userId) {
    rLock.lock();
    try {
      Set<String> gameIds = gamesByUserId.get(userId);
      List<Game> games = new LinkedList<>();
      if (gameIds == null) {
        return games;
      }
      for (String gameId : gameIds) {
        games.add(gameById.get(gameId));
      }
      return games;
    } finally {
      rLock.unlock();
    }
  }

  @Override
  public Game getGame(String gameId) {
    rLock.lock();
    try {
      return gameById.get(gameId);
    } finally {
      rLock.unlock();
    }
  }

  @Override
  public void saveGame(Game game) {
    wLock.lock();
    try {
      gameById.put(game.getGameId(), game);
      for (String player : game.getPlayers()) {
        Set<String> games = gamesByUserId.computeIfAbsent(player, k -> new HashSet<>());
        games.add(game.getGameId());
      }
    } finally {
      wLock.unlock();
    }
  }
}
