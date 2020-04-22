package database;

import org.apache.log4j.Logger;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.HashMap;

/**
 * Created by irisg on 07/04/2020.
 */
public class MongoHelper {

    private final Logger logger = Logger.getLogger(MongoHelper.class);


    public MongoHelper() {
    }

    public void extractData(final String s, HashMap<String, String> values){
        final int start = s.indexOf("_") + 1;
        final int end = s.length() - 2;
        String[] subStrings = s.substring(start, end).replaceAll(" ", "").split(",");

        String[] keyValue;
        for ( String ss : subStrings){
            keyValue = ss.split("=");
            values.put(keyValue[0], keyValue[1]);
        }
        logger.info("extracting data:");
        values.forEach((k,v)-> {
            logger.info(k + " , " + v);
        });

    }

    public HashMap<String, String> emptyMap(){
        return new HashMap<>();
    }





}
