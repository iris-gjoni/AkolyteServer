import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 31/03/2020.
 */
public class TestSocketAcceptor {

    private static final ExecutorService service = Executors.newCachedThreadPool();
    private static final ArrayBlockingQueue<ClientRequest> queue = new ArrayBlockingQueue<>(10);
    private static final Logger logger = Logger.getLogger(TestSocketAcceptor.class);

    public TestSocketAcceptor() throws IOException {
        AcceptThread acceptThread = new AcceptThread();
        service.execute(new WorkerThread(queue, 27017));
        service.execute(acceptThread);
    }

    private class AcceptThread implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket();
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

            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("192.168.0.8", 1001), 1000);

            logger.info(socket.getInetAddress() + " sending message: \n");

            long startTime = System.currentTimeMillis();
            socket.getOutputStream().write("RQ1|irisgjoni@hotmail.co.uk|password".getBytes());

            boolean waitResponse = true;
            byte[] readBytes = new byte[1024];
            while (waitResponse) {
                int bytesRead = socket.getInputStream().read(readBytes);
                if (bytesRead > 0) {
                    waitResponse = false;
                    String message = new String(readBytes, 0, bytesRead);
                    logger.info("message received: " + message);
                }
            }
            logger.info("time taken = " + (System.currentTimeMillis() - startTime) + "ms");
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
            logger.info("time taken = " + (System.currentTimeMillis() - startTime) + "ms");
            assert true;
        }

    }
