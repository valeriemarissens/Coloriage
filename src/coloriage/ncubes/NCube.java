/*
 * NCube.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 1/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage.ncubes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Classe qui représente un N-Cube.
 */
public class NCube {
    /**
     * Nombre de dimensions du N-cube.
     */
    private final int N;
    private final List<NCubeVertex> ncube;

    public NCube(int dimensions){
        this.N = dimensions;

        // Crée 2^n sommets.
        int d = (int) Math.pow(2, N);
        this.ncube = new ArrayList<>(d);
        for (int i = 0; i < d; i++){
            NCubeVertex vertex = new NCubeVertex(toBinary(i), i);
            ncube.add(vertex);
        }

        this.build();
    }

    /**
     * Construit les arêtes du N-Cube.
     */
    private void build(){
        for (int i = 0; i < ncube.size(); i++){
            NCubeVertex v1 = ncube.get(i);
            String name1 = v1.getBinaryName();

            for (int j = 0; j < ncube.size(); j++){
                if (i != j){
                    NCubeVertex v2 = ncube.get(j);
                    String name2 = v2.getBinaryName();
                    int comp = compare(name1, name2);

                    // Ajoute les voisins que quand ils n'ont qu'un seul bit différent.
                    if (comp == 1) {
                        v1.addNeighbor(v2);
                        v2.addNeighbor(v1);
                    }
                }
            }
        }
    }

    /**
     * @param s1 premier mot.
     * @param s2 deuxième mot.
     * @return le nombre de caractères différents entre s1 et s2.
     */
    public int compare(String s1, String s2){
        int diff = 0;
        for (int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i))
                diff++;
        }
        return diff;
    }

    /**
     * @param k à transformer en binaire.
     * @return représentation binaire de taille N du nombre k
     */
    public String toBinary(int k){
        StringBuilder s = new StringBuilder();
        Stack<Integer> remaining = new Stack<>();
        int n = N;

        while (k > 0){
            int r = k % 2;
            remaining.push(r);
            k /= 2;
            n--;
        }

        if (n > 0){
            while (n > 0){
                remaining.push(0);
                n--;
            }
        }

        while (!remaining.isEmpty()){
            s.append(remaining.pop());
        }

        return s.toString();
    }

    /**
     * @return le N-Cube sous forme de matrice d'adjacence.
     */
    private int[][] toMatrix(){
        int size = ncube.size();
        int[][] matrix = new int[size][size];
        int i, j;

        // Initialisation à 0.
        for (i = 0; i < size; i++){
            for (j = 0; j < size; j++)
                matrix[i][j] = 0;
        }

        // On cherche les arêtes.
        for (i = 0; i < size; i++){
            NCubeVertex v1 = ncube.get(i);
            List<NCubeVertex> neighbors = v1.getNeighbors();

            for (j = 0; j < neighbors.size(); j++) {
                NCubeVertex v2 = neighbors.get(j);
                matrix[i][v2.getNb()] = 1;
            }
        }

        return matrix;
    }

    /**
     * Format :
     *      [nombre de sommets]
     *      [matrice d'adjacence]
     *
     * @return la description du N-Cube.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(ncube.size()).append("\n");

        int[][] matrix = toMatrix();
        for (int[] ints : matrix) {
            for (int i : ints)
                s.append(i);
            s.append("\n");
        }

        return s.toString();
    }

    public List<NCubeVertex> getNcube() {
        return ncube;
    }

    /**
     * Sauvegarde le fichier contenant la description du N-Cube dans le format demandé.
     */
    public void saveFile(){
        try {

            FileWriter fileWriter = new FileWriter("ncube"+N+".txt");
            fileWriter.write(toString());
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
