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

public class NCubeGenerator {
    private final Map<Integer, NCube> ncubes;

    public NCubeGenerator(){
        ncubes = new HashMap<>();
    }

    private void createCube(int N) throws NCubeException {
        if (N < 8) {
            NCube nCube = new NCube(N);
            ncubes.put(N, nCube);
        }
        else
            throw new NCubeException("Not valid dimension.");
    }

    public NCube getCube(int dimension) throws NCubeException {
        if (ncubes.containsKey(dimension))
            return ncubes.get(dimension);
        else
            throw new NCubeException("Not valid dimension.");
    }
}
