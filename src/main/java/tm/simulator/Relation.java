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

    public Relation(State currentState, Character inputCharacter, State nextState, Character tapeOutput, Character move) {
        this.currentState = currentState;
        this.inputCharacter = List.of(inputCharacter);
        this.nextState = nextState;
        this.tapeOutputs = List.of(new TapeOutput(tapeOutput, move));
    }

    public String toString() {
        return currentState + " -> " + nextState + " : " + inputCharacter + " -> " + tapeOutputs;
    }
}
