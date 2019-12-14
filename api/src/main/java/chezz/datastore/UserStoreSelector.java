package chezz.datastore;

public class UserStoreSelector {
  public UserStore getByName(String name) {
    switch (name) {
      case "mem":
        return new UserStoreMem();
      default:
        return null;
    }
  }
}
