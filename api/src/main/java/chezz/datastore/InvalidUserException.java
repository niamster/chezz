package chezz.datastore;

public class InvalidUserException extends Exception {

  private static final long serialVersionUID = -3253705565043464949L;

  public InvalidUserException(String message) {
    super(message);
  }
}
