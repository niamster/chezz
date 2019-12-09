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
    assertNull(store.getUser("test_user"));
    assertEquals(0, store.getAllUsers().size());
    assertTrue(store.addUser("test_user", new UserMeta("u@u", "--")));
    assertEquals("u@u", store.getUser("test_user").email);
    assertEquals(1, store.getAllUsers().size());
    assertFalse(store.addUser("test_user", new UserMeta("u@u", "--")));
  }
}
