package tm.simulator;

import java.util.ArrayList;
import java.util.List;

public class State {
    public final String name;
    private List<Relation> relations;
    public boolean isFinal = false;
    public boolean isStart = false;

    public State(String name, List<Relation> relations) {
        this.name = name;
        this.relations = relations;
    }

    public State(String name, boolean isFinal, boolean isStart) {
        this.name = name;
        this.relations = new ArrayList<Relation>();
        this.isFinal = isFinal;
        this.isStart = isStart;
    }

    public State(String name) {
        this.name = name;
        this.relations = new ArrayList<Relation>();
    }

    public void newRelation(State nextState, Character inputCharacter, Character tapeOutput, Character move) {
        relations.add(new Relation(this, inputCharacter, nextState, tapeOutput, move));
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public String toString() {
        return name;
    }
}
