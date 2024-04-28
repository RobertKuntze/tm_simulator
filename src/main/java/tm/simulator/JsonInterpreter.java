package tm.simulator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonInterpreter {
    JSONObject json = new JSONObject();
    JSONParser parser = new JSONParser();


    public void addState(State state) {
        json.put(state.toString(), state.getRelations().toString());
    }

    public void readJson() {
        try {
            Object obj = parser.parse("src/main/java/tm/simulator/data.json");
            JSONObject readJson = (JSONObject) obj;
            System.out.println(json.keySet() + "\n" + readJson.values());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
