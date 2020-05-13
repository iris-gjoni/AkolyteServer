import ClientRequests.AuthenticationHandler;
import database.MongoDbConnector;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by irisg on 19/04/2020.
 */
public class TestPasswordHashing {

    private int logRounds = 7;
    private MongoDbConnector mongoDbConnector = new MongoDbConnector(27017);
    private AuthenticationHandler authenticationHandler = new AuthenticationHandler(mongoDbConnector, logRounds);

    private String[] mutableHash = new String[1];
    private Function<String, Boolean> update = hash -> { mutableHash[0] = hash; return true; };

    @Test
    public void testHashing(){

        String pass = "password";

        long startTime = System.currentTimeMillis();
        String hashedPass = authenticationHandler.hash(pass);
        System.out.println("time taken = " + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println(pass);
        System.out.println(hashedPass);

        boolean passed = authenticationHandler.verifyAndUpdateHash(pass, hashedPass, update);

        boolean passed2 = authenticationHandler.verifyHash(pass, hashedPass);

        boolean shouldFail = authenticationHandler.verifyHash("incorrect", hashedPass);

        System.out.println(passed+", " + passed2 + " " + shouldFail);

        assert passed && passed2 && !shouldFail;
    }


}
