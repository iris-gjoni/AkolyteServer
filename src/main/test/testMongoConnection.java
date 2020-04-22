import database.MongoDbConnector;
import org.junit.Test;

/**
 * Created by irisg on 06/04/2020.
 */
public class testMongoConnection {


    @Test
    public void test(){
        MongoDbConnector dbConnector = new MongoDbConnector(27017);
        dbConnector.connectToDb();


        dbConnector.addValue("name", "tony");
        dbConnector.addValue("password", "password");
        dbConnector.addValue("location", "USA");
        dbConnector.addValue("game", "WOW");

        dbConnector.writeLoadedValuesAndClear();
    }


    @Test
    public void test2(){
        MongoDbConnector dbConnector = new MongoDbConnector(27017);
        dbConnector.connectToDb();

        dbConnector.readByEmail("jeff");
    }

}
