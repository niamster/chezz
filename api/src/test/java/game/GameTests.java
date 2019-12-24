package game;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import chezz.game.Color;
import chezz.game.Deck;
import chezz.game.Game;
import chezz.game.GameException;
import org.junit.jupiter.api.Test;

public class GameTests {

  @Test
  public void testInvalidGame() throws Exception {
    Game game_0 = new Game("p0").join("p1");
    assertThrows(GameException.class, () -> game_0.join("p2"));
    Game game_1 = new Game("p0");
    assertThrows(GameException.class, () -> game_1.setDeck("p1", new Deck()));
  }

  @Test
  public void testGameMove() throws Exception {
    Game game = new Game("p0").join("p1");
    assertThrows(GameException.class, () -> game.setDeck("p1", new Deck()));
    assertDoesNotThrow(() -> game.setDeck("p0", new Deck()));
    assertDoesNotThrow(() -> game.setDeck("p1", new Deck()));
  }

  @Test
  public void testSerialize() throws Exception {
    byte[] blob;
    String gameId;
    {
      Game game = new Game("p0").join("p1");
      game.setDeck("p0", new Deck());
      blob = game.dump();
      gameId = game.getGameId();
      assertNotNull(gameId);
      assertNotEquals("", gameId);
    }
    Game game = Game.load(blob);
    assertEquals(Color.BLACK, game.getTurn());
    assertEquals(gameId, game.getGameId());

    assertThrows(Exception.class, () -> Game.load(null));
  }
}
