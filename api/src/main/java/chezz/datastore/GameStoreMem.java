package chezz.datastore;

import chezz.game.Game;
import java.util.*;
import java.util.concurrent.locks.*;

public class GameStoreMem implements GameStore {

  private final Map<String, byte[]> gameById = new HashMap<>();
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
        try {
          games.add(Game.load(gameById.get(gameId)));
        } catch (Exception exc) {
        }
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
      return Game.load(gameById.get(gameId));
    } catch (Exception exc) {
      return null;
    } finally {
      rLock.unlock();
    }
  }

  @Override
  public void saveGame(Game game) {
    wLock.lock();
    try {
      gameById.put(game.getGameId(), game.dump());
      for (String player : game.getPlayers()) {
        Set<String> games = gamesByUserId.computeIfAbsent(player, k -> new HashSet<>());
        games.add(game.getGameId());
      }
    } catch (Exception exc) {
    } finally {
      wLock.unlock();
    }
  }
}
