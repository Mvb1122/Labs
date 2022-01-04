package Renderer;
import Objects.RenderObject;

import javax.swing.*;
import java.awt.*;

public class Display {
    private static JFrame f;

    // Note: This RenderObject[][] only holds a **REFERENCE** to an array, so that when the original is mutated, this one reflects the change.
    private static RenderObject[][] data;
    private static LevelData level;

    /**
     * The time in miliseconds per frame.
     */
    private static long frameTime;
    public static Thread renderer;
    static {
        setFPS(60);
    }


    /**
     * Just pass this method a 2D array, and it'll display it. Updates are also shown-- it updates 60 times every second.
     * @param data Must effectively be a RenderObject array, where your RenderObjects are put in the Objects package.
     */
    public static void display(RenderObject[][] data) {
        Display.data = data;
        createAndShowGUI();

        // Refresh the display 60 times a second.
        renderer = new Thread(() -> {
            renderLoop:
            while (true) {
                try {
                    // 60 fps = ~16 millis per frame.
                    // 1 ms = 1/1000 per frame
                    // 60f/1s -> 60f / 1ms = 60f * 0.001
                    Thread.sleep(frameTime);
                    if (data != null) repaint();
                } catch (InterruptedException e) {
                    // Sleep until another InterruptedException is thrown.
                    while (true) {
                        try {
                            while (true) Thread.sleep(1);
                        } catch (InterruptedException eg) {
                            repaint();
                            continue renderLoop;
                        }
                    }
                }

            /*
                This forces the window to be re-rendered once every 16ms-- to refresh the grid
                so that it's up-to-date with the array.
             */

            }
        });
        renderer.start();
    }

    /**
     * Repaints the display.
     */
    private static void repaint() {
        /*
        try {
            Thread.sleep(frameTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        f.repaint();
    }

    /**
     * Sets the maximum rendering Frames-per-second
     * @param FPS The Frames-Per-Second to render at.
     */
    public static void setFPS(double FPS) {
        frameTime = (long) (1000 / FPS);
    }


    /**
     * This private method handles starting the UI.
     */
    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        f = new JFrame(LevelData.name);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250,250);
        f.add(new UI(data));
        f.pack();
        f.setVisible(true);
    }

    /**
     * Returns the amount of time between frames, in milliseconds.
     * @return By Default: 6ms
     * @implNote You can change the FPS with setFPS(), but this method returns the frame time, not the frames per second.
     */
    public static long getFPS() {
        return frameTime;
    }
}

/**
 * This private class is the part that actually renders the grid.
 * You do not have to instantiate it or anything.
 */
class UI extends JPanel {
    // Once again, this only holds a reference to the actual program's Tile[][], so it reflects the changes of it.
    RenderObject[][] data;
    static int scale = 10;


    /**
     * This Method just sets the Tile[][] reference, then sets the window's border to be a black line.
     * @param data A Tile[][] reference.
     */
    public UI(RenderObject[][] data) {
        this.data = data;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * This method says how large the window should be, on initial startup.
     * @return A dimension containing space for the array, where each index is given ${scale} square pixels.
     */
    public Dimension getPreferredSize() {
        return new Dimension(data.length * scale, data[0].length * scale);
    }

    /**
     * This is the method which actually draws the squares onto the screen.
     * @param g This method is called internally, so this just is the window's handling Graphics object.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Squares by looping on both dimensions, finding the appropriate color, and then putting it in the appropriate space.
        for (int i = 0; i < data.length; i++) for (int j = 0; j < data[i].length; j++) {
            g.setColor(getColor(data[i][j], i, j));
            g.fillRect(scale * i, scale * j, scale, scale);
        }
    }

    // TODO: Remake this comment.
    /**
     * This is where things get a little tricky;-- I identify the ACTUAL class that the tile is,
     * and since we can guarantee that it is a subclass of Tile due to the circumstances, I only check for those.
     * @param o The tile to check.
     * @return A color, corresponding to that tile.
     */
    private Color getColor(RenderObject o, int x, int y) {
        if (o != null)
        return o.getColorAt(new Coordinate(x, y));
        else {
            // Find first filled slot, return that.
            for (int i = 0; i < LevelData.background.length; i++) for (int j = 0; j < LevelData.background[i].length; j++)
                if (LevelData.background[i][j] != null) return LevelData.background[i][j];
        }
        return LevelData.defaultBackGroundColor;
    }
}
