import ClientRequests.ClientRequestHandler;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 29/03/2020.
 */
public class SocketAcceptor implements Runnable {

    public static final String LOGIN = "RQ1";
    public static final String ADD_NEW_DATA = "RQ2";
    private final Selector selector = Selector.open();
    private final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private boolean running = true;
    private final ClientRequestHandler clientRequestHandler = new ClientRequestHandler();

    /* only instantiated once as hard coded port */
    public SocketAcceptor() throws IOException {
        serverSocketChannel.bind(new InetSocketAddress("192.168.0.8", 1001));
    }

    /* can be used with different port numbers */
    public SocketAcceptor(int port) throws IOException {
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
    }


    @Override
    public void run() {
        while (running) {
            SocketChannel socketChannel;
            try {
                byteBuffer.clear();
                socketChannel = serverSocketChannel.accept();
                int bytesRead = socketChannel.read(byteBuffer);
                if (bytesRead > 0) {
                    handleMessage(socketChannel, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(SocketChannel socketChannel, int bytesRead) throws IOException {

        byteBuffer.flip();
        System.out.println("bytes read: " + bytesRead);
        String message = new String(byteBuffer.array(), 0, bytesRead);
        System.out.println(message);
        System.out.println(message.substring(0, 3));

        String requestType = message.substring(0, 3);

        if (requestType.equals(LOGIN)) {
            clientRequestHandler.handleLoginRequest(socketChannel, message);
        } else if (requestType.equals(ADD_NEW_DATA)){
            clientRequestHandler.handleAddNewDataRequest(socketChannel, message);
        }
    }

}
