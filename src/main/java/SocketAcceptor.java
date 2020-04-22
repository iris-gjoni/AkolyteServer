import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;


/**
 * Created by irisg on 29/03/2020.
 */
public class SocketAcceptor implements Runnable {

    private final Selector selector = Selector.open();
    private final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private Queue<ClientRequest> queue;
    private final Logger logger = Logger.getLogger(SocketAcceptor.class);


    /* only instantiated once as hard coded port */
    public SocketAcceptor(final Queue<ClientRequest> queue, final int port, final String host) throws IOException {
        serverSocketChannel.bind(new InetSocketAddress(host, port));
//        serverSocketChannel.configureBlocking(false);
        this.queue = queue;
        logger.info("<< Socket Acceptor Initialised >>");
    }

    /* can be used with different port numbers */
    public SocketAcceptor(int port) throws IOException {
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
    }


    @Override
    public void run() {
        boolean running = true;
        while (running) {
            SocketChannel socketChannel;
            try {
                byteBuffer.clear();
                socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    handleMessage(socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(SocketChannel socketChannel) throws IOException {

        int bytesRead = socketChannel.read(byteBuffer);

        byteBuffer.flip();
        logger.info("bytes read: " + bytesRead);
        String message = new String(byteBuffer.array(), 0, bytesRead);
        logger.info(message);
        logger.info("message type:" + message.substring(0, 3));

        queue.add(new ClientRequest(socketChannel, message));

    }

}