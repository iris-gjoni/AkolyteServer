import java.nio.channels.SocketChannel;

/**
 * Created by irisg on 14/04/2020.
 */
public class ClientRequest {

    private SocketChannel socketChannel;
    private String message;

    public ClientRequest(SocketChannel socketChannel, String message) {
        this.socketChannel = socketChannel;
        this.message = message;
    }

    public void reset(){
        this.socketChannel = null;
        this.message = null;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public String getMessage() {
        return message;
    }
}
