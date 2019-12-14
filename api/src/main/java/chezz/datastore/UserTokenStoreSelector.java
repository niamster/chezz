package chezz.datastore;

public class UserTokenStoreSelector {
  public UserTokenStore getByName(String name) {
    switch (name) {
      case "mem":
        return new UserTokenStoreMem();
      default:
        return null;
    }
  }
}
