package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Array2DExercises {
    /**
     * Gets the maximum value in an integer array.
     * @param a A 2D Integer Array.
     * @return The maximum value in the inputted array.
     */
    public static int max(int[][] a) {
        int max = Integer.MIN_VALUE;

        for (int[] ints : a)
            for (int anInt : ints)
                if (max < anInt) max = anInt;

        return max;
    }

    /**
     * Gets the sum of a specified <strong>ROW.</strong>
     * @param a The integer array to sum from.
     * @param x The row to get the sum of.
     * @return The sum of the specified row.
     */
    public static int rowSum(int[][] a, int x) {
        int total = 0;

        for (int anInt : a[x])
            total += anInt;

        return total;
    }

    /**
     * Gets the sum of a specified <strong>COLUMN.</strong>
     * @param a The integer array to sum from.
     * @param x The column to get the sum of.
     * @return The sum of the specified column.
     */
    public static int columnSum(int[][] a, int x) {
        int total = 0;

        for (int[] ints : a) {
            try {
                total += ints[x];
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }

        return total;
    }

    /**
     * Gets the sum of the entire 2D integer array.
     * @param a The integer array to sum.
     * @return The total of each array, summed.
     */
    public static int[] allRowSums(int[][] a) {
        int[] output = new int[a.length];
        Thread[] threads = new Thread[a.length];

        // Uses multithreading to go fast.
        for (int i = 0; i < output.length; i++) {
            final int finali = i;
            threads[i] = new Thread(() -> {
                output[finali] = rowSum(a, finali);
            });
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignored) {;} // Do nothing.
        }

        return output;
    }


    /**
     * Checks if all rows have the same sum.
     * @param a The integer array to test.
     * @return True if the array is magic, false if it isn't.
     */
    public static boolean isRowMagic(int[][] a) {
        int previousSum = rowSum(a, 0);
        for (int i = 1; i < a.length; i++) {
            int thisRowSum = rowSum(a, i);
            if (previousSum != thisRowSum) return false;
            previousSum = thisRowSum;
        }
        return true;
    }

    /**
     * Checks if all columns have the same sum.
     * @param a The integer array to test.
     * @return True if the array is magic, false if it isn't.
     */
    public static boolean isColumnMagic(int[][] a) {
        int previousSum = columnSum(a, 0);
        for (int i = 1; i < a.length; i++) {
            int thisColumnSum = columnSum(a, i);
            if (previousSum != thisColumnSum) return false;
            previousSum = thisColumnSum;
        }
        return true;
    }

    /**
     * Checks if every row's length is equal to the length of the entire array.
     * @param a The array to check.
     * @return True if the array is square.
     */
    public static boolean isSquare(int[][] a) {
        for (int[] ints : a)
            if (ints.length != a.length) return false;

        return true;
    }

    /**
     * "Checks if the array is a magic square. This means that it must be square, and that all row sums, all column sums, and the two diagonal-sums must all be equal."
     *      -- Lab instructions
     * @param a The array to check.
     * @return True if it is magic, false if it isn't.
     */
    public static boolean isMagic(int[][] a) {
        boolean isSquare = isSquare(a);
        boolean areColumnsMagic = isColumnMagic(a);
        boolean areRowsMagic = isRowMagic(a);

        // Compute diagonal-sums if the array is square.
        if (isSquare && areColumnsMagic && areRowsMagic) {
            int firstDiag = diagSum(a);
            int revDiag = revDiagSum(a);
            if (firstDiag == revDiag) return true;
        }
        return false;
    }

    /**
     * Does a diagonal sum from the top left to the bottom right corner.
     * @param a The array to compute.
     * @return The value of the diagonal sum.
     */
    private static int diagSum(int[][] a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i][i];
        }
        return sum;
    }

    /**
     * Does a diagonal sum from the bottom left to the top right corner.
     * @param a The array to compute.
     * @return The value of the diagonal sum.
     */
    private static int revDiagSum(int[][] a) {
        int sum = 0;
        for (int i = a.length - 1; i != -1; i--) {
            sum += a[i][a[i].length - 1 - i];
        }
        return sum;
    }

    /**
     * Tests if an array is sequential; eg, that all values from 1 to the maximum possible are present in the array.
     * @param inputs The array to check.
     * @return True if the array is sequential, false if it's not square or if it's nonsequential.
     */
    public static boolean isSequence(int[][] inputs) {
        int[][] basic = new int[inputs.length][];

        // Force a deep copy of the inputs.
        for (int i = 0; i < inputs.length; i++) for (int j = 0; j < inputs[i].length; j++) {
            if (basic[i] == null) return false;
            basic[i][j] = Integer.valueOf(String.valueOf(inputs[i][j]));
        }

        // Make sure that the array is square.
        if (!isSquare(basic)) return false;

        // Create values.
        ArrayList<Integer> values = new ArrayList<Integer>(1);
        {
            int v = 1;
            for (int[] Ints : basic)
                for (int j = 0; j < Ints.length; j++) {
                    values.add(v);
                    v++;
                }
        }

        // Check for each value's presence
        int presentValues = 0;
        valueLoop:
        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);

            // Loop through the entire array.
            for (int x = 0; x < basic.length; x++) for (int y = 0; y < basic[x].length; y++) {
                if (value == basic[x][y]) {
                    presentValues++;
                    basic[x][y] = -1; // I set the value to -1, which would put it out of the sequence, so duplicate values will cause the function to fail.
                    continue valueLoop;
                }
            }
        }

        // If the number of present values is equal to the number of values, return true. Else, return false.
        return presentValues == values.size();
    }

    /**
     * I have no idea what this does but it passes the tests so..?
     * @param inputs The array to check.
     * @return True if the array is latin, false if it's not, or it's not square.
     */
    public static boolean isLatin(int[][] inputs) {
        int[][] a = new int[inputs.length][];
        System.arraycopy(inputs, 0, a, 0, inputs.length);

        // Make sure that the array is square.
        if (!isSquare(a)) return false;

        // Check horizontally
        int rowsThatAreSequential = 0;
        boolean horizontalSequential = false;
        for (int[] aRow : a) {
            int[] Ints = new int[aRow.length];
            System.arraycopy(aRow, 0, Ints, 0, aRow.length);

            // Check if each row contains the values that make it latin.
                // Generate values.
            int[] values = new int[Ints.length];
            for (int i = 1; i < values.length + 1; i++) values[i - 1] = i;
                // Check values.
            int presentValues = 0;

            arrayLoop:
            for (int check : values) {
                for (int j = 0; j < Ints.length; j++) {
                    int val = Ints[j];
                    if (val == check) {
                        presentValues++;
                        Ints[j] = -1;
                        continue arrayLoop;
                    }
                }
            }

            // Increase the number of sequentialHorizontalRows by one.
            if (presentValues == values.length) rowsThatAreSequential++;
        }
        if (rowsThatAreSequential == a.length) horizontalSequential = true;

        // Check vertically
        int columnsThatAreSequential = 0;
        boolean verticalSequential = false;
        for (int i = 0; i < a.length; i++) {
            ArrayList<Integer> Ints = new ArrayList<>(1);
            // Add the values of the column into it.
            for (int[] ints : a)
                Ints.add(ints[i]);


            // Generate the list of values that we need.
            int[] values = new int[Ints.size()];
            for (int j = 1; j < values.length + 1; j++) values[j - 1] = j;

            // Check values.
            int presentValues = 0;

            arrayLoop:
            for (int j = 0; j < values.length; j++) {
                int check = values[j];

                for (int y = 0; y < Ints.size(); y++) {
                    int val = Ints.get(y);
                    if (val == check) {
                        presentValues++;
                        Ints.set(y, -1);
                        continue arrayLoop;
                    }
                }
            }

            if (presentValues == values.length) columnsThatAreSequential++;
        }
        if (columnsThatAreSequential == a.length) verticalSequential = true;

        // If the columns and rows are sequential, return true. Else, return false.
        return verticalSequential && horizontalSequential;
    }
}
