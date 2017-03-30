package parsing.json;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by dusky on 3/2/17.
 */
public class JsonParsing {

    private static final String inputFile = "jsonTestFile.json";

    public static void streamApi() throws FileNotFoundException {
        JsonParser parser = null;
        InputStream targetStream = null;
        try {
            System.out.println("================");
            System.out.println("Stream Api");
            System.out.println("================");
            File initialFile = new File(inputFile);
            targetStream = new FileInputStream(initialFile);
            parser = Json.createParser(targetStream);

            while (parser.hasNext()) {
                switch (parser.next()) {
                    case KEY_NAME:
                        String key = parser.getString();
                        switch (key) {
                            case "firstname":
                                parser.next();
                                String firstname = parser.getString();
                                System.out.print("firstname: " + firstname + ", ");
                                break;
                            case "id":
                                parser.next();
                                int id = parser.getInt();
                                System.out.print("id: " + id + ", ");
                                break;
                            case "languages":
                                System.out.print("languages: ");
                                break;
                            case "lang":
                                parser.next();
                                String lang = parser.getString();
                                System.out.print("lang: " + lang + ", ");
                                break;
                            case "knowledge":
                                parser.next();
                                String knowledge = parser.getString();
                                System.out.print("knowledge: " + knowledge);
                                break;
                            case "job":
                                System.out.print("job: ");
                                break;
                            case "site":
                                parser.next();
                                String site = parser.getString();
                                System.out.print("site: " + site + ", ");
                                break;
                            case "name":
                                parser.next();
                                String name = parser.getString();
                                System.out.print("name: " + name);
                                break;
                            default:
                                break;
                        }
                        break;
                    case START_ARRAY:
                        System.out.print("[");
                        break;
                    case END_ARRAY:
                        System.out.print("], ");
                        break;
                    case START_OBJECT:
                        System.out.print("{");
                        break;
                    case END_OBJECT:
                        System.out.print("}, ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
            System.out.println("================");
        }finally {

        }
    }

    public static void objectModelApi() throws FileNotFoundException {
        JsonReader jsonReader = null;
        InputStream targetStream = null;
        try {
            System.out.println("================");
            System.out.println("Object Model Api");
            System.out.println("================");
            File initialFile = new File(inputFile);
            targetStream = new FileInputStream(initialFile);
            Reader reader = new InputStreamReader( targetStream , StandardCharsets.UTF_8);


            jsonReader = Json.createReader(reader);
            JsonObject jsonObject = jsonReader.readObject();
            System.out.println(jsonObject);
            System.out.println("================");

            int id = jsonObject.getInt("id");
            System.out.println("id: " + id);
            String firstname = jsonObject.getString("firstname");
            System.out.println("firstname: " + firstname);
            JsonArray languages = jsonObject.getJsonArray("languages");
            System.out.print("languages: ");

            for (JsonValue value: languages){
                System.out.print("{");
                JsonObject obj = (JsonObject) value;
                String lang = obj.getString("lang");
                System.out.print("lang: " + lang);
                String knowledge = obj.getString("knowledge");
                System.out.print("knowledge: " + knowledge);
                System.out.print("}, ");
            }
            System.out.println();

            JsonObject job = jsonObject.getJsonObject("job");

            String site = job.getString("site");
            String name = job.getString("name");
            System.out.println("job: {site: " + site + ", " + "name: " + name + "}");
            System.out.println("================");
        } finally {
            if (jsonReader != null) {
                jsonReader.close();
            }
            if (targetStream != null){
                try {
                    targetStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
