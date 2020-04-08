package database;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.HashMap;

/**
 * Created by irisg on 07/04/2020.
 */
public class MongoHelper {

    public MongoHelper() {
    }

    public HashMap<String, String> extractData(final String s){
        final int start = s.indexOf("_") + 1;
        final int end = s.length() - 2;
        String[] subStrings = s.substring(start, end).replaceAll(" ", "").split(",");

            HashMap<String, String> values = new HashMap<>();

        String[] keyValue;
        for ( String ss : subStrings){
            keyValue = ss.split("=");
            values.put(keyValue[0], keyValue[1]);
        }
        values.forEach((k,v)-> {
            System.out.println(k + " , " + v);
        });

        return values;
    }



}
