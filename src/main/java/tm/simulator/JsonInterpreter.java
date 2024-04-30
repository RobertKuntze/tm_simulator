package tm.simulator;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonInterpreter {
    JSONParser parser = new JSONParser();

    /*
     * Creates a JSON object from a list of State objects.
     * 
     * @param states The list of State objects to create a JSON object from.
     * @return The JSON object created from the list of State objects.
     */
    @SuppressWarnings("unchecked")
    public JSONObject createJson(List<State> states){
        JSONObject json = new JSONObject();
        for (State state : states) {
            JSONObject stateJson = new JSONObject();
            for (Relation relation : state.getRelations()) {
                JSONObject relationJson = new JSONObject();
                relationJson.put("currentState", relation.currentState.toString());
                relationJson.put("nextState", relation.nextState.toString());
                
                JSONObject inputCharacterJson = new JSONObject();
                for (int i = 0; i < relation.inputCharacter.size(); i++) {
                    inputCharacterJson.put(String.valueOf(i), relation.inputCharacter.get(i).toString());
                }

                relationJson.put("inputCharacter", inputCharacterJson);
                JSONObject tapeOutputsJson = new JSONObject();
                for (int i = 0; i < relation.tapeOutputs.size(); i++) {
                    tapeOutputsJson.put(String.valueOf(i), relation.tapeOutputs.get(i).toString());
                }
                relationJson.put("tapeOutputs", tapeOutputsJson);
                
                stateJson.put(relation.toString(), relationJson);
            }
            stateJson.put("isFinal", state.isFinal);
            stateJson.put("isStart", state.isStart);
            json.put(state.toString(), stateJson);
        }
        return json;
    }

    /*
     * Writes a JSON object to a file.
     * 
     * @param path The path to the file to write to.
     * @param json The JSON object to write to the file.
     */
    public void writeJson(String path, JSONObject json){
        try {
            FileWriter file = new FileWriter(path);
            file.write(json.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Reads a JSON file and returns a JSON object.
     * 
     * @param path The path to the JSON file.
     * @return The JSON object read from the file or null in case the path does not contain a json file.
     */
    public JSONObject readJson(String path){
        try {
            Reader reader = new FileReader(path);
            Object obj = parser.parse(reader);
            JSONObject readJson = (JSONObject) obj;
            return readJson;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    /**
     * Parses a JSON object and returns a list of State objects.
     *
     * @param json The JSON object to parse.
     * @return A list of State objects extracted from the JSON.
     */
    public List<State> getStatesFromJson(JSONObject json){
        List<State> result = new ArrayList<State>();
        State currentState = null;
        State nextState = null;
        List<Character> inputCharacter = new ArrayList<Character>();
        List<TapeOutput> tapeOutputs = new ArrayList<TapeOutput>();
        
        for (Object key : json.keySet()) {
            String stateName = (String) key;
            State state = new State(stateName);
            state.isFinal = (boolean) ((Map) json.get(key)).get("isFinal");
            state.isStart = (boolean) ((Map) json.get(key)).get("isStart");
            result.add(state);
        } for (Object key : json.keySet()) {
            JSONObject stateJson = (JSONObject) json.get(key);
            for (Object relationKey : stateJson.keySet()) {
                if (relationKey.equals("isFinal") || relationKey.equals("isStart")) {
                    continue;
                }
                JSONObject relationJson = (JSONObject) stateJson.get(relationKey);
                for (State state : result) {
                    if (state.name.equals(relationJson.get("currentState"))) {
                        currentState = state;
                    }
                    if (state.name.equals(relationJson.get("nextState"))) {
                        nextState = state;
                    }
                }
                for (Object inputCharacterKey : relationJson.keySet()) {
                    if (inputCharacterKey.equals("inputCharacter")) {
                        JSONObject inputCharacterJson = (JSONObject) relationJson.get(inputCharacterKey);
                        for (Object inputCharacterIndex : inputCharacterJson.keySet()) {
                            inputCharacter.add(inputCharacterJson.get(inputCharacterIndex).toString().charAt(0));
                        }
                    }
                }
                for (Object tapeOutputsKey : relationJson.keySet()) {
                    if (tapeOutputsKey.equals("tapeOutputs")) {
                        JSONObject tapeOutputsJson = (JSONObject) relationJson.get(tapeOutputsKey);
                        for (Object tapeOutputsIndex : tapeOutputsJson.keySet()) {
                            tapeOutputs.add(TapeOutput.fromString(tapeOutputsJson.get(tapeOutputsIndex).toString()));
                        }
                    }
                }
                if (currentState != null && nextState != null) {
                    Relation relation = new Relation(currentState, inputCharacter, nextState, tapeOutputs);
                    currentState.addRelation(relation);
                }
            }
        }
        return result;
    }
}
