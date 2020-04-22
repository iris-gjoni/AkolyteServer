package ClientRequests;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import database.MongoDbConnector;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.function.Function;

/**
 * Created by irisg on 31/03/2020.
 */
public class AuthenticationHandler {

    final private MongoDbConnector mongoDbConnector;
    private final Logger logger = Logger.getLogger(AuthenticationHandler.class);
    private int logRounds;

    public AuthenticationHandler(MongoDbConnector mongoDbConnector, int logRounds) {
        this.mongoDbConnector = mongoDbConnector;
        this.logRounds = logRounds;
    }

    boolean verifyLogonRequest(final String email, final String pass) {
        HashMap<String, String> userData = mongoDbConnector.readByEmail(email);
        if (userData != null) {
            if (verifyHash(pass, userData.get("password"))) {
                return true;
            }
            logger.info("readByEmail returned null");
        }
        logger.info("failed to match hashes");
        return false;
    }

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        if (BCrypt.checkpw(password, hash)) {
            int rounds = getRounds(hash);
            // It might be smart to only allow increasing the rounds.
            // If someone makes a mistake the ability to undo it would be nice though.
            if (rounds != logRounds) {
                logger.info("Updating password from " + rounds + " rounds to " + logRounds);
                String newHash = hash(password);
                return updateFunc.apply(newHash);
            }
            return true;
        }
        return false;
    }

    private int getRounds(String salt) {
        char minor = (char) 0;
        int off = 0;

        if (salt.charAt(0) != '$' || salt.charAt(1) != '2')
            throw new IllegalArgumentException("Invalid salt version");
        if (salt.charAt(2) == '$')
            off = 3;
        else {
            minor = salt.charAt(2);
            if (minor != 'a' || salt.charAt(3) != '$')
                throw new IllegalArgumentException("Invalid salt revision");
            off = 4;
        }

        // Extract number of rounds
        if (salt.charAt(off + 2) > '$')
            throw new IllegalArgumentException("Missing salt rounds");
        return Integer.parseInt(salt.substring(off, off + 2));
    }

}
