package pathfinding.table.astar;

import api.log.LoggerFactory;
import org.apache.logging.log4j.Logger;
import pathfinding.table.Point;
import pathfinding.table.Table;

import java.util.PriorityQueue;
import java.util.Stack;


/**
 * Created by Guillaume on 15/05/2017.
 */
public class Astar {
    /**
     * La distance des points. On utilise des entiers, donc pour la distance des diagonales,
     * on arrondit sqrt(2) à 1.4 et on multiplie tout par 10
     */
    private static final int DIST_DIAGONALE = 18;//14;
    private static final int DIST_H_V = 10;
    private Logger logger;

    /**
     * La table
     */
    private Table table;

    /**
     * La dimension de la grille
     */
    private int dimX;
    private int dimY;

    /**
     * La grille
     */
    private Node[][] grille;

    /**
     * Les noeuds à examiner: une file ordonnée
     */
    private PriorityQueue<Node> ouverts;

    /**
     * Crée un calculateur de chemin basé sur l'algo Astar
     */
    public Astar(Table table) {
        this.logger = LoggerFactory.getLogger(Astar.class);

        this.table = table;

        // Pour une taille N, on stocke les points de coordonnées 0 à N.
        // Cela fait donc N+1 points
        this.dimX = this.table.getRectifiedXSize() + 1;
        this.dimY = this.table.getRectifiedYSize() + 1;
        this.logger.info("Initialize the algorithm with a dimension: " + dimX + " x " + dimY);

        grille = new Node[this.dimX][this.dimY];
        for (int x = 0; x < this.dimX; x++) {
            for (int y = 0; y < this.dimY; y++) {
                //If we can reach a case adjacent to a node, we declare it unreachable
                if (this.table.isAreaForbiddenSafe(x, y)
                        || this.table.isAreaForbiddenSafe(x - 1, y)
                        || this.table.isAreaForbiddenSafe(x - 1, y - 1)
                        || this.table.isAreaForbiddenSafe(x, y - 1)) {
                    grille[x][y] = null;
                } else {
                    grille[x][y] = new Node(x, y);
                }
            }
        }

        ouverts = new PriorityQueue<Node>(this.dimX * this.dimY);
        updateVoisinageInfo();
    }

    public Table getTable() {
        return table;
    }

    /**
     * Controle l'accessibilité d'une case de la grille
     * Cette méthode est "définitive": à utiliser à l'init pour définir
     * les zones physiquements inaccessibles sur la table, genre bordures et cie.
     * <p>
     * Ne pas oublier d'appeler la méthode {@link #updateVoisinageInfo()} après !
     *
     * @param x
     * @param y
     * @param accessible
     */
    public void setDefinitivelyAccessible(int x, int y, boolean accessible) {

        //  Si une case n'est pas accessible, on met le noeud correspondant à null
        if (accessible && grille[x][y] == null) {
            grille[x][y] = new Node(x, y);
        } else if (!accessible) {
            grille[x][y] = null;
        }
    }

    /**
     * Controle l'accès à un noeud de manière temporaire
     * A utiliser juste avant de calculer un chemin, pour définir une zone inaccessible
     * parce qu'un robot adverse y est, par exemple...
     *
     * @param x
     * @param y
     * @param accessible
     */
    public void setTemporaryAccessible(int x, int y, boolean accessible) {
        Node noeud = getNode(x, y);

        if (noeud != null) {
            noeud.accessible = accessible;
        }
    }

    /**
     * Remet toutes les zones temporairements inaccessibles en accessibles
     */
    public void resetTemporaryAccessible() {
        for (int x = 0; x < dimX; x++) {
            for (int y = 0; y < dimY; y++) {
                if (grille[x][y] != null) {
                    grille[x][y].accessible = true;
                }
            }
        }
    }

    private Node getNode(int x, int y) {
        if (x >= 0 && x < dimX && y >= 0 && y < dimY) {
            return grille[x][y];
        }
        return null;
    }

    /**
     * Cette fonction est à appeler INDISPENSABLEMENT APRÈS avoir défini les zones
     * définitivement inaccessible !
     * Si c'est appelé avant ou pas appelé, ça fera N'IMPORTE QUOI !
     */
    public void updateVoisinageInfo() {
        for (int x = 0; x < dimX; x++) {
            for (int y = 0; y < dimY; y++) {

                Node node = grille[x][y];

                if (node == null) {
                    continue;
                }

                /*
                 * Comme on est des oufs, on a une méthode qui renvoie null si le noeud
                 * n'existe pas, donc on ne vérifie pas les bornes ici.
                 */
                node.voisin_n = getNode(x - 1, y);
                node.voisin_s = getNode(x + 1, y);
                node.voisin_e = getNode(x, y - 1);
                node.voisin_w = getNode(x, y + 1);
                node.voisin_ne = getNode(x - 1, y - 1);
                node.voisin_nw = getNode(x - 1, y + 1);
                node.voisin_se = getNode(x + 1, y - 1);
                node.voisin_sw = getNode(x + 1, y + 1);

            }
        }
    }

