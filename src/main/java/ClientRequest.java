import java.net.Socket;

/**
 * Created by irisg on 14/04/2020.
 */
public class ClientRequest {

    private Socket socket;
    private String message;
    private long startTime;

    public ClientRequest(final Socket socketChannel,final String message, final long startTime) {
        this.socket = socketChannel;
        this.message = message;
        this.startTime = startTime;
    }

    public void reset(){
        this.socket = null;
        this.message = null;
    }

    public long getStartTime() {
        return startTime;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getMessage() {
        return message;
    }
}
