package chezz.datastore;

import java.util.*;

public class GameStoreSelector {
  public Optional<GameStore> getByName(String name, final DataStoreConnectionInformation info) {
    switch (name) {
      case "mem":
        return Optional.of(new GameStoreMem(info));
      default:
        return Optional.empty();
    }
  }
}