    private void updateCout(Node courant, Node suivant, int cout, Point.DIRECTION direction) {

        if (suivant == null || suivant.ferme || !suivant.accessible) {
            // Rien à faire
            return;
        }
        /*
         * Nouveau cout = (cout pour aller au noeud suivant)
         * 					+ (cout estimé du noeud suivant jusqu'à l'arrivé)
         */
        final int directionOverhead = (courant.parentDirection == direction) ? 0 : 500000000;
        int nouveauCout = suivant.heuristique + cout + directionOverhead;

        // Si on n'a pas examiné le point, ou que le chemin améliore le cout...
        if (nouveauCout < suivant.coutHeuristique) {

            // ... on met à jour les couts...
            suivant.cout = cout;
            suivant.coutHeuristique = nouveauCout;
            suivant.parentDirection = direction;

            /* Il y a un risque d'ajouter un noeud déjà existant dans la file des ouverts, mais:
             * - La file ne se re-trie pas si un noeud est modifié ;
             * - Supprimer un noeud existant est lent : temps en O(n) ;
             * - La tête de la file est toujours le noeud le moins couteux, même si le reste
             *   est moins bien trié.
             * Du coup, on ajoute et on s'en cogne. */
            ouverts.add(suivant);

            // ... et on met à jour le chemin
            suivant.parent = courant;
        }
    }

    private void calculChemin(int startX, int startY, int objectifX, int objectifY) {
        if (grille[objectifX][objectifY] == null) {
            logger.error("Objectif is in forbiden area.");
        }

        if (grille[startX][startY] == null) {
            logger.error("Start is in forbiden area.");
        }

        // ON VIDE TOUT !!!
        for (int x = 0; x < dimX; x++) {
            final int distX = Math.abs(objectifX - x);

            for (int y = 0; y < dimY; y++) {
                final Node temp = grille[x][y];

                if (temp != null) {
                    temp.cout = Integer.MAX_VALUE;
                    temp.coutHeuristique = Integer.MAX_VALUE;
                    temp.parent = null;
                    temp.ferme = false;

                    // Cout estimé entre le noeud et l'arrivé: distance de Manhattan
                    temp.heuristique = (distX + Math.abs(objectifY - y)) * DIST_H_V;
                }
            }
        }
        ouverts.clear();

        Node courant = null;
        final Node start = grille[startX][startY];
        start.cout = 0;
        start.coutHeuristique = start.heuristique;
        start.parentDirection = Point.DIRECTION.NULL;

        // On ajoute le noeud de départ à la liste des ouverts
        ouverts.add(start);

        // Boucle de calcul
        while (true) {

            // On prend le noeud le plus intéressant (cout le plus faible)
            courant = ouverts.poll();

            if (courant == null) {
                // Plus rien à faire
                return;
            }

            if (courant.ferme) {
                /* Un même noeud peut être ajouté plusieurs fois dans l'ensemble des ouverts, donc
                 * on vérifie si on ne l'a pas déjà traité. */
                continue;
            }

            // On "ferme" le noeud
            courant.ferme = true;

            // On vérifie si on arrivé
            if (courant.x == objectifX && courant.y == objectifY) {
                return;
            }

            // on vérifie les noeuds adjacents
            final int cout_h_v = courant.cout + DIST_H_V;
            final int cout_diag = courant.cout + DIST_DIAGONALE;

            updateCout(courant, courant.voisin_n, cout_h_v, Point.DIRECTION.N);
            updateCout(courant, courant.voisin_s, cout_h_v, Point.DIRECTION.S);
            updateCout(courant, courant.voisin_e, cout_h_v, Point.DIRECTION.E);
            updateCout(courant, courant.voisin_w, cout_h_v, Point.DIRECTION.O);

            updateCout(courant, courant.voisin_ne, cout_diag, Point.DIRECTION.NE);
            updateCout(courant, courant.voisin_nw, cout_diag, Point.DIRECTION.NO);
            updateCout(courant, courant.voisin_se, cout_diag, Point.DIRECTION.SE);
            updateCout(courant, courant.voisin_sw, cout_diag, Point.DIRECTION.SO);
        }

    }

    /**
     * Récupère une pile de points à parcourir entre deux points
     *
     * @param start    le point de départ
     * @param objectif le point d'arrivé
     * @return une pile de point, commençant par le point de départ
     */
    public Stack<Point> getChemin(Point start, Point objectif) {
        logger.info("Start astar to compute path between " + start + " and " + objectif);
        long startTime = System.currentTimeMillis();

        // Le chemin calculé
        Stack<Point> leChemin = new Stack<Point>();

        // Test tout con, parce qu'on sait jamais...
        if (start.x == objectif.x && start.y == objectif.y) {
            // start = objectif, alors on s'emmerde pas !
            leChemin.add(start);
            return leChemin;
        }

        // On vérifie les index, parce qu'on sait jamais non plus...
        if (start.x < 0 || start.x >= dimX || start.y < 0 || start.y >= dimY
                || objectif.x < 0 || objectif.x > dimX || objectif.y < 0 || objectif.y >= dimY) {
            logger.error("Point en dehors de la table !");
            System.out.println("Point en dehors de la table !");
            return null;
        }

        if (grille[start.x][start.y] == null) {
            logger.error("Départ en zone interdite");
            System.out.println("Départ en zone interdite");
            return null;
        }

        if (grille[objectif.x][objectif.y] == null) {
            logger.error("Arrivée en zone interdite");
            System.out.println("Arrivée en zone interdite");
            return null;
        }

        // On lance le bouzin !
        calculChemin(start.x, start.y, objectif.x, objectif.y);

        // On remplie la pile avec les points
        Node courant = grille[objectif.x][objectif.y];

        // Si l'objectif n'a pas de parent, c'est qu'il n'y a pas de chemin !
        if (courant == null || courant.parent == null) {
            logger.error("Aucun chemin trouvé !");
            System.out.println("Aucun chemin trouvé !");
            return null;
        }

        while (courant != null) {
            leChemin.add(new Point(courant.x, courant.y));
            courant = courant.parent;
        }
        logger.info("Astar end computation in " + (System.currentTimeMillis() - startTime) + "ms");
        return leChemin;
    }

}
