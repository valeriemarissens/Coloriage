/*
 * Vertex.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 1/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package com.coloriage.ncubes;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NCubeVertex {
    private final int nb;
    private final String name;
    private List<NCubeVertex> neighbors;

    public NCubeVertex(String name, int nb){
        this.nb = nb;
        this.name = name;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(@NotNull NCubeVertex v){
        if (!neighbors.contains(v))
            neighbors.add(v);
    }

    public String getName() {
        return name;
    }

    public List<NCubeVertex> getNeighbors() {
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NCubeVertex)) return false;
        NCubeVertex vertex = (NCubeVertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
