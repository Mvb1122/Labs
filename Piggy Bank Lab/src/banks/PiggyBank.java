package banks;

import java.util.ArrayList;
import java.util.Locale;

public class PiggyBank {
    // Create arrays to hold the values of the coins, and their nouns.
    final static double[] values;
    final static String[][] names;

    // Create an array to hold a bank's coins.
    int[] coins;

    static {
        // Statically initialize the arrays for nouns and coin values.
        /*
         The way I have this setup, each String[] in `names` is equivalent to an index in values.
         Then, in each String[] on names, I have the singular name before the plural name.
         In the case of the half-dollar, I've allowed for four different nouns, all of which work.
        */
        values = new double[]{0.01, 0.05, 0.1, 0.25, 0.5, 1};
        names = new String[][]{{"penny", "pennies"}, {"nickel", "nickels"}, {"dime", "dimes"}, {"quarter", "quarters"}, {"half-dollar", "half dollars", "half dollar", "half-dollars"}, {"dollar", "dollars"}};
    }

    /**
     * Create an empty bank.
     */
    public PiggyBank() {
        coins = new int[names.length];
    }

    /**
     * Creates a bank from an array of coins.
     * @param coins An array of coins in the following order: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     */
    public PiggyBank(int[] coins) {
        // Ensure that the input array is of the correct length, since being the wrong length would mean that values would be mis-assigned and that errors may be thrown.
        if (coins.length != names.length) {
            System.out.println("Your input coins Array is of an incorrect length!");
        }

        // Assume that everything's fine and copy the input array into the bank's array.
        this.coins = new int[names.length];
        System.arraycopy(coins, 0, this.coins, 0, names.length);
    }

