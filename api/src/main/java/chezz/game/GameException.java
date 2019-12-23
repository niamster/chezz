package chezz.game;

public class GameException extends Exception {

  private static final long serialVersionUID = 4079281802990027494L;

  GameException(String msg) {
    super(msg);
  }
}
