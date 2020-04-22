import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 29/03/2020.
 */
public class Main {

    private static final ExecutorService service = Executors.newCachedThreadPool();
    private static Queue<ClientRequest> queue;
    private static Config config = new Config();

    /* src/main/properties/config-dev.yaml */

    public static void main(String args[]) throws IOException {
        init(args[0]);
        queue = new ArrayBlockingQueue<>(config.getQueueCapacity());
        service.execute(new SocketAcceptor(queue,
                config.getSocketConnectPort(),
                config.getSocketConnectHost()));
        service.execute(new WorkerThread(queue, config.getMongoConnectPort()));
    }

    private static void init(final String configFilePath) {
        Yaml yaml = new Yaml(new Constructor(Config.class));
        System.out.println();
        try {
            config = yaml.load(new FileReader(configFilePath));
            System.setProperty("log4j.configuration", config.getLogFilePath());
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0); // dont start app if no config
        }
    }


}
