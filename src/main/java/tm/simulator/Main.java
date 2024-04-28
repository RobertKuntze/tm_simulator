package tm.simulator;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        JsonInterpreter jsonInterpreter = new JsonInterpreter();

        State q0 = new State("q0", false, true);
        State q1 = new State("q1");
        State q2 = new State("q2");
        State q3 = new State("q3");
        State q4 = new State("q4");
        State q5 = new State("q5");
        State qf = new State("qf", true, false);


        q0.newRelation(q1, ' ', 'a', 'R');
        q1.newRelation(qf, ' ', 'b', 'N');
        q2.newRelation(q2, 'b', 'b', 'L');
        q2.newRelation(q3, 'a', 'a', 'R');
        q3.newRelation(q4, 'b', 'a', 'R');
        q4.newRelation(q4, 'b', 'b', 'R');
        q4.newRelation(q5, ' ', 'b', 'R');
        q5.newRelation(qf, ' ', 'b', 'N');
        qf.newRelation(q2, 'b', 'b', 'N');


        List<State> states = new ArrayList<State>();
        states.add(q0);
        states.add(q1);
        states.add(q2);
        states.add(q3);
        states.add(q4);
        states.add(q5);
        states.add(qf);

        // JSONObject json = jsonInterpreter.readJson("src/main/java/tm/simulator/data.json");
        // List<State> states = jsonInterpreter.getStatesFromJson(json);

        jsonInterpreter.writeJson("src/main/java/tm/simulator/data.json", jsonInterpreter.createJson(states));

        TuringMachine tm = new TuringMachine(states);

        tm.runAsGenerator(3);
    }
}