import java.net.Socket;

/**
 * Created by irisg on 14/04/2020.
 */
public class ClientRequest {

    private Socket socket;
    private String message;

    public ClientRequest(Socket socketChannel, String message) {
        this.socket = socketChannel;
        this.message = message;
    }

    public void reset(){
        this.socket = null;
        this.message = null;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getMessage() {
        return message;
    }
}
