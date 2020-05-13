import org.apache.log4j.Logger;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 27/04/2020.
 */
public class testSSLSocketAcceptor {

    private static final ExecutorService service = Executors.newCachedThreadPool();
    private static final ArrayBlockingQueue<ClientRequest> queue = new ArrayBlockingQueue<>(10);
    private static final Logger logger = Logger.getLogger(TestSocketAcceptor.class);
    private String keyStoreFilePath = "C:/dev/serverCA.jks";
    private String trustFilePath = "C:/dev/servertrustStore.jks";
    private String keyStorePass = "gjoniserver";
    private String trustStorePass = "gjoniserver";

    public testSSLSocketAcceptor() throws IOException {
        System.setProperty("javax.net.ssl.keyStore", keyStoreFilePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePass);
        System.setProperty("javax.net.ssl.trustStore", keyStoreFilePath);
        System.setProperty("javax.net.ssl.trustStorePassword", keyStorePass);

        AcceptThread acceptThread = new AcceptThread();
        service.execute(new WorkerThread(queue, 27017));
        service.execute(acceptThread);
    }

    private class AcceptThread implements Runnable {
        @Override
        public void run() {
            try {
//                System.setProperty("javax.net.ssl.keyStore", keyStoreFilePath);
//                System.setProperty("javax.net.ssl.keyStorePassword", keyStorePass);

                SSLServerSocketFactory sslsocketfactory =
                        (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                SSLServerSocket serverSocket = (SSLServerSocket) sslsocketfactory.createServerSocket();
                serverSocket.bind(new InetSocketAddress(
                        "192.168.0.8",
                        1001));
                while (true) {
                    Socket socket = serverSocket.accept();
                    SocketAcceptor socketAcceptor = new SocketAcceptor(
                            queue,
                            socket);
                    service.execute(socketAcceptor);
                }
            } catch (Exception e) {
                logger.error("failed");
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testLoginOnSocket() throws IOException {

        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory
                .createSocket("192.168.0.8", 1001);
        sslsocket.startHandshake();

        logger.info(sslsocket.getInetAddress() + " sending message: \n");

        long startTime = System.currentTimeMillis();
        sslsocket.getOutputStream().write("RQ1|irisgjoni@hotmail.co.uk|password".getBytes());

        boolean waitResponse = true;
        byte[] readBytes = new byte[1024];
        while (waitResponse) {
            int bytesRead = sslsocket.getInputStream().read(readBytes);
            if (bytesRead > 0) {
                waitResponse = false;
                String message = new String(readBytes, 0, bytesRead);
                logger.info("message received: " + message);
            }
        }
        logger.info("time taken = " + (System.currentTimeMillis() - startTime));
        assert true;
    }


    @Test
    public void testCreateAccountOnSocket() throws IOException {

        ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);
        byteBuffer.put("CNU|fake1@hotmail.co.uk|iris|fake".getBytes());

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("192.168.0.8", 1001));
        logger.info(socketChannel.getLocalAddress());
        logger.info(socketChannel.getRemoteAddress() + "\n\n sending message: \n");

        byteBuffer.flip();
        long startTime = System.currentTimeMillis();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();

        boolean waitResponse = true;
        while (waitResponse) {
            int bytesRead = socketChannel.read(byteBuffer);
            if (bytesRead > 0) {
                waitResponse = false;
                String message = new String(byteBuffer.array(), 0, bytesRead);
                logger.info("message received: " + message);
            }
        }
        logger.info("time taken = " + (System.currentTimeMillis() - startTime));
        assert true;
    }

    @Test
    public void testSSL() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, UnrecoverableKeyException, CertificateException {
//        KeyStore ks = KeyStore.getInstance("JKS");
//        ks.load(new FileInputStream(
//                new File(keyStoreFilePath)), keyStorePass.toCharArray());


        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory
                .createSocket("192.168.0.8", 1001);


        sslsocket.getOutputStream().write("RQ1|test|test".getBytes());

    }

}

