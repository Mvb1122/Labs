// Pulled from my Github at: https://github.com/Mvb1122/Labs/blob/main/Statistics-Lab/src/com/company/Helpers.java
public class Helpers {
    /**
     * Searches through a string and returns a list of the indexes at which the specified char occurs.
     * @param input The input string to search.
     * @param selection The char to search for.
     * @return An int[] which contains the indexes at which the selection occurs.
     */
    public static int[] search(String input, char selection) {
        // Find the number of characters in the string.
        int numIndexes = numberOf(input, selection);

        // Create an array to hold the outputted information.
        Integer[] searchResults = new Integer[numIndexes];

        // Loop through the input and find the matches.
        for (int i = 0; i < input.length(); i++) if (input.charAt(i) == selection) {
            // Find the first empty index of the array, and put the index into there.
            searchResults[findFirstEmptyIndex(searchResults)] = i;
        }

        // Convert searchResults to an int[] and return it.
        int[] searchedResults = new int[searchResults.length];
        for (int i = 0; i < searchResults.length; i++) {
            searchedResults[i] = searchResults[i];
        }

        return searchedResults;
    }

    /**
     * A function which returns the number of times a character appears in a string.
     * @param s The string to count through.
     * @param c The character to count.
     * @return The number of that character that occurs in the string.
     */
    public static int numberOf(String s, char c) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) if (s.charAt(i) == c) sum++;
        return sum;
    }

    /**
     * A method which finds the first empty index in any non-primitive array.
     * If it doesn't find any empty indexes, it returns the length of the input.
     * @param input The array to search through.
     * @return The first null index.
     */
    public static int findFirstEmptyIndex(Object[] input) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) {
                return i;
            }
        }
        return input.length;
    }

    public static Integer[] intToIntegerArray(int[] j) {
        Integer[] output = new Integer[j.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = j[i];
        }
        return output;
    }

    public static int[] IntegerToIntArray(Integer[] j) {
        int[] output = new int[j.length];
        for (int i = 0; i < j.length; i++) {
            output[i] = j[i];
        }
        return output;
    }

    public static int[] parseToIntArray(String data) {
        int[] values;
        {
            // Find newlines
            int[] newLineIndexes = Helpers.search(data, '\n');

            // Split to int[]
            values = new int[Helpers.findFirstEmptyIndex(Helpers.intToIntegerArray(newLineIndexes))];
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
        return values;
    }

    public static double flatRound(double v, int places) {
        return (double) (int) (v * Math.pow(10, places)) / Math.pow(10, places);
    }
}

