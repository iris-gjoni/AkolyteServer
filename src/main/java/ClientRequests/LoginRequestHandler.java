package ClientRequests;

import database.MongoDbConnector;
import java.util.HashMap;

/**
 * Created by irisg on 31/03/2020.
 */
public class LoginRequestHandler {

    final private MongoDbConnector mongoDbConnector;

    public LoginRequestHandler(final MongoDbConnector mongoDbConnector) {
        this.mongoDbConnector = mongoDbConnector;

    }


    public boolean verifyLogonRequest(final String user, final String pass){
        HashMap<String, String> userData = mongoDbConnector.readData(user);
        final String loadedPass;
        if((loadedPass = userData.get("password") )!= null){
            if (loadedPass.equals(pass)){
                mongoDbConnector.disconnectToDb();
                return true;
            }
        }
        mongoDbConnector.disconnectToDb();
        return false;
    }



}
