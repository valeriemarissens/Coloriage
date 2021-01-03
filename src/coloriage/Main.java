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
import coloriage.troiscoloriage.Graph;
import coloriage.troiscoloriage.ThreeColoring;
import coloriage.utils.GraphLoader;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        GraphLoader graphLoader = GraphLoader.getInstance();
        try {
            Graph graph = graphLoader.loadFile(5);
            ThreeColoring threeColoring = new ThreeColoring(graph);
            int[] solution = threeColoring.color();

        } catch (GraphLoaderException | IOException | ThreeColoringException e) {
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
