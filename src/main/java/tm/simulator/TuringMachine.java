package tm.simulator;

import java.util.List;

public class TuringMachine {
    private State currentState;
    private List<State> finalStates;
    private List<Tape> tapes;
    
    public TuringMachine(State startState, List<State> finalStates, List<Tape> tapes) {
        this.currentState = startState;
        this.tapes = tapes;
        this.finalStates = finalStates;
    }

    public void run() {
        gameLoop:
        while (true) {
            System.out.println("Current state: " + currentState.name);
            for (Tape tape : tapes) {
                System.out.println("Tape: " + tape.toString());
            }
            if (currentState.getRelations().isEmpty()) {
                if (finalStates.contains(currentState)) {
                    System.out.println("Final state reached");
                } else {
                    System.out.println("No relations found for current state: " + currentState.name);
                }
                return;
            } 
            List<Character> inputCharacters = tapes.stream().map(Tape::read).toList();
            for (Relation relation : currentState.getRelations()) {
                if (relation.inputCharacter.equals(inputCharacters)) {
                    currentState = relation.nextState;
                    for (int i = 0; i < tapes.size(); i++) {
                        tapes.get(i).write(relation.tapeOutputs.get(i).character());
                        tapes.get(i).moveHead(relation.tapeOutputs.get(i).direction());
                        System.out.println("Used relation: " + relation.currentState.name + " -> "
                         + relation.nextState.name + " with tape output: " + relation.tapeOutputs.get(i).toString());
                    }
                    continue gameLoop;
                }
            }
            return;
        }
    }
}
