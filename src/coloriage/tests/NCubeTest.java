/*
 * NCubeTest.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 3/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

import coloriage.ncubes.NCube;
import coloriage.ncubes.NCubeVertex;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class NCubeTest {
    @Test
    void build(){
        NCube nCube = new NCube(10);
        for (NCubeVertex v1 : nCube.getNcube()){
            for (NCubeVertex v2 : v1.getNeighbors()){
                assertEquals(1, nCube.compare(v1.getBinaryName(), v2.getBinaryName()));
            }
        }
    }

    @Test
    void compare(){
        NCube ncube = new NCube(2);
        String o = "00";
        String p = "11";
        String k = "10";

        assertEquals(2, ncube.compare(o,p));
        assertEquals(1, ncube.compare(k,p));
        assertEquals(1, ncube.compare(k,o));
    }

    @Test
    void toBinary() {
        NCube ncube = new NCube(8);
        assertEquals("00000110", ncube.toBinary(6));
        assertEquals("00000000", ncube.toBinary(0));
        assertEquals("11111111", ncube.toBinary(255));
    }
}