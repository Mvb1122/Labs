package com.company;

public class Helpers {
    public static int[] search(String input, char selection) {
        // Find the number of characters in the string.
        int numIndexes = 0;
        for (int i = 0; i < input.length(); i++) if (input.charAt(i) == selection) numIndexes++;

        // Create an array to hold the outputted information.
        int[] searchResults = new int[numIndexes];
        

        return searchResults;
    }
}
