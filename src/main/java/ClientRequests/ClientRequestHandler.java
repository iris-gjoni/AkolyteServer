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

    private static final int logRounds = 7;
    private static final String splitter = "\\|";
    private final MongoDbConnector mongoDbConnector;
    private final AuthenticationHandler authenticationHandler;
    private final UserAccountProcesser accountCreationHandler;
    private final HashMap<String, String> extractedValues = new HashMap<>();
    private SocketChannel responseChannel;
    private final ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
    private final Logger logger = Logger.getLogger(ClientRequestHandler.class);

    public ClientRequestHandler(int port) {
        mongoDbConnector = new MongoDbConnector(port);
        authenticationHandler = new AuthenticationHandler(mongoDbConnector, logRounds);
        accountCreationHandler = new UserAccountProcesser(mongoDbConnector, logRounds);
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

    //expect NUA.email.name.password
    public void exctractNewUserAccountMessage(final String message) throws IOException {
        String[] newDataValues = message.split(splitter); // EXPECTING RQ1.iris.password
        int size = newDataValues.length;
        logger.info("size of newDataValues: " + size);

        boolean result;
        if (size == 4) {
            result = accountCreationHandler.CreateAccount(
                    newDataValues[1],
                    newDataValues[2],
                    newDataValues[3]);
        } else {
            result = false;
        }

        if(result){
            sendResponse("Successfully created new account".getBytes());
        } else {
            sendResponse("Failed to create account".getBytes());
        }

    }

    /* expect format RQ1.iris@hotmail.password */
    private void exctractLogonMessage(String message) throws IOException {
        String[] requestUserPass = message.split(splitter); // EXPECTING RQ1.iris.password
        logger.info(requestUserPass.length);
        /* temp printing for debugging */
        for (int i = 0; i < requestUserPass.length; i++) {
            logger.info(requestUserPass[i]);
        }
        if (requestUserPass.length == 3) { // validate the number of fields sent
            final boolean result = authenticationHandler.verifyLogonRequest(requestUserPass[1], requestUserPass[2]);
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
