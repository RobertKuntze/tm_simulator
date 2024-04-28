package tm.simulator;

public record TapeOutput(Character character, Character direction) {
    public String toString() {
        return character + " " + direction;
    }
}
