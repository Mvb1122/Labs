package com.company;

import ceiling.*;

import javax.swing.*;
import java.awt.*;

public class TwoDimensionalArrayDisplay extends JPanel {
    private static JFrame f;

    // Note: This Object[][] only holds a **REFERENCE** to an array, so that when the original is mutated, this one reflects the change.
    private static Object[][] data;


    /**
     * Just pass this method a 2D array, and it'll display it. Updates are also shown-- it updates 60 times every second.
     * @param data Must effectively be a Tile array, where your Tile, Vent, and Light classes are in the ceiling package.
     */
    public static void display(Object[][] data) {
        TwoDimensionalArrayDisplay.data = data;
        createAndShowGUI();

        // Refresh the display 60 times a second.
        Thread renderer = new Thread(() -> {
            while (true) {
                try {
                    // 60 fps = ~16 millis per frame.
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {;}

                /*
                    This forces the window to be re-rendered once every 16ms-- to refresh the grid
                    so that it's up-to-date with the array.
                 */
                f.repaint();
            }
        });
        renderer.start();
    }


    /**
     * This private method handles starting the UI.
     */
    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        f = new JFrame("2DArray");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250,250);
        f.add(new UI(data));
        f.pack();
        f.setVisible(true);
    }
}

/**
 * This private class is the part that actually renders the grid.
 * You do not have to instantiate it or anything.
 */
class UI extends JPanel {
    // Once again, this only holds a reference to the actual program's Tile[][], so it reflects the changes of it.
    Object[][] data;


    /**
     * This Method just sets the Tile[][] reference, then sets the window's border to be a black line.
     * @param data A Tile[][] reference.
     */
    public UI(Object[][] data) {
        this.data = data;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * This method says how large the window should be, on initial startup.
     * @return A dimension containing space for the array, where each index is given 5 square pixels.
     */
    public Dimension getPreferredSize() {
        return new Dimension(data.length * 5, data[0].length * 5);
    }

    /**
     * This is the method which actually draws the squares onto the screen.
     * @param g This method is called internally, so this just is the window's handling Graphics object.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Squares by looping on both dimensions, finding the appropriate color, and then putting it in the appropriate space.
        for (int i = 0; i < data.length; i++) for (int j = 0; j < data[i].length; j++) {
            g.setColor(getColor(data[i][j]));
            g.fillRect(5 * i, 5 * j, 5, 5);
        }
    }

    /**
     * This is where things get a little tricky;-- I identify the ACTUAL class that the tile is,
     * and since we can guarantee that it is a subclass of Tile due to the circumstances, I only check for those.
     * @param o The tile to check.
     * @return A color, corresponding to that tile.
     */
    private Color getColor(Object o) {
        if (o.getClass() == Tile.class)
            return Color.GRAY;


        if (o.getClass() == Vent.class) if (Vent.getIsOn()) return Color.green;
            else return Color.black;


        if (o.getClass() == Light.class)
            if (((Light) o).getState()) return Color.yellow;
            else return Color.lightGray;

        return Color.GRAY;
    }
}
