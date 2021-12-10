package Objects;

import Renderer.Coordinate;

import java.awt.*;
import java.util.Arrays;

public class RenderObject {
    public Color[][] texture;
    public boolean[][] collision;
    public int x, y;
    public int width, height;

    /**
     * This version automatically generates collision data from the texture.
     * @param x The x-location in world space (Top right corner)
     * @param y The y-location in world space (Top right corner)
     * @param texture A Color[][] which specifes the pixels to be rendered.
     */
    public RenderObject(int x, int y, Color[][] texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        collision = new boolean[texture.length][];
        for (int i = 0; i < collision.length; i++) {
            collision[i] = new boolean[texture[i].length];
            Arrays.fill(collision[i], true);
        }

        width = texture.length;

        // Calculate maximum height, apply it.
        height = 0;
        for (Color[] xt : texture) if (height < xt.length) height = xt.length;
    }

    public RenderObject(int x, int y, Color[][] texture, boolean[][] collision) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    /**
     * Gets a color from the Object's texture.
     * @param locale The world-space coordinates where the texture is located at.
     * @return The Color located at that point on the texture.
     * @implNote May return null.
     */
    public Color getColorAt(Coordinate locale) {
        Coordinate c2 = convertToLocalSpace(locale);

        return texture[c2.x][c2.y];
    }

    private Coordinate convertToLocalSpace(Coordinate worldSpace) {
        return new Coordinate(worldSpace.x - x, worldSpace.y - y);
    }

    /**
     * @return The top-right corner of the RenderObject.
     */
    public Coordinate getWorldLocation() {
        return new Coordinate(x, y);
    }
}
