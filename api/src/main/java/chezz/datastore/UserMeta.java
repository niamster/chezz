package chezz.datastore;

public class UserMeta {

  public final String email;
  public final String hash;

  public UserMeta(String email, String hash) {
    this.email = email;
    this.hash = hash;
  }
}
