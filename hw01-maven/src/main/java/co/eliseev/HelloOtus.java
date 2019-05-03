package co.eliseev;

import com.google.common.math.Stats;

import java.util.Scanner;

public class HelloOtus {

    public static void main(String[] args) {

        if (hasArguments(args)) {
            calculate(args);
        } else {
            showHelp();
            readArgsByScannerAndCalculate();
        }
    }

    static boolean hasArguments(String[] args) {
        return args != null && args.length > 0;
    }

    private static void calculate(String[] args) {
        try {
            int[] ints = convertStringsToInts(args);
            double result = calculate(ints);
            System.out.println("Mean result is " + result);

        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }

    static void showHelp() {
        System.out.println("______________________________");
        System.out.println("To calculate mean of integers please enter numbers with space delimiters and press ENTER.");
        System.out.println("To quit enter 'q' and press ENTER");
        System.out.println("______________________________");
    }

    private static void readArgsByScannerAndCalculate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            String line = scanner.nextLine();
            if (line.equals("q")) {
                break;
            }

            try {

                int[] ints = convertLineToInts(line);
                double result = calculate(ints);
                System.out.println("Mean result is " + result);

            } catch (IllegalArgumentException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }


    static int[] convertStringsToInts(String[] args) {

        int[] ints = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                ints[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Arguments must be Integer type, but was: " + args[i]);
            }
        }
        return ints;
    }

    static int[] convertLineToInts(String line) {

        if (line == null || line.equals("")) {
            throw new IllegalArgumentException("Your input line is empty. Please enter numbers with space delimiters and press ENTER");
        }
        String[] args = line.split(" ");

        if (args.length == 0) {
            throw new IllegalArgumentException("Please enter args to calculate guavaMean");
        }

        return convertStringsToInts(args);
    }

    private static double calculate(int... args) {
        return Stats.meanOf(args);
    }
}
