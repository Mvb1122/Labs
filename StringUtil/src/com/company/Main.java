package com.company;

import javax.swing.*;
import java.awt.event.*;

public class Main {
    static JFrame frame;
    static JScrollPane scp;
    static JTextArea input;
    static JButton[] buttons = new JButton[3];
    static JLabel output;

    public static void main(String[] args) {
        final String[] tests = new String[]{"radar", "J", "Lewd did I live, & evil I did dwel.", "I like Java", "Straw? No, too stupid a fad, I put soot on warts."};
        try {
            //test reverse string function
            for (String test : tests) {
                StringUtil.reverse(test);
            }

            //test pig latin
            for (String test : tests){
                StringUtil.toPigLatin(test);
            }

            // Run palindrome test cases.
            for (String test : tests) {
                System.out.printf("%s : %s%n", test, StringUtil.isPalindrome(test));
            }

            // Test toShortHand()
            System.out.println(StringUtil.toShortHand("And then I said, balls."));
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Setup the window.
	    frame = new JFrame("StringUtil Lab");
        frame.setBounds(5, 5, 500, 500);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup the input.
        input = new JTextArea();
        scp = new JScrollPane(input);
        scp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scp);

        input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Do nothing.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Do nothing.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                fixUI();
            }
        });


        // Make the frame fix itself when it's resized.
        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                fixUI();
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

        // Set up the UI on initial boot.
        fixUI();
    }

    static int buttonHeight = 50;

    public static void fixUI() {
        scp.setBounds(0, buttonHeight, frame.getWidth(), frame.getHeight() - buttonHeight * 3);

        // 0 - Reverse 1 - To Pig Latin 2 - to Short Hand
        String[] labels = new String[]{"Reverse", "To Pig Latin", "To Short Hand"};
        int width = frame.getWidth() / buttons.length;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                buttons[i].setVisible(false);
            }

            buttons[i] = new JButton(labels[i]);
            buttons[i].setBounds(i * width, frame.getHeight() - buttonHeight * 2, width, buttonHeight);

            frame.add(buttons[i]);
        }

        // Set up the buttons.
        buttons[0].addActionListener(e -> input.setText(StringUtil.reverse(input.getText())));
        buttons[1].addActionListener(e -> input.setText(StringUtil.toPigLatin(input.getText())));
        buttons[2].addActionListener(e -> input.setText(StringUtil.toShortHand(input.getText())));

        if (output != null) output.setVisible(false);

        output = new JLabel("Is Palindrome: " + StringUtil.isPalindrome(input.getText()));
        output.setBounds(0, 0, frame.getWidth(), buttonHeight);
        output.setVerticalAlignment(SwingConstants.NORTH);
        frame.add(output);
    }
}
