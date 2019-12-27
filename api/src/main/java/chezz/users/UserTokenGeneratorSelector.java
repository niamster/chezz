package chezz.users;

import java.util.*;

public class UserTokenGeneratorSelector {

  public Optional<UserTokenGenerator> getByName(String name) {
    switch (name) {
      case "simple":
        return Optional.of(new UserTokenGeneratorSimple());
      default:
        return Optional.empty();
    }
  }
}
