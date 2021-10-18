// Yes, I did just recycle a lot of my code from the other lab. It's called efficiency, okay?

import Math.Statistics;
import javax.swing.*;

public class Main {
    static JFrame window;
    static JTextArea numberInput;
    static JScrollPane scrollPane;
    static JLabel label;

    public static void main(String[] args) {
        // Create variables to hold layout information.
        int maxX = 500;
        int maxY = 500;
        int buttonHeight = 75;
        int labelHeight = 25;

        // Create a window for the program to run in.
        window = new JFrame("General Array Lab");
        // Make the program close when the X button is hit.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a text input for the numbers.
        numberInput = new JTextArea("Enter your numbers here.");
        // Make it editable.
        numberInput.setEditable(true);
        // Make it as wide as it can be and as tall as it can be, while leaving 75 px of space for the text and button below/above it.
        numberInput.setBounds(0, 0, maxX, maxY - labelHeight - buttonHeight);

        // Create a scrollPane to hold the number input.
        scrollPane = new JScrollPane(numberInput);
        // Make the vertical scroll bar always visible.
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // Place it 50 px down and make it just wide enough to fill the window while still leaving space for the scroll bar itself, and make it the maximum minus the label height and button height tall, as to allow the button to fit.
        scrollPane.setBounds(0, labelHeight, maxX, maxY - labelHeight - buttonHeight - 45);

        // Create a label to show the data's information at the top of the screen.
        label = new JLabel("Enter your numbers below and then hit the \"Go\" button.", JLabel.CENTER);
        label.setBounds(0, 0, maxX, buttonHeight);
        label.setVerticalAlignment(JLabel.NORTH);

        // Create button to process the data.
        JButton goButton = new JButton("Go!");
        goButton.setBounds(0, maxY - buttonHeight - 45, maxX, buttonHeight);
        // Have the button call the process method when it's clicked.
        goButton.addActionListener(e -> {
            process();
        });

        // Add the elements to the window.
        window.add(goButton);
        window.add(scrollPane);
        window.add(label);

        // Set the window properties.
        window.setLayout(null);
        window.setSize(maxX, maxY);
        window.setVisible(true);
    }

    public static void process() {
        try {
            // Split the input by the newLine char, then put it into an int[].
            int[] values = Helpers.parseToIntArray(numberInput.getText().trim() + '\n');

            // Find the minimum:
            int minimum = Statistics.min(values);

            // Find the maximum:
            int maximum = Statistics.max(values);

            // Flip the array and output it.
            {
                String text = "";
                for (int i = 0; i < values.length; i++) {
                    text = values[i] + "\n" + text;
                }
                numberInput.setText(text);
            }

            // Create and display output.
            String text = "The minimum is " + minimum + ", the maximum is " + maximum + ".";
            label.setText(text);
        } catch (NumberFormatException e) {
            // Do nothing, since this error means that the user has malformed the input.
        }
    }
}
