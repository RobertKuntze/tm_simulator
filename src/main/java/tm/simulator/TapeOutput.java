package tm.simulator;

public record TapeOutput(Character character, Character direction) {
    public String toString() {
        return character + " " + direction;
    }

    public static TapeOutput fromString(String string) {
        return new TapeOutput(string.charAt(0), string.charAt(2));
    }
}
