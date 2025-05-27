package io.binarskugga.math;


import static io.binarskugga.Constants.LOMONT_FAST_INVSQRT;


public class StaticMath {
    private static final float SIN_COS_TABLE_PRECISION = 1000f;
    private static final int SIN_COS_TABLE_LENGTH = 360 * (int) SIN_COS_TABLE_PRECISION;
    private static float[] sinTable;
    private static float[] cosTable;

    public static void computeSinTable() {
        sinTable = new float[SIN_COS_TABLE_LENGTH];
        for(int i = 0; i < SIN_COS_TABLE_LENGTH; i ++) {
            sinTable[i] = (float) Math.sin(Math.toRadians(i / SIN_COS_TABLE_PRECISION));
        }
    }

    public static void computeCosTable() {
        cosTable = new float[SIN_COS_TABLE_LENGTH];
        for(int i = 0; i < SIN_COS_TABLE_LENGTH; i ++) {
            cosTable[i] = (float) Math.cos(Math.toRadians(i / SIN_COS_TABLE_PRECISION));
        }
    }

    public static float sin(float angle) {
        angle = Math.abs(angle);
        return sinTable[(int) Math.floor((angle * SIN_COS_TABLE_PRECISION)) % SIN_COS_TABLE_LENGTH];
    }

    public static float cos(float angle) {
        angle = Math.abs(angle);
        return cosTable[(int) Math.floor((angle * SIN_COS_TABLE_PRECISION) % SIN_COS_TABLE_LENGTH)];
    }

    public static boolean floatCompare(float a, float b, float epsilon) {
        return Math.abs(a - b) <= epsilon * Math.max(1.0f, Math.max(Math.abs(a), Math.abs(b)));
    }

    public static boolean floatCompare(float a, float b) {
        return floatCompare(a, b, Float.MIN_VALUE);
    }

    public static float invsqrt(float a) {
        float xhalf = 0.5f * a;
        int i = Float.floatToIntBits(a);
        i = LOMONT_FAST_INVSQRT - (i >> 1);
        a = Float.intBitsToFloat(i);
        a *= (1.5f - xhalf * a * a);
        return a;
    }

    public static float sqrt(float a) {
        return 1.0f / invsqrt(a);
    }

    public static double invsqrt(double a) {
        double xhalf = 0.5 * a;
        long i = Double.doubleToLongBits(a);
        i = LOMONT_FAST_INVSQRT - (i >> 1);
        a = Double.longBitsToDouble(i);
        a *= (1.5 - xhalf * a * a);
        return a;
    }

    public static double sqrt(double a) {
        return 1.0 / invsqrt(a);
    }
}
