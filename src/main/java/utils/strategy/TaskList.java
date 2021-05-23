package utils.strategy;

import java.util.ArrayList;

public class TaskList extends ArrayList<Tache> {

    @Override
    public boolean add(Tache tache) {
        tache.setId(this.size() + 1);
        return super.add(tache);
    }

    public boolean add(Tache tache, int id) {
        tache.setId(id);
        return super.add(tache);
    }
}
