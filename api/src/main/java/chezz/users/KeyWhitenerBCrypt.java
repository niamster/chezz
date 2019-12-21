package chezz.users;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class KeyWhitenerBCrypt implements KeyWhitener {

  @Override
  public String hash(String key) {
    return BCrypt.hashpw(key, BCrypt.gensalt());
  }

  @Override
  public boolean verify(String key, String hash) {
    return BCrypt.checkpw(key, hash);
  }
}
