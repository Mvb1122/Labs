package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Ask the user to choose a number of iterations.
        System.out.println("Please enter the number of iterations to run:\n(Note: An iteration is one prime number found.)");
        Scanner s = new Scanner(System.in);
        int numIterations = 0;
        while (numIterations == 0) {
            try {
                numIterations = s.nextInt();

                if (numIterations <= 0) {
                    throw new Exception("The user must enter a number that's greater than zero.");
                }

            } catch (Exception e) {
                System.out.println("You must enter a number that's greater than 0, try again:");
                s.next();
            }
        }
        s.close();
        final int finalNumIterations = numIterations;

        // Solve for said prime numbers.
        if (numIterations > 10) System.out.println("This may take some time.");

        ArrayList<Integer> numbers = new ArrayList<Integer>(numIterations + 1);
        int var1 = (int)Math.ceil(numIterations / 10d);
        String res = String.valueOf(var1 == 0 ? 1 : var1);
        String formatString = "Prime #%" + res + "d:  %-5d%n";

        var ref = new Object() {
            int i = 1;
        };

        // Solve on one thread, log on the main thread.
        Thread solver = new Thread(() -> {
            while (numbers.size() != finalNumIterations) {
                int prime = prime(ref.i);
                if (ref.i == 1 || (prime != 0 && !numbers.contains(prime))) {
                    numbers.add(prime);
                    System.out.printf(formatString, numbers.size(), prime);
                };

                if (numbers.size() == finalNumIterations) break;
                else ref.i++;
            }
        }); solver.start();


        // Sleep the main thread for a second so that the first few primes can be calculated.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (numbers.size() != numIterations) {
            System.out.printf("Tries: %s%n", ref.i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Assemble the equation.
        StringBuilder equation = new StringBuilder("f(x)=");
        for (int i : numbers) {
            equation.append("(x-").append(i).append(")");
        }
        System.out.println("Your equation is: " + equation);
    }

    // Solves for the nth prime.
    private static int prime(double n) {
        return (int) (Math.floor(
                (factorial(n) % (n + 1)) / n
        ) * (n - 1) + 2);
    }

    // Computes the factorial of a number.
    public static double factorial(double d) {
        double fact = 1;
        for (int i = 2; i <= d; i++) {
            fact = fact * i;
        }
        return fact;
    }
}
