package chezz.datastore;

import java.util.*;

public class UserTokenStoreSelector {
  public Optional<UserTokenStore> getByName(
      String name, final DataStoreConnectionInformation info) {
    switch (name) {
      case "mem":
        return Optional.of(new UserTokenStoreMem(info));
      default:
        return Optional.empty();
    }
  }
}
