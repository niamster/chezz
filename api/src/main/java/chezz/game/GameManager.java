package chezz.game;

import chezz.datastore.GameStore;
import java.util.*;

public class GameManager {

  private final GameStore gameStore;

  public GameManager(GameStore gameStore) {
    this.gameStore = gameStore;
  }

  public Game newGame(String userId) throws Exception {
    // NOTE: This is a fake protection from creation of multiple new games
    for (var game : gameStore.getGamesByUserId(userId)) {
      if (game.isOpen()) {
        throw new GameException("user already has open games");
      }
    }
    var game = new Game(userId);
    gameStore.saveGame(game);
    return game;
  }

  public Optional<Game> joinGame(String userId) {
    var gameIds = gameStore.getOpenGameIds();
    Collections.shuffle(gameIds);
    for (var gameId : gameIds) {
      try {
        var game = gameStore.getGameById(gameId).get();
        game.join(userId);
        gameStore.saveGame(game);
        return Optional.of(game);
      } catch (Exception ignored) {
      }
    }
    return Optional.empty();
  }
}
