package ClientRequests;

import database.MongoDbConnector;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by irisg on 31/03/2020.
 */
public class LoginRequestHandler {

    final private MongoDbConnector mongoDbConnector;
    private final Logger logger = Logger.getLogger(LoginRequestHandler.class);


    public LoginRequestHandler(final MongoDbConnector mongoDbConnector) {
        this.mongoDbConnector = mongoDbConnector;

    }


    public boolean verifyLogonRequest(final String user, final String pass){
        HashMap<String, String> userData = mongoDbConnector.readData(user);
        final String loadedPass;
        if (userData != null) {
            if ((loadedPass = userData.get("password")) != null) {
                if (loadedPass.equals(pass)) {
                    return true;
                }
            }
            logger.info("readData returned null");
        }
        return false;
    }



}
