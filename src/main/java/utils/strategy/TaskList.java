package utils.strategy;

import java.util.ArrayList;

public class TaskList extends ArrayList<Tache> {

    TaskList mirrorTaskList;

    public TaskList getMirrorTaskList() {
        return mirrorTaskList;
    }

    public void setMirrorTaskList(TaskList mirrorTaskList) {
        this.mirrorTaskList = mirrorTaskList;
    }

    @Override
    public boolean add(Tache tache) {
        tache.setId(this.size() + 1);
        return super.add(tache);
    }

    public boolean add(Tache tache, int id) {
        tache.setId(id);
        return super.add(tache);
    }

    public boolean add(Tache task, Tache mirrorTask) {
        this.add(task);
        return mirrorTaskList.add(mirrorTask, task.getId());
    }
}
