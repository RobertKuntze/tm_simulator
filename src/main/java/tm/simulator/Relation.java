package tm.simulator;

import java.util.List;

public class Relation {
    public final State currentState;
    public final List<Character> inputCharacter;

    public final State nextState;
    public final List<TapeOutput> tapeOutputs;

    public Relation(State currentState, List<Character> inputCharacter, State nextState, List<TapeOutput> tapeOutputs) {
        this.currentState = currentState;
        this.inputCharacter = inputCharacter;
        this.nextState = nextState;
        this.tapeOutputs = tapeOutputs;
    }

    public String toString() {
        return currentState + " -> " + nextState + " : " + inputCharacter + " -> " + tapeOutputs;
    }
}
