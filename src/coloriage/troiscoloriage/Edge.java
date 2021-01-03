/*
 * Edge.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 2/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage.troiscoloriage;

/**
 * Représente une arête non orientée du graphe.
 */
public class Edge
{
    /**
     * Sommet d'où l'arête part.
     */
    private final int from;

    /**
     * Sommet où l'arête va.
     */
    private final int to;

    public Edge(int x, int y){
        this.from = x;
        this.to = y;
    }

    /**
     * @param v sommet dont on cherche le voisin.
     * @return le sommet à l'autre extrémité de l'arête.
     */
    public final int other(int v)
    {
        if (this.from == v) return this.to; else return this.from;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[" + from + "-" + to + "]";
    }
}

