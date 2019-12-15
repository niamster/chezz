package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTokenStoreTests {

  @Test
  public void testUnknownStore() {
    UserTokenStore store = new UserTokenStoreSelector().getByName("???", null);
    assertNull(store);
  }

  @DisplayName("User Token Store corner cases tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testCornerCases(String name) {
    UserTokenStore store = new UserTokenStoreSelector().getByName(name, null);
    assertThrows(
        InvalidUserTokenException.class,
        () -> {
          store.setUserToken(null, null);
        });
    assertThrows(
        InvalidUserTokenException.class,
        () -> {
          store.setUserToken("", null);
        });
    assertThrows(
        InvalidUserTokenException.class,
        () -> {
          store.setUserToken(".", null);
        });
    assertThrows(
        InvalidUserTokenException.class,
        () -> {
          store.setUserToken(".", "");
        });
    assertThrows(
        InvalidUserTokenException.class,
        () -> {
          store.removeToken(null);
        });
    assertThrows(
        InvalidUserTokenException.class,
        () -> {
          store.removeToken("");
        });
  }

  @DisplayName("User Token Store tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testSetToken(String name) throws Exception {
    UserTokenStore store = new UserTokenStoreSelector().getByName(name, null);
    String token0 = "token-0", token1 = "token-1";

    assertNull(store.getUser(token0));
    assertNull(store.getUser(token1));

    store.setUserToken("test_user", token0);
    assertEquals("test_user", store.getUser(token0));
    assertNull(store.getUser(token1));

    store.removeToken(token0);
    assertNull(store.getUser(token0));
    assertNull(store.getUser(token1));

    store.setUserToken("test_user", token0);
    store.setUserToken("test_user", token1);
    assertNull(store.getUser(token0));
    assertEquals("test_user", store.getUser(token1));

    assertNull(store.getUser(token0));
    assertEquals("test_user", store.getUser(token1));
  }
}
