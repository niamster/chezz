package chezz.datastore;

public class UserMeta {

  public final UserInfo userInfo;
  public final String hash;
  public final String id;

  public UserMeta(UserInfo userInfo, String hash, String id) {
    this.userInfo = userInfo;
    this.hash = hash;
    this.id = id;
  }
}
