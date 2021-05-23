package utils.strategy;

import java.util.List;

/**
 * Created by franc on 27/04/2018.
 */
public class Strategie {

    public List<Objectif> couleur0;
    public List<Objectif> couleur3000;

    public Strategie(List<Objectif> couleur0, List<Objectif> couleur3000) {
        this.couleur0 = couleur0;
        this.couleur3000 = couleur3000;
    }

    public Strategie() {
    }

    @Override
    public String toString() {
        return "Strategie{" +
                "\n\tcouleur0=" + couleur0 +
                ", \n\tcouleur3000=" + couleur3000 +
                "\n}";
    }
}
