package utils.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by franc on 27/04/2018.
 */
public class Objectif {

    public String desc;
    public int id;
    public int points;
    public int priorite;
    public List<Tache> taches;

    public Objectif(String desc, int id, int points, int priorite, List<Tache> taches) {
        this.desc = desc;
        this.id = id;
        this.points = points;
        this.priorite = priorite;
        this.taches = taches;
    }

    public Objectif() {
    }

    /**
     * Génère les tâches mirroirs à partie d'une liste
     * @param tacheMirroir
     */
    public void generateMirror(List<Tache> tacheMirroir) throws Exception {
        this.generateMirror(tacheMirroir, new ArrayList<>());
    }

    /**
     * Génère les tâches mirroirs à partie d'une liste
     * @param tacheMirroir
     * @param tacheSpecifique
     */
    public void generateMirror(List<Tache> tacheMirroir, List<Tache> tacheSpecifique) throws Exception {
        this.taches = new ArrayList<>();

        for (Tache tache : tacheMirroir) {
            if (tache.mirror == Tache.Mirror.NONE) {
                this.taches.add(tache);
            } else if (tache.mirror == Tache.Mirror.MIRRORY) {
                Tache t = tache.clone();
                t.positionY = 3000 - t.positionY;
                if (t.itemId != null) {
                    t.itemId = t.itemId.replace("0_", "3000_");
                }
                this.taches.add(t);
            }
        }

        this.taches.addAll(tacheSpecifique);
        this.taches.sort(Comparator.comparingInt(o -> o.id));

        if (this.taches.size() != tacheMirroir.size()) {
            throw new Exception("Il manque des objectifs non ?");
        }
    }

    @Override
    public String toString() {
        return "\n\t\tObjectif{" +
                "\n\t\t\tdesc='" + desc + '\'' +
                ",\n\t\t\tid=" + id +
                ",\n\t\t\tpoints=" + points +
                ",\n\t\t\tpriorite=" + priorite +
                ",\n\t\t\ttaches=" + taches +
                "\n\t\t)";
    }
}
