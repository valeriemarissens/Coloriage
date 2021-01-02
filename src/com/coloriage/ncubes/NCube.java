/*
 * NCube.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 1/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package com.coloriage.ncubes;

import java.util.*;

public class NCube {
    /**
     * Nombre de dimensions du N-cube.
     */
    private final int N;
    private final List<Vertex> graph;

    public NCube(int nbDimensions){
        this.N = nbDimensions;

        int d = (int) Math.pow(2, N);
        this.graph = new ArrayList<>(d);
        for (int i = 0; i < d; i++){
            Vertex vertex = new Vertex(toBinary(i), i);
            graph.add(vertex);
        }

        this.build();
    }

    public void build(){
        for (int i = 0; i < graph.size(); i++){
            Vertex v = graph.get(i);
            String name1 = v.getName();

            for (int j = 0; j < graph.size(); j++){
                if (i != j){
                    Vertex v2 = graph.get(j);
                    String name2 = v2.getName();
                    int comp = compare(name1, name2);
                    if (comp == 1) {
                        v.addNeighbor(v2);
                        v2.addNeighbor(v);
                    }
                }
            }
        }

        testGraph();
    }

    public void testGraph(){
        for (Vertex v : graph)
            System.out.println(v.getName()+" : "+v.getNeighbors().toString());
    }

    public int compare(String s1, String s2){
        int diff = 0;
        for (int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i))
                diff++;
        }
        return diff;
    }

    /**
     *
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(N+"\n");
        for (Vertex vertex : graph){
            List<Vertex> neighbors = vertex.getNeighbors();

        }

        return s.toString();
    }
}
