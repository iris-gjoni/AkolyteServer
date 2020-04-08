package ClientRequests;

import database.MongoDbConnector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by irisg on 08/04/2020.
 */
public class AddNewDataRequestHandler {

    final private MongoDbConnector mongoDbConnector;


    public AddNewDataRequestHandler(final MongoDbConnector mongoDbConnector) {
        this.mongoDbConnector = mongoDbConnector;

    }

    public boolean AddDataToDB(HashMap<String, String> values){
        mongoDbConnector.connectToDb();
        boolean result =  mongoDbConnector.writeLoadedValuesAndClear(values);
        mongoDbConnector.disconnectToDb();
        return result;
    }
}
