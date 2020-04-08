import database.MongoDbConnector;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by irisg on 06/04/2020.
 */
public class testMongoConnection {


    @Test
    public void test(){
        MongoDbConnector dbConnector = new MongoDbConnector();
        dbConnector.connectToDb();


        dbConnector.addValue("name", "tony");
        dbConnector.addValue("password", "password");
        dbConnector.addValue("location", "USA");
        dbConnector.addValue("game", "WOW");

        dbConnector.writeLoadedValuesAndClear();
    }


    @Test
    public void test2(){
        MongoDbConnector dbConnector = new MongoDbConnector();
        dbConnector.connectToDb();

        dbConnector.readData("jeff");
    }

}
