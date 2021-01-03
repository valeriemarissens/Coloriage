/*
 * ThreeColoringTest.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 2/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage.tests;

import coloriage.exceptions.ThreeColoringException;
import coloriage.troiscoloriage.Edge;
import coloriage.troiscoloriage.Graph;
import coloriage.troiscoloriage.ThreeColoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ThreeColoringTest {
    private ThreeColoring threeColoring;
    private Graph g;

    @BeforeEach
    void setUp() {
        g = Graph.example();
        threeColoring = new ThreeColoring(g);
    }

    /**
     * O   -    2   -   4
     * |        |
     * 3   -    1
     */
    @Test
    void colorParticularGraph() throws ThreeColoringException {
        g = new Graph(5);
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

        threeColoring = new ThreeColoring(g);
        int[] solution = threeColoring.color();

        for (int vertex = 0; vertex < g.vertices(); vertex++){
            for (Edge e : g.adj(vertex)){
                int v2 = e.other(vertex);
                assertTrue(solution[vertex] != solution[v2]);
            }
        }
    }

    /**
     * O   -    2   -   4
     * |   X    |
     * 3   -    1
     */
    @Test
    void colorParticularIncorrectGraph() {
        g = new Graph(5);
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

        threeColoring = new ThreeColoring(g);
        assertThrows(ThreeColoringException.class, () -> threeColoring.color());
    }

    @Test
    void colorRandomGraph() throws ThreeColoringException {
        Random random = new Random();
        int size = random.nextInt(10);
        System.out.println("Size: "+size*size);
        g = Graph.Grid(size);

        threeColoring = new ThreeColoring(g);
        int[] solution = threeColoring.color();

        for (int vertex = 0; vertex < g.vertices(); vertex++){
            for (Edge e : g.adj(vertex)){
                int v2 = e.other(vertex);
                assertTrue(solution[vertex] != solution[v2]);
            }
        }
    }

    /**
     * O    -   2
     * |    X   |
     * 3    -   1
     */
    @SuppressWarnings("unchecked")
    @Test
    void isFourCliqueTrue() {
        Graph g = Graph.example();
        g.addEdge(3, 2);

        ThreeColoring threeColoring = new ThreeColoring(g);
        assertTrue(threeColoring.isFourClique(3));
    }

    /**
     * O    -   2
     * |    /   |
     * 3    -   1
     */
    @Test
    void isFourCliqueMinusOne() {
        Graph g = Graph.example();

        ThreeColoring threeColoring = new ThreeColoring(g);
        assertFalse(threeColoring.isFourClique(3));
    }

    /**
     * O        2
     * |        |
     * 3        1
     */
    @Test
    void isFourCliqueFalse() {
        Graph g = new Graph(4);
        g.setCoordinate(0, 100,100);
        g.setCoordinate(1, 300,300);
        g.setCoordinate(2, 300,100);
        g.setCoordinate(3, 100,300);
        g.addEdge(new Edge(0,3));
        g.addEdge(new Edge(1,2));

        ThreeColoring threeColoring = new ThreeColoring(g);
        assertFalse(threeColoring.isFourClique(3));
    }
}