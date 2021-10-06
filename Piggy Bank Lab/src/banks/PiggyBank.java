package banks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (coins.length != names.length) {
            System.out.println("Your input coins Array is of an incorrect length!");
        }

        this.coins = new int[names.length];
        System.arraycopy(coins, 0, this.coins, 0, names.length);
    }

    /**
     * Adds a specified number of coins to the bank.
     * @param numCoins The number of coins to add.
     * @param coin A string which decides the type of coin, either Pennies, Nickels, Dimes, Quarters, Half-Dollars, or dollars.
     */
    public void add(int numCoins, String coin) {
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < names[i].length; i++) {
                if (coin.equals(names[i][j])) coins[i] += numCoins;
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
        this.add(-numCoins, coin);
    }

    /**
     * Subtracts any number of coins directly from the bank.
     * @param coins An array holding the values of coins to remove in the following order: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     */
    public void subtract(int[] coins) {
        for (int i = 0; i < coins.length; i++) {
            coins[i] = -coins[i];
        }
        this.add(coins);
    }

    /**
     * Adds any number of coins directly from the bank.
     * @param coins An array holding the values of coins to add in the following order: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     */
    public void add(int[] coins) {
        for (int i = 0; i < this.coins.length; i++) {
            this.coins[i] += coins[i];
            if (this.coins[i] < 0) this.coins[i] = 0;
        }
    }

    /**
     * Gets the number of a coin in a bank.
     * @param coin The coin type to remove, any of the following: Pennies, Nickels, Dimes, Quarters, Half-Dollars, and dollars.
     * @return The number of the specified coin in the bank.
     */
    public int getCoin(String coin) {
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
        double value = 0;
        for (int i = 0; i < coins.length; i++) {
            value += coins[i] * values[i];
        }
        return value;
    }

    /**
     * Generates a bank from a string of "natural" language.
     * @param s A string which contains a set of coins, see the <a href="https://github.com/Mvb1122/Labs/blob/main/Piggy%20Bank%20Lab/Examples.md">examples</a> for examples.
     */
    public PiggyBank(String s) {
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

                // System.out.println(parts);
            } catch (IndexOutOfBoundsException e) {
                // See above comments.
                String parts = s.substring(spaceIndexes.get(i)).trim();
                int firstSpaceIndex = getFirstSpaceIndex(parts);
                int numCoins = Integer.parseInt(parts.substring(0, firstSpaceIndex));
                String coin = parts.substring(firstSpaceIndex);
                coins[getCoinIndex(coin.toLowerCase(Locale.ROOT).trim())] += numCoins;
                break;
            }
        }
        return coins;
    }

    private static int getFirstSpaceIndex(String parts) {
        for (int i = 0; i < parts.length(); i++) {
            if (parts.charAt(i) == ' ') {
                return i;
            }
        }
        return (int) Double.POSITIVE_INFINITY;
    }

    private static int getCoinIndex(String s) {
        for (int i = 0; i < names.length; i++) {
            for (int  j = 0; j < names[i].length; j++) {
                if (s.equals(names[i][j])) return i;
            }
        }
        return (int) Double.POSITIVE_INFINITY;
    }

    public void set(String coin, int value) {
        for (int i = 0; i < names.length; i++) {
            for (int  j = 0; j < names[i].length; i++) {
                if (coin.equals(names[i][j])) coins[i] = value;
            }
        }
    }

    public void set(int[] input) {
        if (input.length != names.length) System.out.println("Your input array is not the correct length! (" + input.length + " != " + names.length + ")");
        else this.coins = input;
    }

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

        String output = "This piggy bank has ";
        if (!empty) for (int i = 0; i < names.length; i++) {
            if (coins[i] > 0) {
                // If this is the last filled coin, and there are more than 1 filled slots, put an "and".
                if (lastFilledCoin == i && moreThanOneFilledSlot) output += " and ";

                output += coins[i] + " ";

                // Add the plural form of the name if it's greater than 1.
                if (coins[i] == 1) output += names[i][0];
                else output += names[i][1];

                // If this is the last coin, put a `.`
                if (lastFilledCoin == i) output += ". ";
                else output += ", ";
            }
        }
        else output += "nothing. ";

        output += "This gives it a value of $" + getValue() + ".";
        return output; // "This piggy bank has " + getCoin("pennies") + " pennies, " + getCoin("nickels") + " nickels, " + getCoin("dimes") + " dimes, and " + getCoin("quarters") + " quarters. It has a value of $" + getValue() + ".";
    }
}
