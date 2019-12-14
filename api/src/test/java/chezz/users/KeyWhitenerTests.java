package chezz.users;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class KeyWhitenerTests {

  @DisplayName("Key Whitener tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"bcrypt", "argon2"})
  public void testKeyWhitener(String name) {
    KeyWhitener kw = new KeyWhitenerByName().getKeyWhitener(name);
    String hash_0 = kw.hash("key_0");
    String hash_1 = kw.hash("key_1");
    assertNotEquals(hash_0, hash_1);
    assertTrue(kw.verify("key_0", hash_0));
    assertTrue(kw.verify("key_1", hash_1));
  }
}
