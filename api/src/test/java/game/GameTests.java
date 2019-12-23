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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      Game game = new Game("p0").join("p1");
      game.setDeck("p0", new Deck());
      ObjectOutputStream os = new ObjectOutputStream(bos);
      os.writeObject(game);
      os.close();
      blob = bos.toByteArray();
      gameId = game.getGameId();
      assertNotNull(gameId);
      assertNotEquals("", gameId);
    }
    ByteArrayInputStream bis = new ByteArrayInputStream(blob);
    ObjectInputStream is = new ObjectInputStream(bis);
    Game game = (Game) is.readObject();
    assertEquals(Color.BLACK, game.getTurn());
    assertEquals(gameId, game.getGameId());
  }
}
