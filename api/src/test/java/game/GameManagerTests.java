package game;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chezz.datastore.GameStoreSelector;
import chezz.game.GameException;
import chezz.game.GameManager;
import org.junit.jupiter.api.Test;

public class GameManagerTests {

  @Test
  public void testUserManager() {
    var gameStore = new GameStoreSelector().getByName("mem", null).get();
    var gameManager = new GameManager(gameStore);
    assertTrue(gameManager.joinGame("user_0").isEmpty());
    assertDoesNotThrow(() -> gameManager.newGame("user_1"));
    assertThrows(GameException.class, () -> gameManager.newGame("user_1"));
    var game = gameManager.joinGame("user_0");
    assertTrue(game.isPresent());
    assertEquals(game.get().getGameId(), gameStore.getGamesByUserId("user_0").get(0).getGameId());
    assertEquals(game.get().getGameId(), gameStore.getGamesByUserId("user_1").get(0).getGameId());
    assertDoesNotThrow(() -> gameManager.newGame("user_1"));
  }
}
