package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import chezz.game.Color;
import chezz.game.Deck;
import chezz.game.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GameStoreTests {

  @Test
  public void testUnknownStore() {
    GameStore store = new GameStoreSelector().getByName("???", null);
    assertNull(store);
  }

  @DisplayName("Game Store tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testGameStore(String name) {
    GameStore store = new GameStoreSelector().getByName(name, null);
    assertEquals(0, store.getGames("user_0").size());
    store.saveGame(new Game("user_0", "user_1"));
    assertEquals(1, store.getGames("user_0").size());
    assertEquals(1, store.getGames("user_1").size());
    store.saveGame(new Game("user_0", "user_1"));
    assertEquals(2, store.getGames("user_0").size());
    assertEquals(2, store.getGames("user_1").size());
    Game game = store.getGames("user_0").get(0);
    assertDoesNotThrow(() -> game.setDeck("user_0", new Deck()));
    store.saveGame(game);
    assertEquals(Color.BLACK, store.getGame(game.getGameId()).getTurn());
  }
}
