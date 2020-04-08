import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 29/03/2020.
 */
public class Main {

    private static final ExecutorService service = Executors.newSingleThreadExecutor();


    public static void main(String args[]) throws IOException {
        service.execute(new SocketAcceptor());
    }

    public static void init(SocketAcceptor socketAcceptor){

    }
}
