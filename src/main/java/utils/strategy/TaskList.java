package utils.strategy;

import java.util.ArrayList;

public class TaskList extends ArrayList<Tache> {

    TaskList mirrorTaskList;

    public TaskList() {
        super();
        this.mirrorTaskList = new TaskList(false);
    }

    public TaskList(boolean withMirror) {
        super();
        if (withMirror) {
            this.mirrorTaskList = new TaskList(false);
        }
    }

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
    public Objectif generateObjectif(String name, int id, int score, int priority) {
        return new Objectif(name, id, score, priority, this);
    }

    public Objectif generateMirrorObjectif(String name, int id, int score, int priority) {
        Objectif objectif = new Objectif(name, id, score, priority, null);
        try {
            objectif.generateMirror(this, this.mirrorTaskList == null ? new ArrayList<>() : this.mirrorTaskList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectif;
    }
}
