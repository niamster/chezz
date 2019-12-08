package chezz.users;

public interface KeyWhitener {

  public String hash(String key);

  public boolean verify(String key, String hash);
}
