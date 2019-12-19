package chezz.game;

import java.io.Serializable;
import java.util.*;
import org.apache.commons.codec.digest.DigestUtils;

public class Game implements Serializable {

  private static final long serialVersionUID = 3566215125987781138L;

  private final Map<String, Color> players = new HashMap<>();
  private Color turn;
  private Deck deck;
  private final String gameId;

  public Game(String white, String black) {
    this.turn = Color.WHITE;
    this.deck = new Deck();
    this.players.put(white, Color.WHITE);
    this.players.put(black, Color.BLACK);
    this.gameId = DigestUtils.sha256Hex(UUID.randomUUID().toString() + white + black);
  }

  public Deck getDeck() {
    return deck;
  }

  public void setDeck(String player, Deck deck) throws Exception {
    Color color = players.get(player);
    if (color != this.turn) {
      throw new InvalidMoveException("this player can't do a move");
    }
    this.deck = deck;
    this.turn = color == Color.WHITE ? Color.BLACK : Color.WHITE;
  }

  public List<String> getPlayers() {
    return new LinkedList<>(this.players.keySet());
  }

  public Color getTurn() {
    return turn;
  }

  public String getGameId() {
    return gameId;
  }
}
