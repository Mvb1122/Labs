package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Make a window for it, configure it as I like it.
        JFrame window = new JFrame("XOR Encryption");
        window.setBounds(5, 5, 500,500);
        window.setLayout(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup inputs.
        JTextField[] inputs = new JTextField[2];
            // Index 0 - String to be encrypted.
            // Index 1 - Key to encrypt by.
    }
}
