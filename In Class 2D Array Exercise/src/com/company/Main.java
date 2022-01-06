package com.company;

public class Main {

    public static void main(String[] args) {
        String[] countries = new String[]{"Canada", "China", "Germany", "Korea", "Japan", "Russia", "United States"};
	    int[][] medalData = {{1, 0, 1}, {1, 1, 0}, {0, 0, 1}, {1, 0, 0}, {0, 1, 1}, {0, 1, 1}, {1, 1, 0}};
        String[] medalTypes = {"Gold", "Silver", "Bronze"};

        // Find the maximum width of the countries array.
        int maxLength = Integer.MIN_VALUE;
        for (String country : countries) if (country.length() > maxLength) maxLength = country.length();

        // Find the maximum width of the medalTypes array.
        int maxLengthOfTheMedals = Integer.MIN_VALUE;
        for (String medal : medalTypes) if (medal.length() > maxLengthOfTheMedals) maxLengthOfTheMedals = medal.length();

        // Create the format string.
        String formatString = "%-" + maxLength + "s" + "\t\t";
        String medalFormatString = "%" + (3 + maxLengthOfTheMedals) + "s";

        // Print the medal headers
        System.out.printf(formatString, "");
        for (String medalType : medalTypes) System.out.printf(medalFormatString, medalType);
        System.out.printf("%n");

        for (int i = 0; i < countries.length; i++) {
            System.out.printf(formatString, countries[i]);
            for (int j = 0; j < medalData[i].length; j++) System.out.printf(medalFormatString, medalData[i][j]);
            System.out.printf("%n");
        }

        // Calculate and print out the totals.
        System.out.println();
        System.out.printf(formatString, ""); System.out.printf("\t %s%n", "Total");
        for (int i = 0; i < countries.length; i++) {
            int sum = 0;
            for (int j = 0; j < medalData[i].length; j++) sum += medalData[i][j];
            System.out.printf(formatString, countries[i]);
            System.out.printf("\t %d%n", sum);
        }
    }
}