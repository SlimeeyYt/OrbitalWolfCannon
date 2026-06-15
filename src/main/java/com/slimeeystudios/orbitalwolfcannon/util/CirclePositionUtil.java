package com.slimeeystudios.orbitalwolfcannon.util;

import java.util.ArrayList;
import java.util.List;

public final class CirclePositionUtil {
    private CirclePositionUtil() {
    }

    public static List<double[]> evenlyDistributedXZ(double centerX, double centerZ, double radius, int count) {
        List<double[]> positions = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2.0D * i) / count;
            double x = centerX + radius * Math.cos(angle);
            double z = centerZ + radius * Math.sin(angle);
            positions.add(new double[]{x, z});
        }

        return positions;
    }
}

