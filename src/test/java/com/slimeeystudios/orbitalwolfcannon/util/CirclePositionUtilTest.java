package com.slimeeystudios.orbitalwolfcannon.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CirclePositionUtilTest {
    @Test
    void evenlyDistributedXZProducesExpectedCountAndRadius() {
        List<double[]> points = CirclePositionUtil.evenlyDistributedXZ(0.0D, 0.0D, 6.0D, 48);
        assertEquals(48, points.size());

        for (double[] point : points) {
            double distance = Math.sqrt(point[0] * point[0] + point[1] * point[1]);
            assertTrue(Math.abs(distance - 6.0D) < 1.0E-9, "Point is not on expected radius");
        }
    }
}

