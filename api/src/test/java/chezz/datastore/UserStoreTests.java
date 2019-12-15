package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserStoreTests {

  @Test
  public void testUnknownStore() {
    UserStore store = new UserStoreSelector().getByName("???", null);
    assertNull(store);
  }

  @DisplayName("User Store corner case tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testCornerCases(String name) throws Exception {
    UserStore store = new UserStoreSelector().getByName(name, null);
    assertThrows(
        InvalidUserException.class,
        () -> {
          store.addUser(null, null);
        });
    assertThrows(
        InvalidUserException.class,
        () -> {
          store.addUser(new UserInfo(null, ""), null);
        });
    assertThrows(
        InvalidUserException.class,
        () -> {
          store.addUser(new UserInfo("", ""), null);
        });
    assertThrows(
        InvalidUserException.class,
        () -> {
          store.addUser(new UserInfo(".", ""), null);
        });
    assertThrows(
        InvalidUserException.class,
        () -> {
          store.addUser(new UserInfo(".", ""), "");
        });
  }

  @DisplayName("User Store tests")
  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"mem"})
  public void testAddUser(String name) throws Exception {
    UserStore store = new UserStoreSelector().getByName(name, null);
    assertNull(store.getUserByName("test_user"));
    assertEquals(0, store.getAllUsers().size());
    assertTrue(store.addUser(new UserInfo("test_user", "u@u"), "--"));
    UserMeta userMeta = store.getUserByName("test_user");
    assertEquals("u@u", userMeta.userInfo.email);
    assertEquals(userMeta, store.getUserById(userMeta.id));
    assertEquals(1, store.getAllUsers().size());
    assertFalse(store.addUser(new UserInfo("test_user", "u@u"), "--"));
  }
}
