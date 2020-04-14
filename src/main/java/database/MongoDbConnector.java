package database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.*;
import org.bson.conversions.Bson;

import java.util.HashMap;

/**
 * Created by irisg on 06/04/2020.
 */
public class MongoDbConnector {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> clients;
    private HashMap<String, Object> valueMap = new HashMap<>();
    private MongoHelper helper = new MongoHelper();
    private final Logger logger = Logger.getLogger(MongoDbConnector.class);

    public MongoDbConnector() {
        connectToDb();
    }

    public void connectToDb() {
        mongoClient = new MongoClient("localhost", 27017);
        logger.debug(mongoClient.toString());

        mongoDatabase = mongoClient.getDatabase("testDb");
        clients = mongoDatabase.getCollection("clients");

        logger.info("connected to DataBase: " + mongoDatabase.getName());
    }

    public void disconnectToDb() {
        mongoClient.close();
    }

    public Document createOneDbDocument(HashMap<String, Object> map) {
        Document document = new Document();
        for (String key : map.keySet()) {
            document.append(key, map.get(key));
        }
        return document;
    }

    private void writeOneDbDocument(Document document) {
        clients.insertOne(document);
        logger.info("successfully wrote data");
    }

    public HashMap<String, String> readData(String user) {
        Bson bson = new BasicDBObject("name", user);
        FindIterable<Document> findIterable = clients.find(bson);
        if (!findIterable.iterator().hasNext()) {
            return helper.emptyMap();
        }

        return helper.extractData(findIterable.iterator().next().toString());
    }

    public void writeLoadedValues() {
        Document document = new Document();
        for (String key : valueMap.keySet()) {
            document.append(key, valueMap.get(key));
        }
        writeOneDbDocument(document);
    }

    public boolean writeLoadedValuesAndClear(HashMap<String, String> passedInMap) {
        try {
            Document document = new Document();
            passedInMap.forEach(document::append);
            writeOneDbDocument(document);
            return true;
        } catch (MongoWriteException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean writeLoadedValuesAndClear() {
        try {
            Document document = new Document();
            valueMap.forEach(document::append);
            writeOneDbDocument(document);
            clearMap();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void addValue(final String key, final Object value) {
        valueMap.put(key, value);
    }

    public void clearMap() {
        valueMap.clear();
    }


}
