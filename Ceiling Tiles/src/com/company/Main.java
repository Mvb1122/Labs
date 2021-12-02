package com.company;

import ceiling.*;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main {

    public static void main(String[] args) {
        // Create an array to represent the ceiling.
        Tile[][] ceiling = new Tile[15][20];

        // Fill it with blank tile objects.
        for (int i = 0; i < ceiling.length; i++) for (int j = 0; j < ceiling[i].length; j++) ceiling[i][j] = new Tile();

        // Setup the UI.
        TwoDimensionalArrayDisplay.display(ceiling);

        // Put the light in gradually, so I can see the updates happen.
        for (int x = 2; x < ceiling.length; x += 5) for (int y = 0; y < ceiling[x].length; y += 3) {
            try {
                Thread.sleep(16 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Insert the lights.
            try {
                Light light = new Light();
                ceiling[x][y] = light;
                ceiling[x][y + 1] = light;
            } catch (ArrayIndexOutOfBoundsException e) {;}
        }

        // Turn on the lights, to demonstrate that it works.
        for (Tile[] a: ceiling) for (Tile b: a) if (b instanceof Light) ((Light) b).setState(true);

        // Put the vents in, gradually.
        for (int y = 0; y < ceiling[0].length; y += 3) {
            ceiling[4][y] = new Vent();
            ceiling[ceiling.length - 5][y] = new Vent();

            try {
                Thread.sleep(16 * 2);
            } catch (InterruptedException ignored) {;}
        }

        // Create buttons to toggle the vents and fans.
        int buttonWidth = 120;
        int buttonHeight = 50;
        JFrame buttonDisplay = new JFrame("Vent and Light Control");
        buttonDisplay.setLayout(null);
        buttonDisplay.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonDisplay.setBounds(255, 0, buttonWidth, 3 * buttonHeight);

        // Create the buttons.
        JButton[] buttons = new JButton[2];
        buttons[0] = new JButton("Toggle Vents");
        buttons[1] = new JButton("Toggle Lights");
        for (int i = 0; i < buttons.length; i++) {
            buttonDisplay.add(buttons[i]);
            buttons[i].setBounds(0, i * buttonHeight, buttonWidth, buttonHeight);
        }

        // Toggle vents when the button's clicked.
        buttons[0].addActionListener(e -> Vent.setIsOn(!Vent.getIsOn()));

        // Toggle lights when the button's clicked.
        buttons[1].addActionListener(e -> {
            // Loop through all the lights and toggle their state.
            // Note that we have to skip every other light, since that "light" is actually just a reference to one that's already been ticked.
            for (Tile[] a: ceiling) for (int i = 0; i < a.length; i += 2) {
                Tile b = a[i];
                if (b instanceof Light) {
                    boolean lightState = ((Light) b).getState();
                    ((Light) b).setState(!lightState);
                }
            }
        });

        // Update the UI when the button display is resized
        buttonDisplay.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (int i = 0; i < buttons.length; i++) {
                    buttonDisplay.add(buttons[i]);
                    int height = buttonDisplay.getHeight() / (buttons.length + 1);
                    buttons[i].setBounds(0, i * height, buttonDisplay.getWidth(), height);
                }
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

        // Set the button window to be visible.
        buttonDisplay.setVisible(true);

        // Print out the array.
        print2DArray(ceiling);
    }

    /**
     * This simply method just outputs a representation of a 2D array.
     * @param arr A Tile[][] to be printed.
     */
    public static void print2DArray(Object[][] arr) {
        String output = "[";
        for (Object[] objects : arr) {
            output += "\n\t[";
            for (Object object : objects) output += String.format(" %5s ", object);
            output += "]";
        }

        System.out.println(output + "\n]");
    }
}
