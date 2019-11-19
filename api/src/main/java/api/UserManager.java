package api;

import java.util.*;

public class UserManager {

    private final UserStore store;
    private final KeyWhitener keyWhitener;

    public UserManager(UserStore store, KeyWhitener keyWhitener) {
        this.store = store;
        this.keyWhitener = keyWhitener;
    }

    public boolean addUser(String username, String email, String password) {
        String hash = keyWhitener.hash(password);
        return store.addUser(username, new UserStore.UserMeta(email, hash));
    }

    public boolean verifyUser(String username, String password) {
        UserStore.UserMeta meta = store.getUser(username);
        return keyWhitener.verify(password, meta.hash);
    }

    public List<String> getAllUsers() {
        return store.getAllUsers();
    }
}