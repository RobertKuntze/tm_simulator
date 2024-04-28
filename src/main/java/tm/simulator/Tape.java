package tm.simulator;

import java.util.List;

public class Tape {
    private List<Character> tape;
    private int headPosition;

    public Tape(List<Character> tape) {
        this.tape = tape;
        this.headPosition = 0;
    }

    public Character read() {
        if (tape.size() <= headPosition) {
            tape.add(' ');
        }
        return tape.get(headPosition);
    }

    public void write(Character character) {
        tape.set(headPosition, character);
    }

    public void moveHead(Character direction) {
        if (direction == 'L') {
            headPosition--;
        } else if (direction == 'R') {
            headPosition++;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tape.size(); i++) {
            if (i == headPosition) {
                sb.append("[" + tape.get(i) + "]");
            } else {
                sb.append(" " + tape.get(i) + " ");
            }
        }
        return sb.toString();
    }
}
