package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserStoreMemTests {

  @Test
  public void testAddUser() {
    UserStoreMem store = new UserStoreMem();
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
