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
