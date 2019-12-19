package chezz.game;

public class InvalidMoveException extends Exception {

  private static final long serialVersionUID = 4079281802990027494L;

  InvalidMoveException(String msg) {
    super(msg);
  }
}
