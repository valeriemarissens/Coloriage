/*
 * TroisColoriage.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 2/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage.troiscoloriage;

import coloriage.exceptions.ThreeColoringException;
import coloriage.utils.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreeColoring {
    private final Graph graph;
    private final int n;
    private final int[] colors;
    private int[] solution;
    private int problematicVertex;

    public ThreeColoring(Graph graph){
        this.graph = graph;
        this.n = graph.vertices();
        this.colors = new int[3];
        this.solution = new int[n];
        this.problematicVertex = -1;

        for (int i = 0; i < 3; i++)
            colors[i] = i;
    }

    /* ----------------------------------
     *         CHERCHE 3-COLORIAGE
     * --------------------------------- */

    /**
     *
     * @return un tableau à n cases contenant les couleurs pour chaque sommet.
     * @throws ThreeColoringException s'il n'y a pas de solution : le graphe n'est
     * pas 3-coloriable.
     */
    public int[] color() throws ThreeColoringException {
        this.solution = new int[n];

        // Lance le coloriage à partir du premier sommet du graphe.
        boolean foundColors = searchColor(0);

        // Échec du coloriage.
        if (!foundColors) {

            // Vérifie si l'échec est dû à la présence d'une 4-clique.
            if (isFourClique(problematicVertex))
                System.out.println("Normal : 4-clique.");

            throw new ThreeColoringException("Pas de solution trouvée");
        }

        // Vérification de la coloration.
        if (isValid()) {
            // todo: effacer souts
            System.out.println("VALIDE");
            for (int v = 0; v < n; v++) {
                System.out.println("color " + v + " : " + solution[v]);
            }

            // Affichage du graphe.
            display();
        }
        else
            throw new ThreeColoringException("Coloriage non valide.");

        // todo : effacer Affichage du graphe.
        display();

        return solution;
    }

    /**
     * Parcours les couleurs, s'il y a une couleur qui ne pose pas de problèmes
     * pour ce sommet, alors on le colorie et on passe au suivant.
     *
     * @param vertex sommet qu'on colorie.
     * @return vrai si un coloriage a été trouvé. Sinon, donne la valeur du sommet
     * en conflit à l'attribut "problematicVertex".
     */
    private boolean searchColor(int vertex){
        if (vertex == n)
            return true;

        int k = 0;
        while (k < 3){
            int color = colors[k];
            if (isCorrect(vertex, color)){
                solution[vertex] = color;
                return searchColor(vertex + 1);
            }
            k++;
        }

        this.problematicVertex = vertex;
        System.out.println("NOT FOUND: "+vertex);
        return false;
    }

    /**
     * @param vertex sommet à tester.
     * @param color couleur à tester.
     * @return vrai si aucun des voisins du sommet a déjà occupé la couleur.
     */
    private boolean isCorrect(int vertex, int color){
        for (Edge e : graph.adj(vertex)){
            int neighbor = e.other(vertex);

            if (solution[neighbor] == color) {
                return false;
            }
        }

        return true;
    }

    /* ----------------------------------
     *          PRESENCE 4-CLIQUE
     * --------------------------------- */

    /* todo : fix ceci (ou chercher bien sur internetla déf de 4-clique ?)
     * dit oui à :
     * O    -   2
     * |    /   |
     * 3    -   1
     * alors qu'il devrait y avoir une arête entre 0 et 1 (?
     */

    /**
     * Compte le nombre de sommets qui sont deux-à-deux adjacents avec vertex.
     *
     * @param vertex sommet qui nous intéresse.
     * @return vrai si le sommet vertex appartient à une 4-clique.
     */
    public boolean isFourClique(int vertex){
        int nbConnectedVertices = 0;
        for (int v2 = 0; v2 < n; v2++){
            if (pathExists(vertex, v2))
                nbConnectedVertices++;
        }

        System.out.println("nbConnectedVertices: "+nbConnectedVertices);
        return (nbConnectedVertices >= 4);
    }

    /**
     * Vérifie si le sommet v2 apparaît dans le parcours en profondeur depuis v1.
     * Il n'est pas nécessaire de faire l'inverse (vérifier si v1 apparaît dans
     * le parcours en profondeur depuis v2) car le graphe est non orienté.
     *
     * @param v1 premier sommet.
     * @param v2 deuxième sommet.
     * @return vrai si les sommets v1 et v2 sont adjacents.
     */
    private boolean pathExists(int v1, int v2){
        List<Integer> tab1 = depthFirstSearch(v1);

        return tab1.get(1).equals(v2);
    }

    /**
     * @param vertex sommet de départ.
     * @return parcours en profondeur depuis le sommet vertex.
     */
    private List<Integer> depthFirstSearch(int vertex){
        List<Integer> marked = new ArrayList<>();
        explore(vertex, marked);

        System.out.println("parcoursProf "+vertex+" : "+marked);

        return marked;
    }

    /**
     * Marquage des sommets pour éviter de retomber sur des sommets déjà parcourus.
     *
     * @param vertex sommet qu'on parcourt.
     * @param marked liste des sommets déjà parcourus.
     */
    private void explore(int vertex, List<Integer> marked){
        marked.add(vertex);

        for (Edge e : graph.adj(vertex)){
            int neighbor = e.other(vertex);
            if (!marked.contains(neighbor))
                explore(neighbor, marked);
        }
    }

    /* ----------------------------------
     *          VERIFICATION
     * --------------------------------- */

    /**
     * Vérifie que, pour toutes les arêtes, les couleurs des deux sommets soient
     * différentes.
     *
     * @return vrai si le 3-coloriage est valide.
     */
    private boolean isValid(){
        for (Edge e : graph.edges()){
            int from = e.getFrom();
            int to = e.getTo();
            if (solution[from] == solution[to]) {
                System.out.println(from+" et "+to+" sont "+solution[to]);
                return false;
            }
        }

        return true;
    }

    /* ----------------------------------
     *          AFFICHAGE
     * --------------------------------- */

    private void display(){
        Display d = new Display("Graphe");
        d.setImage(graph.toImage(solution));

        System.out.println("Veuillez appuyer sur une touche pour fermer la fenêtre.");
        new Scanner(System.in).nextLine();
        d.close();
    }

}
