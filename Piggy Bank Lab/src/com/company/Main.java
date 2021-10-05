package com.company;

import banks.PiggyBank;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Main {

    public static void main(String[] args) {
        int[] coins = PiggyBank.parse("26 Quarters, 35 Pennies, 29 Dimes");

        JFrame frame = new JFrame("Piggy Bank Lab");
        frame.setSize(300, 300);
        frame.setLocation(5, 5);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel(intArrayToString(coins), SwingConstants.CENTER);
        frame.add(label);

        frame.show();
    }

    private static String intArrayToString(int[] v) {
        String s = "[";
        for (int i = 0; i < v.length; i++) {
            if (i == 0) s += v[i]; else s += ", " + v[i];
        }
        s += "]";
        return s;
    }
}