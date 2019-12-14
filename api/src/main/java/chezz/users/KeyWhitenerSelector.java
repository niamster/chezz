package chezz.users;

public class KeyWhitenerSelector {

  public KeyWhitener getByName(String name) {
    switch (name) {
      case "bcrypt":
        return new KeyWhitenerBCrypt();
      case "argon2":
        return new KeyWhitenerArgon2();
      default:
        return null;
    }
  }
}
