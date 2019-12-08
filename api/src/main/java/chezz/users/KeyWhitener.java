package chezz.users;

public interface KeyWhitener {

  String hash(String key);

  boolean verify(String key, String hash);
}
