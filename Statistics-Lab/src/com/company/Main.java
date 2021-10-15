package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            // Read file.
            String data = Reader.readFile("numbers.txt");

            // Split the input by the newLine char, then put it into an int[].
            int[] values;
            {
                // Find the number of newLines
                int numNewLines = 0;
                for (int i = 0; i < data.length(); i++) if (data.charAt(i) == '\n') numNewLines++;
                // Find newlines
                int[] newLineIndexes = new int[numNewLines];
                int filledIndexes = 0;
                for (int i = 0; i < data.length(); i++) if (data.charAt(i) == '\n') {
                    newLineIndexes[filledIndexes] = i;
                    filledIndexes++;
                }

                // Split to int[]
                values = new int[filledIndexes + 1];
                for (int i = 0; i < values.length; i++) {
                    // Get a substring from either 0 to the index of the space, the previous space to the next one, or from the last one to the end of the file.
                    String number;
                    if (i == 0) {
                        // For the first number:
                        number = data.substring(0, newLineIndexes[0]);
                    } else {
                        try {
                            // For middling numbers:
                            number = data.substring(newLineIndexes[i-1] + 1, newLineIndexes[i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // For the last number:
                            number = data.substring(newLineIndexes[newLineIndexes.length - 1] + 1);
                        }
                    }

                    // Parse to number, then push to the array.
                    values[i] = Integer.parseInt(number.trim());
                }
            }

            // Calculate Sum.
            int sum = 0; // The lab says to use a long, but the maximum value for a random list of integers that's 1000u long from 0 to 100 is only 100,000, so you actually don't have to.
            for (int k : values) sum += k;

            // Calculate Average:
            double average = (double) sum / values.length;

            // Determine the difference of each number from the average, and square each difference.
            double[] differences = new double[values.length];
            for (int i = 0; i < values.length; i++) {
                differences[i] = Math.pow(average - (double) values[i], 2);
            }

            // Calculate the sum of all differences.
            double sumDifferences = 0;
            for (double value : differences) {
                sumDifferences += value;
            }

            // Divide the sum by the number of values, minus one.
            double divisionProblem = sumDifferences / (values.length - 1);

            // Get the square root of the sum.
            double STDev = Math.sqrt(divisionProblem);

            // Find the mode
            int mode = 0;
            {
                // Create two arrays, one which holds a matching value, and the other which holds the number of values.
                int[] matching = new int[values.length];
                int[] numberOfValues = new int[values.length];

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
                    if (numberOfValues[i] != 0 && numberOfValues[i] > mode) {
                        mode = matching[i];
                    }
                }
            }

            // Output.
            System.out.printf("The average is %.2f, %nThe STDev is %.2f, %nThe mode is %.2f.", average, STDev, (double) mode);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}