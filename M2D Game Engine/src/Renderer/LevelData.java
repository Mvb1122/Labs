package Renderer;

import Objects.RenderObject;

import java.awt.*;

public class LevelData {
    public static RenderObject[][] foreground;
    public static boolean[][] collision;
    public static Color[][] background;
    public static int levelWidth, levelHeight;
    public static String name;

    static {
        name = "I'm totally not a virus lmao xd lol :3.exe";
    }

    public static void init(RenderObject[] foreground, boolean[][] collision, Color backgroundColor, int width, int height) {
        // Create an array to hold the level's foreground information.
        LevelData.foreground = new RenderObject[width][height];
        apply(foreground);
        LevelData.collision = collision;
        background = new Color[collision.length][];

        // Set Default color
        setDefaultBackgroundColor(backgroundColor);

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
    }

    private static void apply(RenderObject[] foreground) {
        for (RenderObject ro : foreground) {
            apply(ro);
        }
    }

    public static void apply(RenderObject objectToBeApplied) {
        // Convert local coordinates to world space on the object.
        Coordinate worldSpaceLocale = objectToBeApplied.getWorldLocation();

        for (int x = 0; x < objectToBeApplied.width; x++) for (int y = 0; y < objectToBeApplied.height; y++) {
            foreground[x + worldSpaceLocale.x][y + worldSpaceLocale.y] = objectToBeApplied; // .getColorAt(new Coordinate(x, y))
        }
    }

    public static void setDefaultBackgroundColor(Color c) {
        // Set Default color
        for (int i = 0; i < collision.length; i++) {
            background[i] = new Color[collision[i].length];
            for (int j = 0; j < collision[i].length; j++)
                background[i][j] = c;

        }
    }
}
