package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class UserTokenStoreMemTests {

  @Test
  public void testSetToken() {
    UserTokenStoreMem store = new UserTokenStoreMem();
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

    store.removeToken(null);
    assertNull(store.getUser(token0));
    assertEquals("test_user", store.getUser(token1));
  }
}