    /**
     * Adds a specified number of coins to the bank.
     * @param numCoins The number of coins to add.
     * @param coin A string which decides the type of coin, either Pennies, Nickels, Dimes, Quarters, Half-Dollars, or dollars.
     */
    public void add(int numCoins, String coin) {
        // Loop through the names specified, on two axis, and if a match is found, add the value.
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < names[i].length; i++) {
                if (coin.equals(names[i][j])) coins[i] += numCoins;
                // If the value turns out to be less than zero, since you can't have negative coins, we just set it *to* zero.
                if (this.coins[i] < 0) this.coins[i] = 0;
            }
        }
    }

    /**
     * Subtracts a specified number of coins from the bank.
     * @param numCoins The number of coins to remove.
     * @param coin A string which decides the type of coin, either Pennies, Nickels, Dimes, Quarters, Half-Dollars, or dollars.
     */
    public void subtract(int numCoins, String coin) {
        // You might call this laziness, I call it efficiency.
        this.add(-numCoins, coin);
    }

    /**
     * Subtracts any number of coins directly from the bank.
     * @param coins An array holding the values of coins to remove in the following order: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     */
    public void subtract(int[] coins) {
        // First, I flip all the values in the input array, so I have negative coins which I can add to the positive ones, in effect subtracting them.
        for (int i = 0; i < coins.length; i++) {
            coins[i] = -coins[i];
        }
        // Then, I call the add method using the coins, and it removes the specified coins.
        this.add(coins);
    }

    /**
     * Adds any number of coins directly from the bank.
     * @param coins An array holding the values of coins to add in the following order: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     */
    public void add(int[] coins) {
        // This method loops through the inputted array and assigns each of its values to the corresponding values in the
        // object's array.
        for (int i = 0; i < this.coins.length; i++) {
            this.coins[i] += coins[i];
            // Once again, since we can't have negative coins, we just set the value to zero if it falls below zero.
            if (this.coins[i] < 0) this.coins[i] = 0;
        }
    }

    /**
     * Gets the number of a coin in a bank.
     * @param coin The coin type to remove, any of the following: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     * @return The number of the specified coin in the bank.
     */
    public int getCoin(String coin) {
        // This method loops through all of the names of coins and, if a match is found, returns the number of those coins which the bank has stored.
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < names[i].length; j++) {
                if (coin.toLowerCase(Locale.ROOT).equals(names[i][j])) return coins[i];
            }
        }
        return 0;
    }

    /**
     * Gets the value, in dollars, of the bank.
     * @return The value in dollars of the bank's contents.
     */
    public double getValue() {
        // This method loops through the coins in the bank and adds their values to a total before returning it.
        double value = 0;
        for (int i = 0; i < coins.length; i++) {
            // Since the index of a coin corresponds to an index on names, and since each index there corresponds to a value,
            // we can find the value of the coin by multiplying its amount times its value.
            value += coins[i] * values[i];
        }

        // Patch: Fix weird decimal results involving 116 dimes.
        // (Basically, I force it to cut the decimals at the hundredth's place and then send that value along.)
        return ((double) (int) (value * 100)) / 100;
    }

    /**
     * Generates a bank from a string of "natural" language.
     * @param s A string which contains a set of coins, see the <a href="https://github.com/Mvb1122/Labs/blob/main/Piggy%20Bank%20Lab/Examples.md">examples</a> for examples.
     */
    public PiggyBank(String s) {
        // Click that github link and you can see examples (and rules) for inputting to parse().
        this(parse(s));
    }

    public static int[] parse(String s) {
        // Remove any commas or new-lines from the input.
        {
            StringBuilder d = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != ',' && s.charAt(i) != '\n') d.append(s.charAt(i));
            }
            s = d.toString();
        }

        // Create an ArrayList to hold the indexes of spaces found in the string
        ArrayList<Integer> spaceIndexes = new ArrayList<>();
        spaceIndexes.add(0);

        // Find Spaces
        {
            // Convert the input string to an array.
            char[] characters = new char[s.length()];
            for (int i = 0; i < characters.length; i++) characters[i] = s.charAt(i);

            // Find Spaces
            for (int i = 0; i < s.length(); i++) {
                if (characters[i] == ' ') {
                    spaceIndexes.add(i);
                }
            }
        }

        // Loop through every third space.
        // Create an Array to hold the outputted coins.
        int[] coins = new int[names.length];

        for (int i = 0; i < spaceIndexes.size(); i += 2) {
            // Note, for the last loop, it just gets a substring from the last known space to the end.
            try {
                // Make a substring from the index of the previous space (or start) to the next space (or end).
                String parts = s.substring(spaceIndexes.get(i), spaceIndexes.get(i + 2)).trim();

                // Get the index of the first space.
                int firstSpaceIndex = getFirstSpaceIndex(parts);

                // Make an int to hold the number of coins of the type.
                String number = parts.substring(0, firstSpaceIndex);
                // System.out.println(parts);
                int numCoins = Integer.parseInt(number);

                // Make a Substring to hold the number of coins.
                String coin = parts.substring(firstSpaceIndex);

                // Increase the coin in the array by the value of the numCoins.
                coins[getCoinIndex(coin.toLowerCase(Locale.ROOT).trim())] += numCoins;
            } catch (IndexOutOfBoundsException e) {
                // This does the same thing as lines 176 to 191, so just look at those comments for the information.
                String parts = s.substring(spaceIndexes.get(i)).trim();
                int firstSpaceIndex = getFirstSpaceIndex(parts);
                int numCoins = Integer.parseInt(parts.substring(0, firstSpaceIndex));
                String coin = parts.substring(firstSpaceIndex);
                coins[getCoinIndex(coin.toLowerCase(Locale.ROOT).trim())] += numCoins;
                break;
            }
        }

        // Finally, return the int[] which contains the coins.
        return coins;
    }

    private static int getFirstSpaceIndex(String parts) {
        // This method just returns the index of the first space in a string, and that's it.
        for (int i = 0; i < parts.length(); i++) {
            if (parts.charAt(i) == ' ') {
                return i;
            }
        }

        // If it fails to find a space, it just returns a really large number, which causes an error in the code that calls this method.
        return (int) Double.POSITIVE_INFINITY;
    }

    private static int getCoinIndex(String s) {
        // This method matches the name of a coin to its index on names.
        for (int i = 0; i < names.length; i++) {
            for (int  j = 0; j < names[i].length; j++) {
                if (s.equals(names[i][j])) return i;
            }
        }
        // If it fails to find a match, it causes the calling code to throw an error.
        return (int) Double.POSITIVE_INFINITY;
    }

    /**
     * Sets the banks number of held coins for a type to the specified value.
     * @param coin The coin type you wish to set.
     * @param value The number of coins you want to set it to.
     */
    public void set(String coin, int value) {
        // This method just sets the number of coins in the bank to be an inputted value by matching the input string and then setting the match to the value.
        for (int i = 0; i < names.length; i++) {
            for (int  j = 0; j < names[i].length; i++) {
                if (coin.equals(names[i][j])) coins[i] = value;
            }
        }
    }

    /**
     * Directly sets the number of coins in the bank to the specified values.
     * @param input An array holding the values of coins to add in the following order: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     */
    public void set(int[] input) {
        // Give a warning if the input's the wrong length.
        if (input.length != names.length) System.out.println("Your input array is not the correct length! (" + input.length + " != " + names.length + ")");
            // Else, it just copies the values from the input over to the object's array.
        else this.coins = input;
    }

    /**
     * A method which gets a printout of the bank's info.
     * @return Some data about the bank, including the number of coins it has and its value.
     */
    public String toString() {
        // Find the last filled coin.
        int lastFilledCoin = 0;
        for (int i = coins.length - 1; i >= 0; i--) {
            if (coins[i] != 0) {
                lastFilledCoin = i;
                break;
            }
        }

        // Check if there's more than 1 filled slot and if the bank is empty.
        boolean moreThanOneFilledSlot = false;
        boolean empty = false;
        {
            int filledSlots = 0;
            for (int i = 0; i < coins.length; i++) {
                if (coins[i] != 0) filledSlots++;
            }
            if (filledSlots > 2) moreThanOneFilledSlot = true;
            if (filledSlots == 0) empty = true;
        }

        // Create a string to hold the information about the bank.
        String output = "This piggy bank has ";

        // If the bank's not empty, loop through the names and output information about each coin.
        if (!empty) for (int i = 0; i < names.length; i++) {
            // Skip empty coins.
            if (coins[i] > 0) {
                // If this is the last filled coin, and there are more than 1 filled slots, put an "and".
                if (lastFilledCoin == i && moreThanOneFilledSlot) output += "and ";

                output += coins[i] + " ";

                // Add the plural form of the name if it's greater than 1.
                if (coins[i] == 1) output += names[i][0];
                else output += names[i][1];

                // If this is the last coin, put a `.`
                if (lastFilledCoin == i) output += ". ";
                else output += ", ";
            }
        }
            // If the bank's empty, put "nothing" instead.
        else output += "nothing. ";

        // Add information about the bank's value and return the string.
        output += "This gives it a value of $" + padPrecision(getValue(), 2) + ".";
        return output;
    }

    /**
     * A custom method which pads a double's value out to the specified number of places.
     * @param d The double you want to pad.
     * @param places The number of places to pad to.
     * @return A string containing the double's new value.
     */
    private static String padPrecision(double d, int places) {
        // Flat-round the double to the specified precision.
        d = (double) (int) (d * Math.pow(10, places)) / Math.pow(10, places);

        // Convert the double to a string, so I can find the index of the decimal.
        String s = String.valueOf(d);

        // Find the index of the decimal.
        int decimalIndex = s.indexOf(".");
        int newLength = decimalIndex + places;

        // Cut a substring, which should start at the beginning, then run until the decimal, plus the number of places.
        s = s.substring(0, newLength);

        // Add zeroes onto the end of the input until it's the correct length.
        while (s.length() < newLength) s += "0";

        // Return the extended string.
        return s;
    }
}
