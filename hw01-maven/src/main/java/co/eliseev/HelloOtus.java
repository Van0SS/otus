package co.eliseev;

import com.google.common.math.Stats;

public class HelloOtus {

    public static void main(String[] args) {

        int[] preparedData = convertStringsToInts(args);
        double result = guavaMean(preparedData);
        System.out.println("Mean result is " + result);

    }

    static int[] convertStringsToInts(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Please enter args to calculate guavaMean");
        }

        int[] ints = new int[args.length];

        for (int i = 0; i < args.length; i++) {
            try {
                ints[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Argument must be Integer, but input was: " + args[i]);
            }
        }
        return ints;
    }


    static double guavaMean(int... args) {
        return Stats.meanOf(args);
    }
}
