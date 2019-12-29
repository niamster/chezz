package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTokenStoreTests {

  @Test
  public void testUnknownStore() {
    assertTrue(new UserTokenStoreSelector().getByName("???", null).isEmpty());
  }

  @DisplayName("User Token Store corner cases tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testCornerCases(String name) {
    UserTokenStore store = new UserTokenStoreSelector().getByName(name, null).get();
    assertThrows(InvalidUserTokenException.class, () -> store.setUserToken(null, null));
    assertThrows(InvalidUserTokenException.class, () -> store.setUserToken("", null));
    assertThrows(InvalidUserTokenException.class, () -> store.setUserToken(".", null));
    assertThrows(InvalidUserTokenException.class, () -> store.setUserToken(".", ""));
    assertThrows(InvalidUserTokenException.class, () -> store.removeToken(null));
    assertThrows(InvalidUserTokenException.class, () -> store.removeToken(""));
  }

  @DisplayName("User Token Store tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testSetToken(String name) throws Exception {
    var store = new UserTokenStoreSelector().getByName(name, null).get();
    String token0 = "token-0", token1 = "token-1";

    assertTrue(store.getUser(token0).isEmpty());
    assertTrue(store.getUser(token1).isEmpty());

    store.setUserToken("test_user", token0);
    assertEquals("test_user", store.getUser(token0).get());
    assertTrue(store.getUser(token1).isEmpty());

    store.removeToken(token0);
    assertTrue(store.getUser(token0).isEmpty());
    assertTrue(store.getUser(token1).isEmpty());

    store.setUserToken("test_user", token0);
    store.setUserToken("test_user", token1);
    assertTrue(store.getUser(token0).isEmpty());
    assertEquals("test_user", store.getUser(token1).get());

    assertTrue(store.getUser(token0).isEmpty());
    assertEquals("test_user", store.getUser(token1).get());
  }
}
