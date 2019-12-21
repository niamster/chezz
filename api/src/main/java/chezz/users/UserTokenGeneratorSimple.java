package chezz.users;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Hex;

public class UserTokenGeneratorSimple implements UserTokenGenerator {

  private final SecureRandom rng = new SecureRandom();

  @Override
  public String generateToken(String username) {
    byte[] random = new byte[16];
    rng.nextBytes(random);
    return Hex.encodeHexString(random);
  }
}
