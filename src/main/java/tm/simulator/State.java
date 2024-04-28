package tm.simulator;

import java.util.ArrayList;
import java.util.List;

public class State {
    public final String name;
    private List<Relation> relations;

    public State(String name, List<Relation> relations) {
        this.name = name;
        this.relations = relations;
    }

    public State(String name) {
        this.name = name;
        this.relations = new ArrayList<Relation>();
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
