package ai.aitia.demo.car_common.dto;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EnvironmentWriter {


    public static void writeDoubleField(String fieldName, Double value) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/../../environment.json"));
        object.remove(fieldName);
        object.put(fieldName, value);
        try (FileWriter fw = new FileWriter(System.getProperty("user.dir") + "/../../environment.json") ) {
            fw.write(object.toJSONString());
        }
    }

}
