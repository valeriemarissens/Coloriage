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

public class Vertex {
    private final int nb;
    private final String name;
    private List<Vertex> neighbors;

    public Vertex(String name, int nb){
        this.nb = nb;
        this.name = name;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(@NotNull Vertex v){
        if (!neighbors.contains(v))
            neighbors.add(v);
    }

    public String getName() {
        return name;
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
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
