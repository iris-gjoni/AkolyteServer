import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 31/03/2020.
 */
public class TestSocketAcceptor {

    private static final ExecutorService service = Executors.newSingleThreadExecutor();
    private static final Queue<ClientRequest> queue = new ArrayBlockingQueue<>(10);
    private static final Logger logger = Logger.getLogger(TestSocketAcceptor.class);

    public TestSocketAcceptor() throws IOException {
        service.execute(new SocketAcceptor(queue));
    }

    @Test
    public void testLogin() throws IOException {

        ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);
        byteBuffer.put("RQ1.jeff.password".getBytes());

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("192.168.0.8", 1001));
        System.out.println(socketChannel.getLocalAddress());
        System.out.println(socketChannel.getRemoteAddress() + "\n\n sending message: \n");
//        Socket socket = socketChannel.socket();

        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        // ready for use in the read.
        byteBuffer.clear();

        boolean waitResponse = true;

        while (waitResponse) {
            int bytesRead = socketChannel.read(byteBuffer);
            if (bytesRead > 0){
                String message = new String(byteBuffer.array()).trim();
                System.out.println("message received: " + message);
            } else {
                System.out.println("failed to decode");
            }
            waitResponse = false;
        }
        assert true;
    }

    @Test
    public void testAddData() throws IOException {

        ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);
        byteBuffer.put("RQ2.name.gerry.password.password.location.singapore.game.COD".getBytes());

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("192.168.0.8", 1001));
        System.out.println(socketChannel.getLocalAddress());
        System.out.println(socketChannel.getRemoteAddress() + "\n\n sending message: \n");
//        Socket socket = socketChannel.socket();

        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        // ready for use in the read.
        byteBuffer.clear();

        boolean waitResponse = true;

        while (waitResponse) {
            int bytesRead = socketChannel.read(byteBuffer);
            if (bytesRead > 0){
                String message = ASCIIUtility.toString(byteBuffer.array(),0, bytesRead);
                System.out.println("message received: " + message);
            } else {
                System.out.println("failed to decode");
            }
            waitResponse = false;
        }
        assert true;
    }

}
