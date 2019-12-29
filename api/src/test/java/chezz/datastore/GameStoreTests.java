package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertTrue(new GameStoreSelector().getByName("???", null).isEmpty());
  }

  @DisplayName("Game Store tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testGameStore(String name) throws Exception {
    var store = new GameStoreSelector().getByName(name, null).get();
    assertEquals(0, store.getGames("user_0").size());
    store.saveGame(new Game("user_0").join("user_1"));
    assertEquals(1, store.getGames("user_0").size());
    assertEquals(1, store.getGames("user_1").size());
    store.saveGame(new Game("user_0").join("user_1"));
    assertEquals(2, store.getGames("user_0").size());
    assertEquals(2, store.getGames("user_1").size());
    var game = store.getGames("user_0").get(0);
    assertDoesNotThrow(() -> game.setDeck("user_0", new Deck()));
    store.saveGame(game);
    assertEquals(Color.BLACK, store.getGame(game.getGameId()).get().getTurn());
    game.setDeck("user_1", new Deck());
    game.setDeck("user_0", new Deck());
    var thrown = assertThrows(GameStoreException.class, () -> store.saveGame(game));
    assertEquals("invalid transaction ID", thrown.getMessage());

    assertEquals(0, store.getOpenGameIds().size());
    var tGame = new Game("user_0");
    store.saveGame(tGame);
    assertEquals(1, store.getOpenGameIds().size());
    tGame.join("user_1");
    store.saveGame(tGame);
    assertEquals(0, store.getOpenGameIds().size());

    assertTrue(store.getGame("xyz").isEmpty());
  }
}
