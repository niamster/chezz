package api;

import java.util.*;
import java.util.concurrent.locks.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class UserStoreMem implements UserStore {
    private final Logger logger = LogManager.getLogger(UserStoreMem.class);
    private final Map<String, UserStore.UserMeta> store = new HashMap<String, UserStore.UserMeta>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock rLock = lock.readLock();
    private final Lock wLock = lock.writeLock();

    public boolean addUser(String username, UserMeta meta) {
        wLock.lock();
        try {
            if (store.containsKey(username)) {
                return false;
            }
            store.put(username, meta);
            logger.info("User '{}' added", username);
        } finally {
            wLock.unlock();
        }
        return true;
    }

    public UserMeta getUser(String username) {
        rLock.lock();
        try {
            return store.get(username);
        } finally {
            rLock.unlock();
        }
    }

    public List<String> getAllUsers() {
        rLock.lock();
        List<String> users = new Vector<String>(store.size());
        try {
            for (String username : store.keySet()) {
                users.add(username);
            }
        } finally {
            rLock.unlock();
        }
        return users;
    }
}