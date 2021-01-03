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

/**
 * Singleton qui représente le chargeur de graphes, il prend un fichier texte et le transforme en Graph.
 */
public class GraphLoader {
    /**
     * Instance partagée.
     */
    private static final GraphLoader instance = new GraphLoader();

    private GraphLoader(){

    }

    /**
     * Crée un graphe de taille précisée à partir d'un fichier qui se trouve dans le dossier resources.
     *
     * @param size taille du graphe.
     * @param isNCube vrai si le fichier correspond à un N-Cube (nécessaire pour le nom du fichier).
     * @return le graphe correspondant.
     * @throws GraphLoaderException si le fichier n'existe pas.
     * @throws IOException si le fichier n'existe pas.
     */
    public Graph loadFile(int size, boolean isNCube) throws GraphLoaderException, IOException {
        String fileName = this.fileName(size, isNCube);
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());

        if (!file.exists())
            throw new GraphLoaderException("Le fichier n'existe pas.");

        String content = new String(Files.readAllBytes(file.toPath()));
        return createGraph(content, size, isNCube);
    }

    /**
     * S'il y a plusieurs graphes de même taille, on prend
     * un au hasard.
     *
     * @param size taille du graphe.
     * @return nom du fichier contenant le graphe.
     */
    private String fileName(int size, boolean isNCube){
        StringBuilder s = new StringBuilder();

        // Fichier de graphe.
        if (!isNCube) {
            s.append("g").append(size);
            if ((size == 30) || (size == 200)) {
                Random random = new Random();
                int i = random.nextInt(2);
                if (i == 1)
                    s.append("a");
                else
                    s.append("b");
            }
        }
        // Fichier de N-Cube.
        else{
            s.append("ncube").append(size);
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
     * @param isNCube vrai si on récupère un graphe à partir d'un fichier de N-Cube.
     * @return graphe créé à partir de la description.
     * @throws GraphLoaderException lancée si la taille demandée ne correspond
     * pas à celle de la description.
     */
    private Graph createGraph(String description, int size, boolean isNCube) throws GraphLoaderException {
        String[] lines = description.split("\r?\n");
        int sizeWritten = Integer.parseInt(lines[0]);

        if ((!isNCube) && (size != sizeWritten))
            throw new GraphLoaderException("Le fichier est incorrect.");

        Graph graph = new Graph(sizeWritten);
        int i, j, x, k = 0;

        // Place les sommets.
        for (i = 0; i < sizeWritten; i++){
            for (j = 0; j < sizeWritten; j++) {
                int vertex = sizeWritten * i + j;
                if (vertex < sizeWritten) {
                    if (vertex % 2 == 0) {
                        if (vertex % 4 == 0)
                            x = 150 + (300 * i) / sizeWritten;
                        else
                            x = 50 + (300 * i) / sizeWritten;
                    }
                    else {
                        if (k % 4 == 0)
                            x = 300 + (300 * i) / sizeWritten;
                        else
                            x = 200 + (300 * i) / sizeWritten;
                        k += 2;
                    }
                    graph.setCoordinate(vertex, x, 50 + (300 * j) / sizeWritten);
                }
            }
        }

        // Place les arêtes.
        for (i = 1; i <= sizeWritten; i++){
            int vertex = i - 1;
            for (j = 0; j < sizeWritten; j++){
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