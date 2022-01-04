package Renderer;

import Objects.RenderObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LevelData {
    public static RenderObject[][] foreground;
    public static boolean[][] collision;
    public static Color[][] background;
    public static int levelWidth, levelHeight;
    public static String name;
    public static Color defaultBackGroundColor;

    static {
        name = "I'm totally not a virus lmao xd lol :3 owo.exe";
    }

    public static void init(RenderObject[] foreground, boolean[][] collision, Color backgroundColor, int width, int height) {
        // Create an array to hold the level's foreground information.
        LevelData.foreground = new RenderObject[width][height];
        apply(foreground);
        LevelData.collision = collision;
        background = new Color[width][height];

        // Set Default color
        setDefaultBackgroundColor(backgroundColor);
        defaultBackGroundColor = backgroundColor;

        // Start up the actual display.
        Display.display(LevelData.foreground);

        // TODO: Refactor collision data storage.

        // TODO: Refactor the window scaling to use these variables in figuring out how to scale the window.
            // EG; on window resize, stretch the canvas so that it fits to the edges.
            // or have it scale so that it's as large as possible, without warping pixels.
            // Find closest-to-real-scale edge -> Scale so that's 100% matching or if there's one that's already past 100
            // scale it so that *that* dimension is 100%.
        levelHeight = height;
        levelWidth = width;

        // Create a thread to update the level data once per frame.
        Thread levelUpdater = new Thread(() -> { while (true) {
            try {
                Thread.sleep(Display.getFPS());
            } catch (InterruptedException ignored) {;}

            // Scrap the extant foreground for its RenderObjects, then apply the list back onto it.
            ArrayList<RenderObject> scrappedData = new ArrayList<>(0);
            for (RenderObject[] ra : LevelData.foreground) for (RenderObject r : ra) {
                if (r != null & !scrappedData.contains(r))
                    scrappedData.add(r);
            }

            // Wipe the foreground, without creating a new object and destroying the Display's reference.
            for (int i = 0; i < LevelData.foreground.length; i++) Arrays.fill(LevelData.foreground[i], null);

            apply(scrappedData.toArray(scrappedData.toArray(new RenderObject[0])));
        }}, "LevelData Scrapping Thread");
        levelUpdater.start();
    }
    public static void init(ArrayList<RenderObject> foreground, boolean[][] collision, Color backgroundColor, int width, int height) {
        // Create a thread to update the array from the arraylist as it happens.
            // Note, this has to be a final one-element array since we need to acess it from the other thread.
        final RenderObject[][] data = {foreground.toArray(new RenderObject[0])};
        Thread updater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10 * Display.getFPS());
                    } catch (InterruptedException ignored) {;}

                    data[0] = foreground.toArray(new RenderObject[0]);
                }
            }
        }, "LevelData::init ArrayList Overload Update Thread");
        updater.start();
        init(data[0], collision, backgroundColor, width, height);
    }

    private static void apply(RenderObject[] foreground) {
        for (RenderObject ro : foreground) {
            apply(ro);
        }
    }

    public static void apply(RenderObject objectToBeApplied) {
        if (Display.renderer != null) Display.renderer.interrupt();
        // Convert local coordinates to world space on the object.
        Coordinate worldSpaceLocale = objectToBeApplied.getWorldLocation();

        for (int x = 0; x < objectToBeApplied.width; x++) for (int y = 0; y < objectToBeApplied.height; y++) {
            try {
                foreground[x + worldSpaceLocale.x][y + worldSpaceLocale.y] = objectToBeApplied; // .getColorAt(new Coordinate(x, y))
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("A RenderObject just tried to be placed outside the screen!");
            }
        }
        if (Display.renderer != null) Display.renderer.interrupt();
    }

    public static void setDefaultBackgroundColor(Color c) {
        // Set Default color
        for (int i = 0; i < levelHeight; i++) {
            background[i] = new Color[levelWidth];
            for (int j = 0; j < levelWidth; j++)
                background[i][j] = c;
        }
        defaultBackGroundColor = c;
    }
}
