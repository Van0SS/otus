package co.eliseev.etest;

public class CommonUtils {

    public static Byte[] wrap(byte[] bytes) {
        Byte[] wrappedBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            wrappedBytes[i] = bytes[i];
        }
        return wrappedBytes;
    }

    public static Short[] wrap(short[] shorts) {
        Short[] wrappedShorts = new Short[shorts.length];
        for (int i = 0; i < shorts.length; i++) {
            wrappedShorts[i] = shorts[i];
        }
        return wrappedShorts;
    }

    public static Integer[] wrap(int[] ints) {
        Integer[] integers = new Integer[ints.length];
        for (int i = 0; i < ints.length; i++) {
            integers[i] = ints[i];
        }
        return integers;
    }

    public static Long[] wrap(long[] longs) {
        Long[] wrappedLongs = new Long[longs.length];
        for (int i = 0; i < longs.length; i++) {
            wrappedLongs[i] = longs[i];
        }
        return wrappedLongs;
    }

    public static Float[] wrap(float[] floats) {
        Float[] wrappedFloats = new Float[floats.length];
        for (int i = 0; i < floats.length; i++) {
            wrappedFloats[i] = floats[i];
        }
        return wrappedFloats;
    }

    public static Double[] wrap(double[] doubles) {
        Double[] wrappedDoubles = new Double[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            wrappedDoubles[i] = doubles[i];
        }
        return wrappedDoubles;
    }

    public static Character[] wrap(char[] chars) {
        Character[] characters = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            characters[i] = chars[i];
        }
        return characters;
    }

    public static Boolean[] wrap(boolean[] booleans) {
        Boolean[] wrappedBooleans = new Boolean[booleans.length];
        for (int i = 0; i < booleans.length; i++) {
            wrappedBooleans[i] = booleans[i];
        }
        return wrappedBooleans;
    }
}
