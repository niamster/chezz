package api;

import java.util.*;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserManager {

    private final Logger logger = LogManager.getLogger(UserStoreMem.class);
    private final UserStore store;
    private final int argon2Iterations;
    private static final int argon2MemKiB = 65536;
    private static final int argon2Parallelism = 1;
    private static final int argon2MaxMs = 1000;

    public UserManager(UserStore store) {
        this.store = store;
        Argon2 argon2 = Argon2Factory.create();
        argon2Iterations = Argon2Helper.findIterations(argon2, argon2MaxMs, argon2MemKiB, argon2Parallelism);
        logger.info("Argon2 iterations {}", argon2Iterations);
    }

    public boolean addUser(String username, String email, String password) {
        Argon2 argon2 = Argon2Factory.create();
        char[] passwordArray = password.toCharArray();
        try {
            String hash = argon2.hash(argon2Iterations, argon2MemKiB, argon2Parallelism, passwordArray);
            return store.addUser(username, new UserStore.UserMeta(email, hash));
        } finally {
            argon2.wipeArray(passwordArray);
        }
    }

    public boolean verifyUser(String username, String password) {
        UserStore.UserMeta meta = store.getUser(username);
        Argon2 argon2 = Argon2Factory.create();
        char[] passwordArray = password.toCharArray();
        try {
            return argon2.verify(meta.hash, passwordArray);
        } finally {
            argon2.wipeArray(passwordArray);
        }
    }

    public List<String> getAllUsers() {
        return store.getAllUsers();
    }
}