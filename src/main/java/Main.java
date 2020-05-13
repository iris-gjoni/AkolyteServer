import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 29/03/2020.
 */
public class Main {

    private static final ExecutorService service = Executors.newCachedThreadPool();
    private static ArrayBlockingQueue<ClientRequest> queue;
    private static Config config = new Config();
    private static SSLServerSocket sslServerSocket;
    private static SSLServerSocketFactory sslsocketfactory;
    private static ServerSocket serverSocket;



    /* src/main/properties/config-dev.yaml */

    public static void main(String args[]) throws IOException {
        init(args[0]);
        queue = new ArrayBlockingQueue<>(config.getQueueCapacity());
        service.execute(new WorkerThread(queue, config.getMongoConnectPort()));
        try {
            acceptSocket(
                    config.getKeyStoreFilePath(),
                    config.getKeyStorePassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init(final String configFilePath) {
        final Yaml yaml = new Yaml(new Constructor(Config.class));
        System.out.println();
        try {
            config = yaml.load(new FileReader(configFilePath));
            System.setProperty("log4j.configuration", config.getLogFilePath());
            System.setProperty("javax.net.ssl.keyStore", config.getKeyStoreFilePath());
            System.setProperty("javax.net.ssl.keyStorePassword", config.getKeyStorePassword());
            System.setProperty("javax.net.ssl.trustStore", config.getTrustFilePath());
            System.setProperty("javax.net.ssl.trustStorePassword", config.getTrustStorePassword());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0); // dont start app if no config
        }
    }


    private static void acceptSocket(final String keyStoreFilePath, final String keyStorePass)
            throws IOException,
            KeyStoreException,
            CertificateException,
            NoSuchAlgorithmException,
            UnrecoverableKeyException,
            KeyManagementException {
        if (config.isUseSSL()) {
            sslsocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sslServerSocket = (SSLServerSocket) sslsocketfactory.createServerSocket();
            sslServerSocket.bind(new InetSocketAddress(
                    config.getSocketConnectHost(),
                    config.getSocketConnectPort()));
            while (true) {
                SSLAcceptLoop();
            }
        } else {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(
                    config.getSocketConnectHost(),
                    config.getSocketConnectPort()));
            while (true) {
                AcceptLoop();
            }
        }
    }

    private static void AcceptLoop() throws IOException {
        Socket socket = serverSocket.accept();
        SocketAcceptor socketAcceptor = new SocketAcceptor(
                queue,
                socket);
        service.execute(socketAcceptor);
    }

    private static void SSLAcceptLoop() throws IOException {
        Socket socket = sslServerSocket.accept();
        SSLSocketAcceptor sslSocketAcceptor = new SSLSocketAcceptor(
                queue,
                socket);
        service.execute(sslSocketAcceptor);
    }


}
