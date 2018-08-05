package util;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import util.vectors.Vec3d;

public abstract class MathUtils {

    public static int ceil(double x) {
        return (int) Math.ceil(x);
    }

    public static double clamp(double x, double lower, double upper) {
        return Math.max(lower, Math.min(x, upper));
    }

    public static int clamp(int x, int lower, int upper) {
        return Math.max(lower, Math.min(x, upper));
    }

    public static int floor(double x) {
        return (int) Math.floor(x);
    }

    public static double mod(double x, double m) {
        return (x % m + m) % m;
    }

    public static int mod(int x, int m) {
        return (x % m + m) % m;
    }

    public static Vec3d randomInSphere(Random random) {
        while (true) {
            Vec3d v = new Vec3d(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
            if (v.lengthSquared() <= 1) {
                return v;
            }
        }
    }

    public static Vec3d vecMap(Vec3d v1, Function<Double, Double> f) {
        return new Vec3d(f.apply(v1.x), f.apply(v1.y), f.apply(v1.z));
    }

    public static Vec3d vecMap(Vec3d v1, Vec3d v2, BiFunction<Double, Double, Double> f) {
        return new Vec3d(f.apply(v1.x, v2.x), f.apply(v1.y, v2.y), f.apply(v1.z, v2.z));
    }

    public static float[] vecsToArray(List<Vec3d> vecs) {
        float[] r = new float[3 * vecs.size()];
        for (int i = 0; i < vecs.size(); i++) {
            r[3 * i] = (float) vecs.get(i).x;
            r[3 * i + 1] = (float) vecs.get(i).y;
            r[3 * i + 2] = (float) vecs.get(i).z;
        }
        return r;
    }
}
