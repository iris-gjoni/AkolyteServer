package database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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

    public MongoDbConnector(){

    }

    public void connectToDb(){
        mongoClient = new MongoClient("localhost", 27017);
        System.out.println(mongoClient.toString());

        mongoDatabase = mongoClient.getDatabase("testDb");
        clients = mongoDatabase.getCollection("clients");

        System.out.println("connected to DataBase: " + mongoDatabase.getName());
    }

    public void disconnectToDb(){
        mongoClient.close();
    }

    public Document createOneDbDocument(HashMap<String, Object> map) {
        Document document = new Document();
        for (String key : map.keySet()){
            document.append(key, map.get(key));
        }
        return document;
    }

    private void writeOneDbDocument(Document document) {
            clients.insertOne(document);
            System.out.println("successfully wrote data");
    }

    public HashMap<String, String> readData(String user){
        connectToDb();

        Bson bson = new BasicDBObject("name", user);
        FindIterable<Document> findIterable = clients.find(bson);
        if(!findIterable.iterator().hasNext()){
            return helper.emptyMap();
        }

        return helper.extractData(findIterable.iterator().next().toString());
    }

    public void writeLoadedValues(){
        Document document = new Document();
        for (String key : valueMap.keySet()){
            document.append(key, valueMap.get(key));
        }
        writeOneDbDocument(document);
    }

    public boolean writeLoadedValuesAndClear(HashMap<String, String> passedInMap){
        connectToDb();
        try {
            Document document = new Document();
            passedInMap.forEach(document::append);
            writeOneDbDocument(document);
            disconnectToDb();
            return true;
        } catch (MongoWriteException e){
            e.printStackTrace();
            disconnectToDb();
            return false;
        }
    }


    public boolean writeLoadedValuesAndClear(){
        connectToDb();
        try {
            Document document = new Document();
            valueMap.forEach(document::append);
            writeOneDbDocument(document);
            clearMap();
            disconnectToDb();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            disconnectToDb();
            return false;
        }
    }


    public void addValue(final String key, final Object value){
        valueMap.put(key, value);
    }

    public void clearMap(){
        valueMap.clear();
    }


}
