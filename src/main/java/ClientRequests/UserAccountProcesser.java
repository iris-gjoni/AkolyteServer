package ClientRequests;

import database.MongoDbConnector;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by irisg on 19/04/2020.
 */
public class UserAccountProcesser {

    private final MongoDbConnector mongoDbConnector;
    private final HashMap<String, String> mapValues = new HashMap<>();
    private final AuthenticationHandler authenticationHandler;

    public UserAccountProcesser(final MongoDbConnector mongoDbConnector, final int logRounds) {
        this.mongoDbConnector = mongoDbConnector;
        authenticationHandler = new AuthenticationHandler(mongoDbConnector, logRounds);
    }

    public boolean CreateAccount(final String email, final String name, final String password){
        if (DoesAccountExist(email)){
            return false;
        }

        final LocalDateTime now = LocalDateTime.now();
        HashMap<String, String> values = new HashMap<>();
        values.put("email", email);
        values.put("name", name);
        values.put("password", authenticationHandler.hash(password));
        values.put("emailVerified", "false");
        values.put("permissions", "user");
        values.put("accountCreationDate", now.toString());
        values.put("lastPaymentDate", "none");

        mongoDbConnector.writeLoadedValuesAndClear(values);
        addTokensToRecord(email, 0);

        return true;
    }

    public boolean DoesAccountExist(final String email){
        if ( mongoDbConnector.readByEmail(email).isEmpty() ){
            return false; //account exists
        } else {
            return true;
        }
    }

    public boolean addToRecord(String email, String[] keys, String[] values, int length){
        mapValues.clear();
        for ( int i = 0; i < length; i++) {
            mapValues.put(keys[i], values[i]);
        }
        return mongoDbConnector.updateRecord(email, mapValues);
    }

    private boolean addTokensToRecord(String email, int value){
        return mongoDbConnector.updateTokens(email, value);
    }

    public AuthenticationHandler getAuthenticationHandler() {
        return authenticationHandler;
    }
}
