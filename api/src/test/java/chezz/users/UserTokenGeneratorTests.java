package chezz.users;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTokenGeneratorTests {

  @Test
  public void testUnknownUserTokenGenerator() {
    UserTokenGenerator gen = new UserTokenGeneratorSelector().getByName("???");
    assertNull(gen);
  }

  @DisplayName("User Token Generator tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"simple"})
  public void testKeyWhitener(String name) {
    UserTokenGenerator gen = new UserTokenGeneratorSelector().getByName(name);
    assertNotEquals(gen.generateToken("a"), gen.generateToken("b"));
  }
}
