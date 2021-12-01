package com.company;

import ceiling.*;

import javax.swing.*;
import java.awt.*;

public class TwoDimensionalArrayDisplay extends JPanel {
    private static JFrame f;
    private static Object[][] data;


    /**
     * Just pass this method a 2D array, and it'll display it. Updates are also shown-- it updates 60 times every second.
     * @param data Must effectively be a Tile array, where your Tile, Vent, and Light classes are in the ceiling package.
     */
    public static void display(Object[][] data) {
        TwoDimensionalArrayDisplay.data = data;
        createAndShowGUI();

        // Refresh the display once every tenth of a second.
        Thread renderer = new Thread(() -> {
            while (true) {
                try {
                    // 60 fps = ~16 millis per frame.
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {;}

                update();
            }
        });
        renderer.start();
    }


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

    private static void update() {
        f.repaint();
    }
}

class UI extends JPanel {
    Object[][] data;

    public UI(Object[][] data) {
        this.data = data;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(data.length * 5, data[0].length * 5);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Squares
        for (int i = 0; i < data.length; i++) for (int j = 0; j < data[i].length; j++) {
            g.setColor(getColor(data[i][j]));
            g.fillRect(5 * i, 5 * j, 5, 5);
        }
    }

    private Color getColor(Object o) {
        if (o.getClass() == Tile.class)
            return Color.GRAY;


        if (o.getClass() == Vent.class) if (Vent.getIsOn()) return Color.green;
            else return Color.black;


        if (o.getClass() == Light.class)
            if (((Light) o).getState()) return Color.yellow;
            else return Color.lightGray;

        return Color.white;
    }
}
