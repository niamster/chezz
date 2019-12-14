package chezz.users;

public class UserTokenGeneratorSelector {

  public UserTokenGenerator getByName(String name) {
    switch (name) {
      case "simple":
        return new UserTokenGeneratorSimple();
      default:
        return null;
    }
  }
}
