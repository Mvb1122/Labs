package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static String password;
    public static Thread[] solvers;

    public static void main(String[] args) {
	    // Get the user's password and maximum guess length.
        Scanner s = new Scanner(System.in);
        System.out.println("Enter your password: ");
        password = s.nextLine();

        // Get the user's maximum guess length.
        int guessLength = 0;

        System.out.println("Enter your maximum guess length, or rather, how long the password should be searched for.");
        System.out.println("You can also enter zero for automatic setting.");
        while (true) {
            try {
                guessLength = s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You have to enter a number.");
                s.next();
                continue;
            }

            if (guessLength == 0) guessLength = password.length();

            if (guessLength < password.length()) {
                System.out.println("Your guess length has to be at least as long as your password itself in order for this to work!");
                System.out.println("Enter it again (or 0):");
            } else break;
        }
        s.close();

        // Create an array to list every possible password's check, where each index is found by concatenating the unicode addresses of each character together.
            // Note, the length of the array is found by multiplying the factorial of the length of the guess, by the number of digits per tag, which is 4 hex digits, or 4396 decimal digits.
        long numSolutions = factorial(guessLength) * 65535L;
        System.out.println("Checking " + numSolutions + " solutions.");

        // Startup threads to check-- one for each core.
        int numCores = Runtime.getRuntime().availableProcessors();
        solvers = new Thread[numCores];
        for (int i = 0; i < numCores; i++) {
            long lowerBound = i * (numSolutions / numCores);
            long boundWidth = numSolutions / numCores;
            solvers[i] = new Thread(new solverThread(lowerBound, lowerBound + boundWidth), "Solver # " + 1 + i);
        }

        // Note, to fix an error where the password is found before the threads are made, we have to wait until the threads have been assigned to start them.
        for (Thread so : solvers) so.start();

        // Start a thread to report progress.
        Thread reporter = new Thread(() -> {
            while (!solverThread.complete) {
                System.out.printf("Tries: %s%n", solverThread.numChecks);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Reporter");
        reporter.start();
    }

    // This method just computes the factorial of a number.
    private static int factorial(int x) {
        for (int i = x - 1; i > 1; i--) {
            x *= i;
        }
        return x;
    }

    /**
     * This class makes it easy for me to create threads to solve with.
     */
    private static class solverThread implements Runnable {
        long lowerBound, upperBound;
        static long numChecks;
        public static boolean complete;

        // Note: Synchronized to prevent data corruption.
        synchronized static void incrementNumChecks() {
            numChecks++;
        }

        /**
         * Creates a new SolverThread which checks from the lower bound up to the upper bound.
         * @param lower The point to start checking at.
         * @param upper The point to stop checking at.
         */
        public solverThread(long lower, long upper) {
            lowerBound = lower;
            upperBound = upper;
        }

        @Override
        public void run() {
            for (long i = lowerBound; i <= upperBound; i++) {
                // Split the value of i into its hex values, by 4 parts:
                // Note, we use StringBuilder here for speed.
                StringBuilder hexValue = new StringBuilder(Long.toHexString(i));
                // Pad the value so it has the correct length.
                while (hexValue.length() % 4 != 0) {
                    hexValue.insert(0, '0');
                }

                // Split it in to Strings of 4 length.
                String[] parts = new String[hexValue.length() / 4];
                for (int j = 0; j < parts.length; j++) {
                    parts[j] = hexValue.substring(0, 4);
                    // subtract the assigned part from the existing string.
                    hexValue = new StringBuilder(hexValue.substring(4));
                }

                // Compose those parts into a unicode string.
                StringBuilder passwordToCheck = new StringBuilder();
                for (String part : parts) passwordToCheck.append((char) (Integer.parseInt(part, 16)));

                // Check if this is the solution, else just increment the number and move on.
                incrementNumChecks();
                if (passwordToCheck.toString().equals(password)) {
                    // Stop all other threads by interrupting them, throw a party or something.
                    for (Thread s : solvers) s.interrupt();
                    System.out.println("Password found after " + numChecks + " tries.");
                    complete = true;
                    break;
                }
            }
        }
    }
}
