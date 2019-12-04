package api;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyWhitenerArgon2 implements KeyWhitener {

  private final Logger logger = LogManager.getLogger(UserStoreMem.class);
  private int argon2Iterations;
  private static final int argon2MemKiB = 65536;
  private static final int argon2Parallelism = 1;
  private static final int argon2MaxMs = 1000;

  public KeyWhitenerArgon2() {
    Argon2 argon2 = Argon2Factory.create();
    argon2Iterations =
        Argon2Helper.findIterations(argon2, argon2MaxMs, argon2MemKiB, argon2Parallelism);
    logger.info("Argon2 iterations {}", argon2Iterations);
  }

  public String hash(String key) {
    Argon2 argon2 = Argon2Factory.create();
    char[] keyArray = key.toCharArray();
    try {
      return argon2.hash(argon2Iterations, argon2MemKiB, argon2Parallelism, keyArray);
    } finally {
      argon2.wipeArray(keyArray);
    }
  }

  public boolean verify(String key, String hash) {
    Argon2 argon2 = Argon2Factory.create();
    char[] keyArray = key.toCharArray();
    try {
      return argon2.verify(hash, keyArray);
    } finally {
      argon2.wipeArray(keyArray);
    }
  }
}
