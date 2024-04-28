package tm.simulator;

import java.io.FileReader;
import java.io.Reader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a tape
        Tape tape = new Tape(List.of('1', '0', '1', '1', '0', '1').stream().collect(Collectors.toCollection(ArrayList::new)));
        
        // Create states
        State q0 = new State("q0");
        State q1 = new State("q1");
        State q2 = new State("q2");
        State q3 = new State("q3");

        // Create relations
        Relation r0 = new Relation(q0, List.of('1'), q1, List.of(new TapeOutput('1', 'R')));
        Relation r1 = new Relation(q1, List.of('0'), q1, List.of(new TapeOutput('0', 'R')));
        Relation r2 = new Relation(q1, List.of('1'), q2, List.of(new TapeOutput('1', 'R')));
        Relation r3 = new Relation(q2, List.of('0'), q2, List.of(new TapeOutput('0', 'R')));
        Relation r4 = new Relation(q2, List.of('1'), q3, List.of(new TapeOutput('1', 'R')));

        // Add relations to states
        q0.addRelation(r0);
        q1.addRelation(r1);
        q1.addRelation(r2);
        q2.addRelation(r3);
        q2.addRelation(r4);

        // TuringMachine tm = new TuringMachine(q0, List.of(q3), List.of(tape));
        // tm.run();

        JsonInterpreter jsonInterpreter = new JsonInterpreter();
        jsonInterpreter.addState(q0);   
        jsonInterpreter.addState(q1);
        jsonInterpreter.addState(q2);
        jsonInterpreter.addState(q3);

        jsonInterpreter.readJson();
        
    }
}