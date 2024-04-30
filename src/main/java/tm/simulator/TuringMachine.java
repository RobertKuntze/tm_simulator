package tm.simulator;

import java.util.ArrayList;
import java.util.List;

public class TuringMachine {
    private State currentState;
    private List<State> finalStates;
    private List<Tape> tapes;
    
    public TuringMachine(State startState, List<State> finalStates) {
        this.currentState = startState;
        this.finalStates = finalStates;
    }

    public TuringMachine(List<State> states) {
        this.currentState = states.stream().filter(r -> r.isStart).findFirst().get();
        this.finalStates = states.stream().filter(state -> state.isFinal).toList();
    }

    /*
     * Runs the Turing machine as an acceptor.
     * 
     * @param tapes The tapes to run the Turing machine on.
     */
    public boolean runAsAcceptor(List<Tape> tapes) {
        this.tapes = tapes;
        gameLoop:
        while (true) {
            // prints the current state
            System.out.println("Current state: " + currentState.name);

            // prints all tapes
            for (Tape tape : tapes) {
                System.out.println("Tape: " + tape.toString());
            }

            // checks if there are no more steps possible, if at final state accept, else reject
            if (currentState.getRelations().isEmpty()) {
                if (finalStates.contains(currentState)) {
                    System.out.println("Final state reached");
                    return true;
                } else {
                    System.out.println("No relations found for current state: " + currentState.name);
                    return false;
                }            
            }

            // reads the input characters from the tapes, and tries to find a relation for the input characters
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
                    // if a relation is found the game loop continues
                    continue gameLoop;
                }
            }
            //if no relation is found the input is rejected
            System.out.println("No relation found for input characters: " + inputCharacters);   
            return false;
        }
    }

    /*
     * Runs the Turing machine as a generator.
     * 
     * @param numberOfWords The number of words to generate.
     */
    public void runAsGenerator(int numberOfWords) {
        //checks if there is exactly one final state to generate words at
        if (finalStates.size() != 1) {
            System.out.println("There must be exactly one final state for a generator Turing machine");
            return;
        }
        //prints all tapes
        this.tapes = new ArrayList<Tape>();
        for (int i = 0; i < currentState.getRelations().get(0).tapeOutputs.size(); i++) {
            tapes.add(new Tape(new ArrayList<Character>()));
        }
        int wordsGenerated = 0;
        while (true) {
            // System.out.println("Current state: " + currentState);
            for (Tape tape : tapes) {
                // System.out.println("Tape: " + tape.toString());
            }
            if (currentState.getRelations().isEmpty()) {
                // System.out.println("No relations found for current state: " + currentState.name);
                return;
            }
            //checks if the current state is a final state, if so a word is generated and printed
            if (currentState.isFinal) {
                System.out.println("Word generated: " + tapes.get(0).toString());
                wordsGenerated++;
                if (wordsGenerated >= numberOfWords) {
                    return;
                }
            }
            //reads the input characters from the tapes, and tries to find a relation for the input characters
            List<Character> inputCharacters = tapes.stream().map(Tape::read).toList();
            for (Relation relation : currentState.getRelations()) {
                if (inputCharacters.equals(relation.inputCharacter)) {
                    currentState = relation.nextState;
                    for (int i = 0; i < tapes.size(); i++) {
                        tapes.get(i).write(relation.tapeOutputs.get(i).character());
                        tapes.get(i).moveHead(relation.tapeOutputs.get(i).direction());
                    }
                }
            }
        }
    }
}
