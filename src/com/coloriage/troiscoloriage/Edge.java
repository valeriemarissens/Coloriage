/*
 * Edge.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 2/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package com.coloriage.troiscoloriage;

/**
 * Représente une arête non orientée.
 */
public class Edge
{
    private final int from;
    private final int to;

    /**
     * Au début toutes les arêtes sont des murs.
     */
    private boolean isCorridor;

    /**
     * Vrai si cette arête est utilisée dans l'arbre couvrant.
     */
    boolean used;

    public Edge(int x, int y){
        this.from = x;
        this.to = y;
        this.used = false;
        this.isCorridor = false;
    }

    /**
     *
     * @param v sommet dont on cherche le voisin.
     * @return le sommet à l'autre extrémité de l'arête.
     */
    public final int other(int v)
    {
        if (this.from == v) return this.to; else return this.from;
    }

    public void mark(boolean b){
        this.used = b;
    }

    /**
     * Fait d'une arête un couloir.
     */
    public void corridor(){
        this.isCorridor = true;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isCorridor() {
        return isCorridor;
    }

    @Override
    public String toString() {
        return "[" + from + "-" + to + "]";
    }
}

