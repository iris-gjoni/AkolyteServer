import ClientRequests.UserAccountProcesser;
import database.MongoDbConnector;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 19/04/2020.
 */
public class TestCreateAccount {

    private static final ExecutorService service = Executors.newSingleThreadExecutor();
    private static final Queue<ClientRequest> queue = new ArrayBlockingQueue<>(10);



    @Test
    public void createAccount(){
        MongoDbConnector connector = new MongoDbConnector(27017);
        UserAccountProcesser userAccountProcesser =
                new UserAccountProcesser(connector, 7);

        String email = "matt@hotmail.co.uk";
        String name = "Anthony";
        String password = "password";

        assert userAccountProcesser.CreateAccount(email, name, password);
    }

    @Test
    public void testDoesAccountExist(){
        MongoDbConnector connector = new MongoDbConnector(27017);
        UserAccountProcesser userAccountProcesser =
                new UserAccountProcesser(connector, 7);

        assert userAccountProcesser.DoesAccountExist("irisgjoni@hotmail.co.uk");
    }
}
