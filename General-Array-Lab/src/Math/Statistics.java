// Pulled from my Github at: https://github.com/Mvb1122/Labs/blob/main/Statistics-Lab/src/Math/Statistics.java
package Math;

public class Statistics {
    public static long sum(int[] inputs) {
        long sum = 0;
        for (int value : inputs) sum += value;
        return sum;
    }

    public static double mean(int[] inputs) {
        return ((double) sum(inputs)) / inputs.length;
    }

    public static double STDiv(int[] values) {
        // Find the average of the list of numbers.
        double average = mean(values);

        // Determine the difference of each number from the average, and square each difference.
        double[] squaredDifferences = new double[values.length];
        for (int i = 0; i < squaredDifferences.length; i++) {
            squaredDifferences[i] = Math.pow(values[i] - average, 2);
        }

        // Sum all the differences.
        double differencesSum = 0; for (double value : squaredDifferences) differencesSum += value;


        // Divide this sum by (the number of values - 1).
        differencesSum /= (values.length - 1);

        // Take the square root of the above division problem from step c.
        return Math.sqrt(differencesSum);
    }

    /**
     * Finds the maximum value in an int[].
     * @param values The array you want to search through.
     * @return The maximum value in the array.
     */
    public static int max(int[] values) {
        // Create a variable to hold the maximum.
        int max = 0;

        // Loop through the array, and if an element is greater than the maximum, set the maximum to it.
        for (int value : values) {
            if (value > max) max = value;
        }

        // Return the maximum.
        return max;
    }

    /**
     * Finds the minimum value in an int[].
     * @param values The array you want to search through.
     * @return The minimum value in the array.
     */
    public static int min(int[] values) {
        // Create a variable to hold the minimum.
        int min = 100;

        // Loop through the array, and if an element is less than the minimum, set the minimum to it.
        for (int value : values) {
            if (value < min) min = value;
        }

        // Return the minimum.
        return min;
    }

    public static int mode(int[] values) {
        // Find the mode
        int mode = 0;
        {
            // Create two arrays, one which holds a matching value, and the other which holds the number of matches,
            // as well as a variable to hold the number of matches for the current mode.
            int[] matching = new int[values.length];
            int[] numberOfValues = new int[values.length];
            int numberOfModeMatches = 0;

            for (int i = 0; i < values.length; i++) {
                // Loop through each value in the matching array, and if it's the correct one, increase the corresponding numberOfValues value by one.
                boolean matchFound = false;
                for (int j = 0; j < matching.length; j++) {
                    if (matching[i] == values[i]) {
                        numberOfValues[i]++;
                        matchFound = true;
                        break;
                    }
                }

                // If no match was found, find the first empty index in matching[] and then fill it.
                for (int j = 0; j < matching.length && !matchFound; j++) {
                    if (matching[i] == '\u0000') {
                        matching[i] = values[i];
                        numberOfValues[i]++;
                        break;
                    }
                }
            }

            // Find the most-used number in the list.
            for (int i = 0; i < matching.length; i++) {
                if (numberOfValues[i] > numberOfModeMatches) {
                    mode = matching[i];
                    numberOfModeMatches = numberOfValues[i];
                }
            }
        }
        return mode;
    }
}
