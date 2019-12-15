package chezz.datastore;

public class UserStoreSelector {
  public UserStore getByName(String name, final DataStoreConnectionInformation info) {
    switch (name) {
      case "mem":
        return new UserStoreMem(info);
      default:
        return null;
    }
  }
}
