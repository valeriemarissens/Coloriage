/*
 * Main.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 29/12/2020.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage;

import coloriage.exceptions.GraphLoaderException;
import coloriage.exceptions.ThreeColoringException;
import coloriage.troiscoloriage.Edge;
import coloriage.troiscoloriage.Graph;
import coloriage.troiscoloriage.ThreeColoring;
import coloriage.utils.GraphLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {

    public static void main(String[] args) {
        // Test avec un graphe fixe.
        petitTest();

        // Test avec un fichier.
        testWithFile(5);
    }

    /**
     * Colorie un des graphes qui sont dans le dossier resources.
     * @param size taille du graphe.
     */
    private static void testWithFile(int size){
        try {
            Graph graph = GraphLoader.getInstance().loadFile(size);
            ThreeColoring threeColoring = new ThreeColoring(graph);
            threeColoring.color();

        } catch (GraphLoaderException | IOException | ThreeColoringException e) {
            e.printStackTrace();
        }
    }

    /**
     * Colorie ce graphe :
     * O   -    2   -   4
     * |        |
     * 3   -    1
     */
    private static void petitTest(){
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

    private static void testExponentiel(){ //bug
        int n = 4;
        int[] couleurs = new int[3];
        for (int i = 0; i < 3; i++){
            couleurs[i] = i;
        }
        System.out.println("couleurs:"+ Arrays.toString(couleurs));

        int[][] tab = new int[(int) Math.pow(3, n)][n];


        for (int k = 0; k < 3; k++){
            for (int j = 0; j < n; j++){
                for(int i = 0; i < Math.pow(3, n); i++){
                    //System.out.println("i:"+i+", j:"+j+", k:"+k);
                    tab[i][j] = couleurs[k];
                }
            }
        }

        for (int i = 0; i < Math.pow(3, n); i++){
            System.out.println(Arrays.toString(tab[i]));
        }
    }
}
