package chezz.datastore;

import java.util.*;

public class UserStoreSelector {
  public Optional<UserStore> getByName(String name, final DataStoreConnectionInformation info) {
    switch (name) {
      case "mem":
        return Optional.of(new UserStoreMem(info));
      default:
        return Optional.empty();
    }
  }
}
