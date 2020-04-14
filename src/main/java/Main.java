import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 29/03/2020.
 */
public class Main {

    private static final ExecutorService service = Executors.newCachedThreadPool();
    private static final int CAPACITY = 1000000;
    private static final int TEST = 10;
    private static final Queue<ClientRequest> queue = new ArrayBlockingQueue<>(TEST);


    public static void main(String args[]) throws IOException {
        init();
        service.execute(new SocketAcceptor(queue));
        service.execute(new WorkerThread(queue));
    }

    private static void init() {
        System.setProperty("log4j.configuration", "file:///C:/workspace/major1/src/companyApp/src/main/properties/log4j.xml");
    }


}
