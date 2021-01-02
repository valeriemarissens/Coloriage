/*
 * Main.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 29/12/2020.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package com.coloriage;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        testExponentiel();
    }

    private static void testExponentiel(){ //bug
        int n = 4;
        int[] couleurs = new int[3];
        for (int i = 0; i < 3; i++){
            couleurs[i] = i;
        }
        System.out.println("couleurs:"+ Arrays.toString(couleurs));

        int[][] tab = new int[(int) Math.pow(3, n)][n];


        for (int k = 0; k < 3; k++){
            for (int j = 0; j < n; j++){
                for(int i = 0; i < Math.pow(3, n); i++){
                    //System.out.println("i:"+i+", j:"+j+", k:"+k);
                    tab[i][j] = couleurs[k];
                }
            }
        }

        for (int i = 0; i < Math.pow(3, n); i++){
            System.out.println(Arrays.toString(tab[i]));
        }
    }
}
