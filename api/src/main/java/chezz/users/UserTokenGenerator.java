package chezz.users;

public interface UserTokenGenerator {

  String generateToken(String username);
}
