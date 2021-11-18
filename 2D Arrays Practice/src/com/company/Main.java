package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static JFrame frame, statsPanel;
    static ArrayList<JTextArea> inputs;

    /**
     * Index 0: Refresh Button, <br>
     * Index 1: Add Button, <br>
     * Index 2: Remove Button. <br>
     */
    static JButton[] buttons;

    public static void main(String[] args) {
        // Create the window where the program runs.
        frame = new JFrame("2D Array Exercises");
        frame.setBounds(5,5,500, 500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the arrayList of inputs-- These text boxes will be how the user inputs text.
        inputs = new ArrayList<>(3);
            // Assign the text areas.
        for (int i = 0; i < 3; i++) inputs.add(i, new JTextArea());

        // Create the array of buttons-- One to manually update the stats, one to add a column, and one to remove a column.
        buttons = new JButton[3];

        // Create the buttons.
            // Refresh button
        buttons[0] = new JButton("Refresh");
        buttons[0].addActionListener(e -> {
            // Refresh the UI.
            refreshUI();
        });

            // Add Column button
        buttons[1] = new JButton("Add Column");
        buttons[1].addActionListener(e -> {
            // Add a new input to the inputs[].
            inputs.add(new JTextArea());

            // Refresh the UI.
            refreshUI();
        });

            // Remove Column button
        buttons[2] = new JButton("Remove Column");
        buttons[2].addActionListener(e -> {
            // Remove the last column
            inputs.remove(inputs.size() - 1);

            // Refresh the UI.
            refreshUI();
        });


        frame.setVisible(true);

        // Create a frame to show the stats of the array.
        statsPanel = new JFrame("Statistics of your array.");
        statsPanel.setBounds(510, 5, 500, 500);
        statsPanel.setLayout(null);
        statsPanel.setVisible(true);
        statsPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build the UI.
        initUI();

        // Refresh UI on window resize
        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshUI();
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

        statsPanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateStats();
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
    }



    private static final int buttonHeight, columnSpacing, topOffset;

    static {
        buttonHeight = 50;
        columnSpacing = 5;
        topOffset = 10;
    }

    private static void initUI() {
        int width = frame.getWidth();
        int height = frame.getHeight();

        int columnWidth = (width - (2 * columnSpacing)) / inputs.size();
        int buttonWidth = (width - (2 * columnSpacing)) / buttons.length;

        // Add all views to the frame.
            // Add all the arrays to the input.
        for (int i = 0; i < inputs.size(); i++) {
            JTextArea ta = inputs.get(i);

            // Create a JScrollPane for the textarea to be placed in (required to set its width/height.)
            JScrollPane jsc = new JScrollPane(ta);
            jsc.setBounds(columnSpacing + (i * columnWidth), topOffset, columnWidth, height - (2 * buttonHeight) - topOffset);

            frame.add(jsc);
            jsc.repaint();
        }

            // Add buttons.
        for (int i = 0; i < buttons.length; i++) {
            JButton b = buttons[i];
            b.setBounds(buttonWidth * i, height - (2 * buttonHeight), buttonWidth, buttonHeight);
            frame.add(b);
        }

        // Patch flickering text boxes by forcing them to be re-rendered by this call.
        frame.repaint();

        // Update the stats.
        updateStats();
    }

    private static void updateStats() {
        // Clear statsPanel.
        statsPanel.getContentPane().removeAll();
        statsPanel.repaint();

        // Run all one million tests.
        int[][] data = getData();

        if (data != null) {
            int max = Array2DExercises.max(data);
            String rowSums = "Row Sums: \n";
            {
                for (int i = 0; i < data.length; i++) {
                    rowSums += "      Row #" + i + " Sum: " + Array2DExercises.rowSum(data, i) + "\n";
                }
            }

            String columnSums = "Column Sums: \n";
            {
                for (int i = 0; i < data[0].length; i++) {
                    columnSums += "      Column #" + i + " Sum: " + Array2DExercises.columnSum(data, i) + "\n";
                }
            }

            boolean isRowMagic = Array2DExercises.isRowMagic(data);

            boolean isColumnMagic = Array2DExercises.isColumnMagic(data);

            boolean isSquare = Array2DExercises.isSquare(data);

            boolean isMagic = Array2DExercises.isMagic(data);

            boolean isLatin = Array2DExercises.isLatin(data);

            boolean isSequence = Array2DExercises.isSequence(data);

            // Compact those results into a string.
            String dataString = Arrays.deepToString(data);
            String statsString = "Results: \n Data: " + dataString + "\nMax: " + max + "\n" + rowSums + "\n" + columnSums
                    + "\n Is Row Magic: " + booleanToString(isRowMagic) + "\n Is Column Magic: " + booleanToString(isColumnMagic)
                    + "\n Is Square: " + booleanToString(isSquare) + "\n Is Magic: " + booleanToString(isMagic)
                    + "\n Is Latin: " + booleanToString(isLatin) + "\n Is Sequence: " + isSequence;

            JLabel l = new JLabel("<html>" + statsString.replaceAll("\n", "<br>") + "</html>");
            l.setVerticalAlignment(JLabel.NORTH);
            l.setBounds(0, 0, statsPanel.getWidth(), statsPanel.getHeight());
            statsPanel.add(l);
            statsPanel.repaint();
        } else {
            JLabel l = new JLabel("No data entered.");
            l.setVerticalAlignment(JLabel.NORTH);
            l.setBounds(0, 0, statsPanel.getWidth(), statsPanel.getHeight());
            statsPanel.add(l);
            statsPanel.repaint();
        }
        frame.repaint();
    }

    private static String booleanToString(boolean i) { if (i) return "Yes"; else return "No"; }

    private static int[][] getData() {
        // Load data into an array.
        int[][] o = new int[inputs.size()][];
        for (int i = 0; i < inputs.size(); i++) o[i] = Helpers.parseToIntArray(inputs.get(i).getText().trim());


            // TODO: Remove the below line.
        // for (int i = 0; i < inputs.size(); i++) System.out.println("Text: " + inputs.get(i).getText());

        // Rotate data by 90° clockwise, since we currently have the columns where we want the rows.
            // Find the maximum length of the rows.
        int maxLength = -1;
        {
            for (int[] ints : o) if (ints == null) return null; else if (maxLength < ints.length) maxLength = ints.length;
        }

        int[][] output = new int[maxLength][o.length];
        for (int i = 0; i < o.length; i++) {
            for (int j = 0; j < o[i].length; j++) {
                output[j][i] = o[i][j];
            }
        }
        if (maxLength == 0) return null;
        else {
            return output;
        }
    }

    private static void refreshUI() {
        // Remove all the existing views on the frame, just so it appears clean.
        frame.getContentPane().removeAll();
        frame.repaint();

        // Call initUI.
        initUI();
    }
}
