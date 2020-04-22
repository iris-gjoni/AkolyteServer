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

    private static final ExecutorService service = Executors.newCachedThreadPool();
    private static final Queue<ClientRequest> queue = new ArrayBlockingQueue<>(10);
    private static final Logger logger = Logger.getLogger(TestSocketAcceptor.class);

    public TestSocketAcceptor() throws IOException {
        service.execute(new SocketAcceptor(queue, 1001, "192.168.0.8"));
        service.execute(new WorkerThread(queue, 27017));
    }

    @Test
    public void testLoginOnSocket() throws IOException {

        ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);
        byteBuffer.put("RQ1|irisgjoni@hotmail.co.uk|password".getBytes());

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("192.168.0.8", 1001));

        logger.info(socketChannel.getRemoteAddress() + " sending message: \n");
        logger.info(socketChannel.finishConnect());

        byteBuffer.flip();
        long startTime = System.currentTimeMillis();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();

        boolean waitResponse = true;
        while (waitResponse) {
            int bytesRead = socketChannel.read(byteBuffer);
            if (bytesRead > 0){
                waitResponse = false;
                String message = new String(byteBuffer.array(), 0, bytesRead);
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
            if (bytesRead > 0){
                waitResponse = false;
                String message = new String(byteBuffer.array(), 0, bytesRead);
                logger.info("message received: " + message);
            }
        }
        logger.info("time taken = " + (System.currentTimeMillis() - startTime));
        assert true;
    }

}
