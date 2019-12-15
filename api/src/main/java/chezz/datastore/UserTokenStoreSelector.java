package chezz.datastore;

public class UserTokenStoreSelector {
  public UserTokenStore getByName(String name, final DataStoreConnectionInformation info) {
    switch (name) {
      case "mem":
        return new UserTokenStoreMem(info);
      default:
        return null;
    }
  }
}
