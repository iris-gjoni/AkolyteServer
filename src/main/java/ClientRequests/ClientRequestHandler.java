package ClientRequests;

import database.MongoDbConnector;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * Created by irisg on 31/03/2020.
 */
public class ClientRequestHandler {

    public static final String splitter = "\\.";
    private final MongoDbConnector mongoDbConnector = new MongoDbConnector();
    private final LoginRequestHandler loginRequestHandler = new LoginRequestHandler(mongoDbConnector);
    private final AddNewDataRequestHandler newDataRequestHandler = new AddNewDataRequestHandler(mongoDbConnector);
    private final HashMap<String, String> extractedValues = new HashMap<>();
    private SocketChannel responseChannel;
    private final ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
    private final Logger logger = Logger.getLogger(ClientRequestHandler.class);

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
    public void handleNewUserAccountRequest(final SocketChannel socketChannel, final String message) {
        this.responseChannel = socketChannel;
        try {
            exctractNewUserAccountMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exctractNewUserAccountMessage(final String message) throws IOException {
        String[] newDataValues = message.split(splitter); // EXPECTING RQ1.iris.password
        int size = newDataValues.length;
        logger.info("size of newDataValues: " + size);
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
        logger.info(requestUserPass.length);
        /* temp printing for debugging */
        for (int i = 0; i < requestUserPass.length; i++) {
            logger.info(requestUserPass[i]);
        }
        if (requestUserPass.length == 3) { // validate the number of fields sent
            final boolean result = loginRequestHandler.verifyLogonRequest(requestUserPass[1], requestUserPass[2]);
            logger.info("result=" + result);
            if (result) {
                logger.info("logged in\n\n");
                sendResponse("loggedOn".getBytes());
            } else {
                logger.info("failed login\n\n");
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

    /* in order to control who is able to create a Trainer account we will create the accounts on our side and give them a login
    * the trainers will then open the app, login with the default login and be prompted to update their account info*/
    public void handleUpdateAccountRequest(SocketChannel socketChannel, String message) {

    }

    public void handleLoadAvaibleSlotsRequest(SocketChannel socketChannel, String message) {

    }

    public void handleBookSlotRequest(SocketChannel socketChannel, String message) {

    }

    public void handlePaymentComplete(SocketChannel socketChannel, String message) {

    }

}
