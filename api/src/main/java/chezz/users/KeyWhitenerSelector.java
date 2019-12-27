package chezz.users;

import java.util.*;

public class KeyWhitenerSelector {

  public Optional<KeyWhitener> getByName(String name) {
    switch (name) {
      case "bcrypt":
        return Optional.of(new KeyWhitenerBCrypt());
      case "argon2":
        return Optional.of(new KeyWhitenerArgon2());
      default:
        return Optional.empty();
    }
  }
}
