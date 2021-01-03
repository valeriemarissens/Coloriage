/*
 * GraphLoader.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 3/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage.utils;

import coloriage.exceptions.GraphLoaderException;
import coloriage.troiscoloriage.Edge;
import coloriage.troiscoloriage.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Random;

public class GraphLoader {
    private static final GraphLoader instance = new GraphLoader();

    //todo : @Contract(pure = true)
    private GraphLoader(){

    }

    public Graph loadFile(int size) throws GraphLoaderException, IOException {
        String fileName = this.fileName(size);
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());

        if (!file.exists())
            throw new GraphLoaderException("Le fichier n'existe pas.");

        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);

        return createGraph(content, size);
    }

    /**
     * S'il y a plusieurs graphes de même taille, on prend
     * un au hasard.
     *
     * @param size taille du graphe.
     * @return nom du fichier contenant le graphe.
     */
    private String fileName(int size){
        StringBuilder s = new StringBuilder("g"+size);
        if ((size == 30) || (size == 200)){
            Random random = new Random();
            int i = random.nextInt(2);
            if (i == 1)
                s.append("a");
            else
                s.append("b");
        }

        s.append(".txt");

        return s.toString();
    }

    /**
     * @param description sous la forme :
     *                      [nombre de sommets]
     *                      [matrice d'adjacence]
     *                    Voici un exemple :
     *                      5
     *                      01101
     *                      10001
     *                      10011
     *                      00101
     *                      11110
     *
     * @param size taille du graphe.
     * @return graphe créé à partir de la description.
     * @throws GraphLoaderException lancée si la taille demandée ne correspond
     * pas à celle de la description.
     */
    private Graph createGraph(String description, int size) throws GraphLoaderException {
        String[] lines = description.split("\r?\n");
        int sizeWritten = Integer.parseInt(lines[0]);

        if (size != sizeWritten)
            throw new GraphLoaderException("Le fichier est incorrect.");

        Graph graph = new Graph(size);
        int i, j, x;

        // Place les sommets.
        for (i = 0; i < size; i++){
            for (j = 0; j < size; j++) {
                int vertex = size * i + j;
                if (vertex < size) {
                    if (vertex % 2 == 0) {
                        if (vertex % 4 == 0)
                            x = 150 + (300 * i) / size;
                        else
                            x = 50 + (300 * i) / size;
                    }
                    else
                        x = 300 + (300 * i) / size;
                    graph.setCoordinate(vertex, x, 50 + (300 * j) / size);
                }
            }
        }

        // Place les arêtes.
        for (i = 1; i <= size; i++){
            int vertex = i - 1;
            for (j = 0; j < size; j++){
                if (lines[i].charAt(j) == '1')
                    graph.addEdge(new Edge(vertex, j));
            }
        }

        return graph;
    }

    public static GraphLoader getInstance() {
        return instance;
    }
}