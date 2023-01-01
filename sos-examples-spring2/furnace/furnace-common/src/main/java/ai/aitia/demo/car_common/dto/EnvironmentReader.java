package ai.aitia.demo.car_common.dto;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class EnvironmentReader {


    public static String readField(String fieldName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + "/../../environment.json"));
        return String.valueOf(object.get(fieldName));
    }

    public static String readField(String fieldName, String relativePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") + relativePath));
        return String.valueOf(object.get(fieldName));
    }



}
