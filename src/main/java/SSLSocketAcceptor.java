import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by irisg on 23/04/2020.
 */
public class SSLSocketAcceptor implements Runnable {

    private static final int ONE_KB = 1024;
    private final Queue<ClientRequest> queue;
    private final Logger logger = Logger.getLogger(SocketAcceptor.class);
    private final Socket sslSocket;

    /* only instantiated once as hard coded port */
    public SSLSocketAcceptor(final ArrayBlockingQueue<ClientRequest> queue,
                             final Socket socket) throws IOException {
        this.sslSocket = socket;
        this.queue = queue;
        logger.info("<< Socket Acceptor Initialised >>");
    }

    @Override
    public void run() {
        try {
            if (sslSocket != null) {
                handleMessage(sslSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(final Socket socket) throws IOException {
        byte[] readBytes = new byte[ONE_KB];
        int bytesRead = socket.getInputStream().read(readBytes);
        logger.info("bytes read: " + bytesRead);

        if (bytesRead > 0) {
            final long startTime = System.currentTimeMillis();
            String message = new String(readBytes, 0, bytesRead);
            logger.info(message);
            logger.info("message type:" + message.substring(0, 3));
            queue.add(new ClientRequest(socket, message, startTime));
        } else {
            logger.error("0 bytes read from the socket");
        }
    }
}
