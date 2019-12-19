package chezz.datastore;

public class GameStoreSelector {
  public GameStore getByName(String name, final DataStoreConnectionInformation info) {
    switch (name) {
      case "mem":
        return new GameStoreMem(info);
      default:
        return null;
    }
  }
}
