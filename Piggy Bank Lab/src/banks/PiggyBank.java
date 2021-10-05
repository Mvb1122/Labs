package banks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class PiggyBank {
    final static double[] values;
    final static String[][] names;
    int[] coins;

    static {
        values = new double[]{0.01, 0.1, 0.25, 0.5, 1};
        names = new String[][]{{"penny", "pennies"}, {"dime", "dimes"}, {"quarter", "quarters"}, {"half-dollar", "half dollar", "half dollars", "half-dollars"}, {"dollar", "dollars"}};
    }

    public PiggyBank() {
        coins = new int[names.length];
    }

    public PiggyBank(int[] coins) {
        if (coins.length != names.length) {
            System.out.println("Your input coins Array is of an incorrect length!");
        }

        this.coins = new int[names.length];
        System.arraycopy(coins, 0, this.coins, 0, names.length);
    }

    public void add(int numCoins, String coin) {
        for (int i = 0; i < coins.length; i++) {
            if (coin.equals(names[i])) coins[i] += numCoins;
        }
    }

    public void subtract(int numCoins, String coin) {
        this.add(-numCoins, coin);
    }

    public int getCoin(String coin) {
        for (int i = 0; i < coins.length; i++) {
            for (int j = 0; j < names[i].length; i++) {
                if (coin.equals(names[i][j])) return coins[i];
            }
        }
        return 0;
    }

    public double getValue() {
        double value = 0;
        for (int i = 0; i < coins.length; i++) {
            value += coins[i] * values[i];
        }
        return value;
    }

    public PiggyBank(String s) {
        this(parse(s));
    }

    public static int[] parse(String s) {
        // Remove any commas from the input.
        {
            StringBuilder d = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != ',') d.append(s.charAt(i));
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

                System.out.println(parts);
            } catch (IndexOutOfBoundsException e) {
                // See above comments.
                String parts = s.substring(spaceIndexes.get(i)).trim();
                int firstSpaceIndex = getFirstSpaceIndex(parts);
                int numCoins = Integer.parseInt(parts.substring(0, firstSpaceIndex));
                String coin = parts.substring(firstSpaceIndex);
                coins[getCoinIndex(coin.toLowerCase(Locale.ROOT).trim())] += numCoins;
                System.out.println(parts);
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
}
