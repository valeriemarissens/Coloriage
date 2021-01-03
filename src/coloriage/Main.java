/*
 * Main.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 29/12/2020.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage;

import coloriage.exceptions.GraphLoaderException;
import coloriage.exceptions.NCubeException;
import coloriage.exceptions.ThreeColoringException;
import coloriage.ncubes.NCube;
import coloriage.ncubes.NCubeGenerator;
import coloriage.troiscoloriage.Edge;
import coloriage.troiscoloriage.Graph;
import coloriage.troiscoloriage.ThreeColoring;
import coloriage.utils.GraphLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {

    public static void main(String[] args) {
        // Test avec un graphe fixe correct.
        //correctTest();

        // Test avec un graphe fixe avec 4-clique.
        //incorrectTest();

        // Test avec un fichier.
        //testWithFile(5);

        // Test avec un N-Cube.
        testNCube(8);
    }

    /**
     * Colorie un des graphes qui sont dans le dossier resources.
     * @param size taille du graphe.
     */
    private static void testWithFile(int size){
        try {
            Graph graph = GraphLoader.getInstance().loadFile(size, false);
            ThreeColoring threeColoring = new ThreeColoring(graph);
            threeColoring.color();
        }
        catch (GraphLoaderException | IOException | ThreeColoringException e) {
            e.printStackTrace();
        }
    }

    /**
     * Colorie ce graphe :
     * O   -    2   -   4
     * |        |
     * 3   -    1
     */
    private static void correctTest(){
        Graph g = new Graph(5);
        g.setCoordinate(0, 100,100);
        g.setCoordinate(1, 300,300);
        g.setCoordinate(2, 300,100);
        g.setCoordinate(3, 100,300);
        g.setCoordinate(4, 400,100);
        g.addEdge(new Edge(0,3));
        g.addEdge(new Edge(0,2));
        g.addEdge(new Edge(1,3));
        g.addEdge(new Edge(4,2));
        g.addEdge(new Edge(1,2));

        ThreeColoring threeColoring = new ThreeColoring(g);
        try {
            threeColoring.color();
        } catch (ThreeColoringException e) {
            e.printStackTrace();
        }
    }

    /**
     * O   -    2   -   4
     * |   X    |
     * 3   -    1
     */
    private static void incorrectTest(){
        Graph g = new Graph(5);
        g.setCoordinate(0, 100,100);
        g.setCoordinate(1, 300,300);
        g.setCoordinate(2, 300,100);
        g.setCoordinate(3, 100,300);
        g.setCoordinate(4, 400,100);
        g.addEdge(new Edge(0,3));
        g.addEdge(new Edge(0,2));
        g.addEdge(new Edge(1,3));
        g.addEdge(new Edge(4,2));
        g.addEdge(new Edge(3,2));
        g.addEdge(new Edge(1,2));
        g.addEdge(new Edge(1,0));

        ThreeColoring threeColoring = new ThreeColoring(g);
        try {
            threeColoring.color();
        } catch (ThreeColoringException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sauvegarde un N-Cube de taille donnée.
     * @param dimension nb de dimensions du N-Cube, ie N.
     */
    private static void saveNCube(int dimension){
        NCube ncube = null;
        try {
            ncube = NCubeGenerator.getInstance().getCube(dimension);
        } catch (NCubeException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(ncube).saveFile();
    }

    /**
     * Test de coloriage avec un N-Cube.
     * @param dimension N.
     */
    private static void testNCube(int dimension){
        try {
            Graph graph = GraphLoader.getInstance().loadFile(dimension, true);
            ThreeColoring threeColoring = new ThreeColoring(graph);
            threeColoring.color();
        }
        catch (GraphLoaderException | IOException | ThreeColoringException e) {
            e.printStackTrace();
        }
    }
}
