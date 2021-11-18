package com.company;

import java.util.Locale;

/**
 * @author Micah Bushman
 * @author Raphael Landau
 */
public class StringUtil {

    /**
     * Takes a string and reverses it.
     * @param input The input string to reverse.
     * @return The string, reversed.
     */
    public static String reverse(String input) {
        //Written by Raphael - note curly brackets
        String reversedInput = "";
        for (int inputLength = input.length() - 1; inputLength != -1; inputLength--) {
            reversedInput += input.charAt(inputLength);
        }

        return reversedInput;
    }

    /**
     * Takes in a string and returns whether it's a palindrome or not.
     * @param input The input string to check.
     * @return True if it's a palindrome, or false if it isn't.
     */
    public static boolean isPalindrome(String input) {
        // Written by Micah

        // Remove spaces and other punctuation.
        input = input.replaceAll(" ", "")
                     .replaceAll(",", "")
                     .replaceAll("\\.","")
                     .replaceAll("\\?", "")
                     .toLowerCase();

        for (int i = 0; i < input.length() / 2; i++)
            if (input.charAt(i) != input.charAt(input.length() - 1 - i)) return false;

        return true;
    }

    /**
     * Converts a String to Pig Latin.
     * @param input The String to convert.
     * @returns The string, converted to Pig Latin.
     */
    public static String toPigLatin(String input) {
        // Raphael Written Code
        // Checks if input has any values
        boolean hasVowels = false;
        boolean startsVowel = false;
        for (int index = 0; index < input.length(); index++) {
            for (char vowel : vowels) {
                if (Character.toLowerCase(input.charAt(index)) == vowel) {
                    hasVowels = true;
                }
            }
        }

        if (!hasVowels) {
            return input + "ay";
        }
        //checks if input starts with a value
        for (char vowel : vowels) {
            if (Character.toLowerCase(input.charAt(0)) == vowel) {
                startsVowel = true;
            }
        }

        if (startsVowel) {
            return input + "yay";
        }

        //to reach this point the input doesn't start with a vowel, and does have vowels
        //checks for index of first vowel
        int firstVowel = -1;
        mainLoop: for (int index = 0; index < input.length(); index++) {
            for (char vowel : vowels) {
                if (Character.toLowerCase(input.charAt(index)) == vowel) {
                    firstVowel = index;
                    break mainLoop;
                }
            }
        }
        String start = input.substring(0,firstVowel);
        String end = input.substring(firstVowel);
        if (Character.isUpperCase(input.charAt(0))) {
            Character.toUpperCase(end.charAt(0));
            Character.toLowerCase(start.charAt(0));
        }

        return end + start + "ay";
    }

    static char[] vowels = new char[]{'a', 'e', 'i', 'o', 'u'};

    /**
     * Converts a string to shorthand by replacing "and", "to", "you", and "for", with "&", "2", "U", and "4",
     * before then removing all vowels.
     * @param input The string to shorten.
     * @return The shortened string.
     */
    public static String toShortHand(String input) {
        //Micah written code, you can tell because it's unreadable
        String output = input.toLowerCase()
                .replaceAll("and", "&")
                .replaceAll("to", "2")
                .replaceAll("you", "U")
                .replaceAll("for", "4");

        for (int i = 0; i < output.length(); i++)
            for (char vowel : vowels)
                if (Character.toLowerCase(output.charAt(i)) == vowel)
                    // Remove the character at i.
                    try {
                        output = output.substring(0, i) + output.substring(i + 1);
                    } catch (StringIndexOutOfBoundsException e) {
                        output = output.substring(0, i);
                    }

        return output;
    }
}