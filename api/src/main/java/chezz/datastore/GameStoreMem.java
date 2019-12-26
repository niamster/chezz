package chezz.datastore;

import chezz.game.Game;
import java.util.*;
import java.util.concurrent.locks.*;

public class GameStoreMem implements GameStore {

  private final Map<String, byte[]> gameById = new HashMap<>();
  private final Map<String, String> transactionIdByGameId = new HashMap<>();
  private final Map<String, Set<String>> gamesByUserId = new HashMap<>();
  private final Set<String> openGames = new LinkedHashSet<>();
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
        games.add(Game.load(gameById.get(gameId)));
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
    } finally {
      rLock.unlock();
    }
  }

  @Override
  public void saveGame(Game game) throws Exception {
    wLock.lock();
    try {
      String gameId = game.getGameId();
      String currentTransactionId = transactionIdByGameId.get(gameId);
      if (game.isOpen()) {
        this.openGames.add(gameId);
      } else {
        this.openGames.remove(gameId);
      }
      if (currentTransactionId != null) {
        String expectedTransactionId = Game.generateNextTransactionId(currentTransactionId);
        if (!expectedTransactionId.equals(game.getCurrentTransactionId())) {
          throw new GameStoreException("invalid transaction ID");
        }
      }
      gameById.put(gameId, game.dump());
      transactionIdByGameId.put(gameId, game.getCurrentTransactionId());
      for (String player : game.getPlayers()) {
        Set<String> games = gamesByUserId.computeIfAbsent(player, k -> new HashSet<>());
        games.add(gameId);
      }
    } finally {
      wLock.unlock();
    }
  }

  @Override
  public List<String> getOpenGameIds() {
    List<String> games = new LinkedList<>();
    rLock.lock();
    try {
      games.addAll(this.openGames);
    } finally {
      rLock.unlock();
    }
    return games;
  }
}
