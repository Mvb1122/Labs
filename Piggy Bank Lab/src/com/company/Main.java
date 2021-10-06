package com.company;

import banks.PiggyBank;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {
        // Create a bank for the program to use.
            // Note, I had to make the piggy-bank atomic, since IntelliJ was complaining about access from other threads. (on a button's click.)
        AtomicReference<PiggyBank> p = new AtomicReference<>(new PiggyBank("26 Quarters, 35 Pennies, 29 Dimes"));
        JFrame frame = new JFrame("Piggy Bank Lab");
        // Set the window's layout to `null` so I can use manual positioning.
        frame.setLayout(null);

        // Make the program close when the X button on the window is hit.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Declare layout configuration variables.
        int maxX, maxY, buttonHeight, buttonWidth;

        // maxX is how wide the window is.
        maxX = 600;

        // maxY is how tall the window is.
        maxY = 300;

        // buttonHeight is how tall the buttons at the bottom of the screen are. I chose 50px, since that's what I thought looked the best.
        buttonHeight = 50;

        // buttonWidth is how wide each button is, and since I only have 3 buttons, I can just divide the width of the window by 3 in order to make 3 equal sections which fit on the window perfectly.
        buttonWidth = maxX / 3;

        // This sets the window's start location, which is 5 pixels from the top right corner of your monitor, both sideways and downwards.
        frame.setLocation(5, 5);

        // This creates the label which displays the bank's value at the top of the screen.
        JLabel label = new JLabel(p.toString(), SwingConstants.CENTER);
        // Since I want the label to fit to the width of the window, I tell it to be as wide as the window itself.
        // Then, since I want it to match the height of the buttons, for horizontal symmetry, I tell it to be as tall as the buttons themselves.
        label.setBounds(0, 0, maxX, buttonHeight);

        // Create a text field where the user can enter the contents of their piggy bank.
        JTextField input = new JTextField("26 Quarters, 35 Pennies, 29 Dimes", 1);

        /*
         I want the text field to run down from the bottom of the label as far as it can, while leaving space for the buttons, as well as being as wide as the window itself,
         so I tell it to be as wide as the screen, and I set the y-start to the button's height, since that's what the label is using as its height.
         Then, since I want it to run as far as possible, I tell it to be as tall as the max height, minus twice the button's height.
         The reason I used **twice** and not only once here is because we already have one button-sized object, the label, at the top of the screen,
         and since I don't want the text area to run off the screen, I had to make enough space for the buttons at the bottom, which are also button sized, thereby requiring **twice** the space of one button.
        */
        input.setBounds(0, buttonHeight, maxX, maxY-(2 * buttonHeight));

        // Create a button which submits the input text to the Piggy-bank's constructor.
        JButton setButton = new JButton("Set");

        /*
         Since I want the button to start at the edge of the screen, without going off, I tell it to start at the maxX, minus the width of one button,
         which places it right up against the edge of the window. Next, I place it at the bottom of the window, placed up a height of a button, which
         the button fills in, thereby making it placed perfectly in the corner of the window.
        */
        setButton.setBounds(maxX-buttonWidth, maxY-buttonHeight, buttonWidth, buttonHeight);

        /*
         Now, I create something called an `Action Listener`, which I apply to that button. It basically allows code to be run whenever something happens,
         which in this case, is a click on the button.
        */
        setButton.addActionListener(e -> {
            // Here, I'm using try-catch statements, which basically keep the program from crashing due to a certain error, which in this case, is user-based, so we account for
            // fools using this program by preventing any possible errors from crashing the program.
            try {
                try {
                    // First, I got the input from the text box and then used the parse() method on the PiggyBank class to turn it into the proper array of values that the normal constructor uses.
                    int[] displayedCoins = PiggyBank.parse(input.getText());
                    // Then, I get the PiggyBank and set the value of it to the user's input.
                    p.get().set(displayedCoins);
                    // Next and finally, I update the display to hold the latest information on the PiggyBank.
                    label.setText(p.get().toString());
                } catch (NumberFormatException N) {
                    // Do nothing, since this means that the user's input is malformed, and they probably accidentally clicked the button.
                }
            } catch (StringIndexOutOfBoundsException S) {
                // See above comment.
            }
        });

        // ~~~~ The code below here is basically copy and paste, so skip to line 139 ~~~~

        // Create a button which adds the specified coins.
        JButton addButton = new JButton("Add");

        // I'll save you the reading, but this basically places the button in the bottom-center of the screen.
        addButton.setBounds(maxX-(2 * buttonWidth), maxY-buttonHeight, buttonWidth, buttonHeight);

        addButton.addActionListener(e -> {
            try {
                try {
                    // Gets the user's input in the proper form.
                    int[] displayedCoins = PiggyBank.parse(input.getText());
                    // Adds the user's input to the bank.
                    p.get().add(displayedCoins);
                    // Updates the label.
                    label.setText(p.get().toString());
                } catch (NumberFormatException N) {
                    // Do nothing, since this means that the user's input is malformed, and they probably accidentally clicked the button.
                }
            } catch (StringIndexOutOfBoundsException S) {
                // See above comment.
            }
        });

        // Create a button which subtracts the specified coins.
        JButton subtractButton = new JButton("Subtract");

        // This is the third time, so this code places the button in the bottom left of the screen.
        subtractButton.setBounds(maxX-(3 * buttonWidth), maxY-buttonHeight, buttonWidth, buttonHeight);
        // Make the button take the appropriate actions when it's clicked.
        subtractButton.addActionListener(e -> {
            try {
                try {
                    // Get
                    int[] displayedCoins = PiggyBank.parse(input.getText());
                    // Set
                    p.get().subtract(displayedCoins);
                    // Display
                    label.setText(p.get().toString());
                } catch (NumberFormatException N) {
                    // Do nothing, since this means that the user's input is malformed, and they probably accidentally clicked the button.
                }
            } catch (StringIndexOutOfBoundsException S) {
                // See above comment.
            }
        });

        // Now, to wrap things up, we just add all the buttons, the label, and the display to the window,
        frame.add(subtractButton);
        frame.add(addButton);
        frame.add(setButton);
        frame.add(label);
        frame.add(input);

        /*
         Set the window's size to match the variables I've been referencing so far.
         Additionally, I set it just a little taller than the values I've used, just to add some white-space at the bottom
         of the window, because I thought it looked better than having it perfectly match up.
        */
        frame.setSize(maxX, maxY + 50);

        // And finally, I set the window to display on the screen.
        frame.setVisible(true);
    }
}