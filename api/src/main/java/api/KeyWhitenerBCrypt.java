package api;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class KeyWhitenerBCrypt implements KeyWhitener {

    public String hash(String key) {
        return BCrypt.hashpw(key, BCrypt.gensalt());
    }

    public boolean verify(String key, String hash) {
        return BCrypt.checkpw(key, hash);
    }
}