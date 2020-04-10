package ClientRequests;

import database.MongoDbConnector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irisg on 31/03/2020.
 */
public class ClientRequestHandler {

    public static final String splitter = "\\.";
    // need a mongoDB connection - spoofing for now
    private final MongoDbConnector mongoDbConnector = new MongoDbConnector();
    private final LoginRequestHandler loginRequestHandler = new LoginRequestHandler(mongoDbConnector);
    private final AddNewDataRequestHandler newDataRequestHandler = new AddNewDataRequestHandler(mongoDbConnector);
    private final HashMap<String, String> extractedValues = new HashMap<>();
    private SocketChannel responseChannel;
    private final ByteBuffer responseBuffer = ByteBuffer.allocate(1024);

    public ClientRequestHandler() {

    }



    public void handleLoginRequest(final SocketChannel socketChannel, final String message) {
        this.responseChannel = socketChannel;
        try {
            exctractLogonMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* expect format RQ2.name.name.password.password.other.n.n2... */
    public void handleAddNewDataRequest(final SocketChannel socketChannel, final String message) {
        this.responseChannel = socketChannel;
        try {
            exctractNewDataMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exctractNewDataMessage(final String message) throws IOException {
        String[] newDataValues = message.split(splitter); // EXPECTING RQ1.iris.password
        int size = newDataValues.length;
        System.out.println("size of newDataValues: " + size);
        clearValueMap();

        if ((size % 2 )!= 0) { // not even
            for (int i = 1; i < size; i = i + 2) {
                // read 2 and 3 as k, v
                extractedValues.put(newDataValues[i], newDataValues[i + 1]);
                System.out.println(newDataValues[i] + ", " + newDataValues[i+1]);
            }
        }
        final boolean result = newDataRequestHandler.AddDataToDB(extractedValues);


        if(result){
            sendResponse("Successfully Added Data".getBytes());
        } else {
            sendResponse("Failed To Add Data".getBytes());
        }

    }

    /* expect format RQ1.iris.password */
    private void exctractLogonMessage(String message) throws IOException {
        String[] requestUserPass = message.split(splitter); // EXPECTING RQ1.iris.password
        System.out.println(requestUserPass.length);
        /* temp printing for debugging */
        for (int i = 0; i < requestUserPass.length; i++) {
            System.out.println(requestUserPass[i]);
        }
        if (requestUserPass.length == 3) { // validate the number of fields sent
            final boolean result = loginRequestHandler.verifyLogonRequest(requestUserPass[1], requestUserPass[2]);
            System.out.println("result=" + result);
            if (result) {
                System.out.println("logged in");
                sendResponse("loggedOn".getBytes());
            } else {
                System.out.println("failed login");
                sendResponse("Failed Authentication".getBytes());
            }

        }
    }

    private void sendResponse(byte[] bytes) throws IOException {
        responseBuffer.clear();
        responseBuffer.put(bytes);
        responseBuffer.flip();
        responseChannel.write(responseBuffer);
    }

    public void clearValueMap(){
        this.extractedValues.clear();
    }
}
