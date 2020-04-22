import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;


/**
 * Created by irisg on 22/04/2020.
 */
public class TestConfig {


    @Test
    public void testDev(){

        Yaml yaml = new Yaml(new Constructor(Config.class));
        try {
            Config config = yaml.load(new FileReader("src/main/properties/config-dev.yaml"));
            System.out.println(config.toString().replaceAll(",", "\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUAT(){

        Yaml yaml = new Yaml(new Constructor(Config.class));
        try {
            Config config = yaml.load(new FileReader("src/main/properties/config-test.yaml"));
            System.out.println(config.toString().replaceAll(",", "\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testProd(){

        Yaml yaml = new Yaml(new Constructor(Config.class));
        try {
            Config config = yaml.load(new FileReader("src/main/properties/config-prod.yaml"));
            System.out.println(config.toString().replaceAll(",", "\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
