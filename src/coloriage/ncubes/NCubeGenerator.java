/*
 * NCubeGenerator.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 1/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package coloriage.ncubes;

import coloriage.exceptions.NCubeException;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton qui contient la liste des N-Cubes de dimensions de 1 à 8.
 */
public class NCubeGenerator {
    /**
     * Map des N-Cubes dont la clé est le dimension.
     */
    private final Map<Integer, NCube> ncubes;

    /**
     * Instance partagée.
     */
    private static final NCubeGenerator instance = new NCubeGenerator();

    /**
     * Crée tous les N-Cubes jusqu'à la dimension 8.
     */
    private NCubeGenerator(){
        ncubes = new HashMap<>();
        for (int i = 1; i <= 8; i++)
            createCube(i);
    }

    /**
     * Crée un N-Cube de dimension N.
     * @param N dimension.
     */
    private void createCube(int N) {
        NCube nCube = new NCube(N);
        ncubes.put(N, nCube);
    }

    public NCube getCube(int dimension) throws NCubeException {
        if (ncubes.containsKey(dimension))
            return ncubes.get(dimension);
        else
            throw new NCubeException("La dimension n'est pas valide (doit être <8).");
    }

    public static NCubeGenerator getInstance() {
        return instance;
    }
}
