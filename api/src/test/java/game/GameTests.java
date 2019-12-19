package game;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import chezz.game.Color;
import chezz.game.Deck;
import chezz.game.Game;
import chezz.game.InvalidMoveException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.jupiter.api.Test;

public class GameTests {

  @Test
  public void testGameMove() {
    Game game = new Game("p0", "p1");
    assertThrows(InvalidMoveException.class, () -> game.setDeck("p1", new Deck()));
    assertDoesNotThrow(() -> game.setDeck("p0", new Deck()));
    assertDoesNotThrow(() -> game.setDeck("p1", new Deck()));
  }

  @Test
  public void testSerialize() throws Exception {
    byte[] blob;
    String gameId;
    {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      Game game = new Game("p0", "p1");
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
