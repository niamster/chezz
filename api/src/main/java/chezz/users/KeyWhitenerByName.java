package chezz.users;

import java.util.*;

public class KeyWhitenerByName {

  public static KeyWhitener getKeyWhitener(String name) {
    switch (name) {
      case "bcrypt":
        return new KeyWhitenerBCrypt();
      case "argon2":
        return new KeyWhitenerArgon2();
      default:
        throw new ServiceConfigurationError(String.format("Unknown key whitener '%s'", name));
    }
  }
}
