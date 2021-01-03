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

import java.util.*;
import java.util.stream.Collectors;

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
            display();

            // Vérifie si l'échec est dû à la présence d'une 4-clique.
            if (isInFourClique(problematicVertex))
                throw new ThreeColoringException("Pas de solution trouvée car 4-clique");

            throw new ThreeColoringException("Pas de solution trouvée");
        }

        // Vérification de la coloration.
        if (isValid()) {
            System.out.println("Coloration possible :");
            for (int v = 0; v < n; v++) {
                System.out.println("color " + v + " : " + solution[v]);
            }

            // Affichage du graphe.
            display();
        }
        else
            throw new ThreeColoringException("Coloriage non valide.");

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
        System.out.println("Sommet problématique : "+vertex);
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

    /**
     * Cherche tous les ensembles de 4 éléments où se trouve le sommet, ensuite
     * regarde si un de ces ensembles est un 4-clique.
     *
     * @param vertex sommet qui nous intéresse.
     * @return vrai si le sommet est dans une 4-clique.
     */
    public boolean isInFourClique(int vertex){
        List<Edge> adj = graph.adj(vertex);

        // Le sommet a au moins 3 voisins : sinon impossible d'être dans une 4-clique.
        if (adj.size() >= 3) {

            // On récupère les voisins du sommet.
            List<Integer> neighbors = new ArrayList<>();
            neighbors.add(vertex);
            for (Edge e : adj) {
                neighbors.add(e.other(vertex));
            }

            // On récupère toutes les partitions de 4 sommets de l'ensemble des voisins.
            List<List<Integer>> partitions = partitions(neighbors);

            // On vérifie si une des partitions est une 4-clique.
            for (List<Integer> partition : partitions) {
                if (isFourClique(partition)) {
                    System.out.println("4-clique trouvée : "+partition.toString());
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param i entier.
     * @return factorielle de i.
     */
    private int fact(int i){
        if (i <= 1)
            return 1;

        return i * fact(i - 1);
    }

    /**
     * @param neighbors liste des sommets.
     * @return toutes les partitions de 4 éléments de la liste.
     */
    public List<List<Integer>> partitions(List<Integer> neighbors){
        List<List<Integer>> partitions = new ArrayList<>();;
        int k = neighbors.size();

        if (k != 4) {
            for (List<Integer> partition : powerSet(neighbors)) {
                if (partition.size() == 4) {
                    partitions.add(partition);
                }
            }
        }
        else{
            partitions.add(neighbors);
        }
        return partitions;
    }

    /**
     * Retrouve tous les sous-ensembles de list avec l'algorithme de Power Set.
     *
     * @param list à diviser.
     * @return tous les sous-ensembles de list.
     */
    private List<List<Integer>> powerSet(Collection<Integer> list) {
        List<List<Integer>> ps = new ArrayList<>();
        ps.add(new ArrayList<>());

        for (int vertex : list) {
            List<List<Integer>> newPs = new ArrayList<>();

            for (List<Integer> subset : ps) {
                // Copie tous les sous-ensembles de ps.
                newPs.add(subset);

                // Ajoute les sous-ensembles + le sommet actuel.
                List<Integer> newSubset = new ArrayList<>(subset);
                newSubset.add(vertex);
                newPs.add(newSubset);
            }

            // p = list.subList(0, list.indexOf(vertex) + 1)
            ps = newPs;
        }
        return ps;
    }

    /**
     * @param vertices liste des sommets.
     * @return vrai si les sommets de la liste forment une 4-clique.
     */
    private boolean isFourClique(List<Integer> vertices){
        boolean fourClique = true;

        if (vertices.size() == 4){
            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 4; j++){
                    if (i != j){
                        if (!isConnected(vertices.get(i), vertices.get(j))) {
                            fourClique = false;
                        }
                    }
                }
            }
        }
        else{
            fourClique = false;
        }

        return fourClique;
    }

    /**
     * @param v1 sommet 1
     * @param v2 sommet 2
     * @return vrai si les deux sommets sont adjcents.
     */
    private boolean isConnected(int v1, int v2){
        for (Edge e : graph.adj(v1)){
            if (v2 == e.other(v1)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------


    /* Dans cette partie, retourne vrai à :
     * O    -   2
     * |    /   |
     * 3    -   1
     * alors qu'il devrait y avoir une arête entre 0 et 1.
     */

    /**
     * Compte le nombre de sommets qui sont deux-à-deux adjacents avec vertex.
     *
     * @param vertex sommet qui nous intéresse.
     * @return vrai si le sommet vertex appartient à une 4-clique.
     */
    public boolean isFourClique2(int vertex){
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
