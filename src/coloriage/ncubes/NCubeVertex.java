/*
 * Vertex.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 1/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage.ncubes;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sommet d'un N-Cube.
 */
public class NCubeVertex {
    /**
     * Nom du sommet en décimal.
     */
    private final int nb;

    /**
     * Nom du sommet en binaire.
     */
    private final String binaryName;

    /**
     * Voisins du sommet.
     */
    private final List<NCubeVertex> neighbors;

    public NCubeVertex(String name, int nb){
        this.nb = nb;
        this.binaryName = name;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(@NotNull NCubeVertex v){
        if (!neighbors.contains(v))
            neighbors.add(v);
    }

    public String getBinaryName() {
        return binaryName;
    }

    public List<NCubeVertex> getNeighbors() {
        return neighbors;
    }

    public int getNb() {
        return nb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NCubeVertex)) return false;
        NCubeVertex vertex = (NCubeVertex) o;
        return Objects.equals(binaryName, vertex.binaryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(binaryName);
    }

    @Override
    public String toString() {
        return this.binaryName;
    }
}
