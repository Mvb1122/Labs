package com.company;

import VinegereEncryption.EncryptionEngine;
import VinegereEncryption.VigenereException;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main {
    static JTextArea[] inputs;
    static JScrollPane[] scrollPanes;
    static JButton[] buttons;
    static JFrame window;
    static JLabel label;

    public static void main(String[] args) {
        // Make a window for it, configure it as I like it.
        window = new JFrame("XOR Encryption");
        window.setBounds(5, 5, 800,500);
        window.setLayout(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup inputs.
        inputs = new JTextArea[2];
            // Index 0 - String to be encrypted.
            // Index 1 - Key to encrypt by.
        // Create the scrollPanes to hold those fields.
        scrollPanes = new JScrollPane[2];

        // Add those into the array.
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = new JTextArea();
            scrollPanes[i] = new JScrollPane(inputs[i]);
            scrollPanes[i].setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        }

        // Set up the label.
        label = new JLabel("<html>Enter either your encrypted text or your text to be encrypted below on the left, and then the key on the right.</html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);



        // Set up the buttons.
        /*
         We don't actually need two buttons to encrypt and decrypt because it's exactly the same as encrypting.
         I've only added the second button because it looks nice.
         */
        buttons = new JButton[2];
            // Index 0 - Encrypt
            // Index 1 - Decrypt

        buttons[0] = new JButton("Encrypt");
        buttons[1] = new JButton("Decrypt");

            // Add actions
        buttons[0].addActionListener(e -> runEncryption());

        buttons[1].addActionListener(e -> runEncryption());

        // Make the UI change scale when the window has been resized
        window.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                setUI();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // Do nothing.
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // Do nothing.
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // Do nothing.
            }
        });

        // Set up the UI.
            // NOTE: I have no idea why, but Swing seems to hate it when I only set up the UI once on startup, so I have to do it twice.
        setUI(); setUI();
    }

    private static void setUI() {
        // Clear the window.
        window.repaint();

        // Run some numbers so that it scales in relation to the window's scale.
        int inputHeight = (int) (window.getHeight() * 0.70);
        int inputWidth = window.getWidth() / 2;
        int labelHeight = (int) (window.getHeight() * 0.1);

        // Do the actual UI Scaling stuff.
            // Set the label.
        label.setBounds(0, 0, window.getWidth(), labelHeight);
        window.add(label);
            // Set the inputs.
        for (int i = 0; i < inputs.length; i++) {
            scrollPanes[i].setBounds((i * inputWidth), labelHeight + 1, inputWidth - 15, inputHeight);
            window.add(scrollPanes[i]);
        }
            // Set the buttons
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds((i * inputWidth), window.getHeight() - (2 * labelHeight), inputWidth, labelHeight);
            window.add(buttons[i]);
        }
    }

    private static void runEncryption() {
        if (!inputs[0].getText().equals("") && !inputs[1].getText().equals("")) {
            EncryptionEngine e = new EncryptionEngine(inputs[0].getText(), inputs[1].getText());
            try {
                createNewTextWindow(e.encrypt());
            } catch (VigenereException er) {
                label.setText(er.error);
            }
        }
    }

    /**
     * Creates a window displaying the encrypted text.
     * @param encrypt The text to be displayed.
     */
    private static void createNewTextWindow(String encrypt) {
        JFrame textWindow = new JFrame("Your converted text");
        textWindow.setBounds(550, 5, 850, 500);
        textWindow.add(new JScrollPane(new JTextArea("Encrypted:\n" + encrypt + "\n\n\nIn ASCII:\n" + EncryptionEngine.toASCII(encrypt))));
        textWindow.setVisible(true);
    }
}
