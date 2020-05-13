import ClientRequests.UserAccountProcesser;
import database.MongoDbConnector;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by irisg on 19/04/2020.
 */
public class TestUpdateAccount {

    @Test
    public void updateAccount(){

        MongoDbConnector connector = new MongoDbConnector(27017);
        UserAccountProcesser userAccountProcesser =
                new UserAccountProcesser(connector, 7);

        String email = "fake1@hotmail.co.uk";
        String name = "fake";
        String password = "password";

        final LocalDateTime now = LocalDateTime.now();

        int size = 7;
        String[] values = new String[size];
        String[] keys = new String[size];

        keys[0] = "email";
        keys[1] = "name";
        keys[2] = "password";
        keys[3] = "emailVerified";
        keys[4] = "permissions";
        keys[5] = "accountCreationDate";
        keys[6] = "lastPaymentDate";

        values[0] = email;
        values[1] = name;
        values[2] = userAccountProcesser.getAuthenticationHandler().hash(password);
        values[3] = "false";
        values[4] = "user";
        values[5] = now.toString();
        values[6] = "none";

        long startTime = System.currentTimeMillis();
        boolean result = userAccountProcesser.addToRecord(email, keys, values, size);
        System.out.println("time taken = " + (System.currentTimeMillis() - startTime) + "ms");

        assert result;
    }

}
