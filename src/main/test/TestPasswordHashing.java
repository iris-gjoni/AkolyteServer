import ClientRequests.AuthenticationHandler;
import database.MongoDbConnector;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by irisg on 19/04/2020.
 */
public class TestPasswordHashing {

    int logRounds = 7;
    MongoDbConnector mongoDbConnector = new MongoDbConnector(27017);
    AuthenticationHandler authenticationHandler = new AuthenticationHandler(mongoDbConnector, logRounds);

    String[] mutableHash = new String[1];
    Function<String, Boolean> update = hash -> { mutableHash[0] = hash; return true; };

    @Test
    public void testHashing(){

        String pass = "password";

        String hashedPass = authenticationHandler.hash(pass);

        System.out.println(pass);
        System.out.println(hashedPass);

        boolean passed = authenticationHandler.verifyAndUpdateHash(pass, hashedPass, update);

        boolean passed2 = authenticationHandler.verifyHash(pass, hashedPass);

        boolean shouldFail = authenticationHandler.verifyHash("incorrect", hashedPass);

        System.out.println(passed+", " + passed2 + " " + shouldFail);

        assert passed && passed2 && !shouldFail;
    }


}
