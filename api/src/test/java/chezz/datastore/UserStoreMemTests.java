package chezz.datastore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserStoreMemTests {

  @Test
  public void testAddUser() {
    UserStoreMem store = new UserStoreMem();
    assertEquals(null, store.getUser("test_user"));
    assertEquals(0, store.getAllUsers().size());
    store.addUser("test_user", new UserMeta("u@u", "--"));
    assertEquals("u@u", store.getUser("test_user").email);
  }
}
