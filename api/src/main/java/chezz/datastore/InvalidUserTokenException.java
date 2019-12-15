package chezz.datastore;

public class InvalidUserTokenException extends Exception {

  private static final long serialVersionUID = -4603457099894694687L;

  public InvalidUserTokenException(String message) {
    super(message);
  }
}
