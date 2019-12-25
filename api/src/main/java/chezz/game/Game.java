package chezz.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.codec.digest.DigestUtils;

public class Game implements Serializable {

  private static final long serialVersionUID = 3566215125987781138L;

  private final Map<String, Color> players = new HashMap<>();
  private Color turn;
  private Deck deck;
  private final String gameId;
  private String transactionId;

  public Game(String white) {
    this.turn = Color.WHITE;
    this.deck = new Deck();
    this.players.put(white, Color.WHITE);
    this.gameId = DigestUtils.sha256Hex(UUID.randomUUID().toString() + white);
    this.transactionId = DigestUtils.sha256Hex(this.gameId);
  }

  public String getCurrentTransactionId() {
    return this.transactionId;
  }

  public static String generateNextTransactionId(String transaction) {
    return DigestUtils.sha256Hex(transaction + ".");
  }

  private void commit() {
    this.transactionId = generateNextTransactionId(this.transactionId);
  }

  public byte[] dump() throws Exception {
    ByteArrayOutputStream boStream = new ByteArrayOutputStream();
    ObjectOutputStream oStream = new ObjectOutputStream(boStream);
    oStream.writeObject(this);
    oStream.close();
    return boStream.toByteArray();
  }

  public static Game load(byte[] data) {
    Game game = null;
    try {
      ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));
      game = (Game) iStream.readObject();
      iStream.close();
    } catch (Exception exc) {
    }
    return game;
  }

  public Game join(String black) throws Exception {
    if (players.size() != 1) {
      throw new GameException("too many players");
    }
    players.put(black, Color.BLACK);
    commit();
    return this;
  }

  public Deck getDeck() {
    return deck;
  }

  public void setDeck(String player, Deck deck) throws Exception {
    if (players.size() != 2) {
      throw new GameException("not enough players");
    }
    Color color = players.get(player);
    if (color != this.turn) {
      throw new GameException("this player can't do a move");
    }
    this.deck = deck;
    this.turn = color == Color.WHITE ? Color.BLACK : Color.WHITE;
    commit();
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
