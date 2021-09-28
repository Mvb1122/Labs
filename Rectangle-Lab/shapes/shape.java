package shapes;
import gpdraw.*;

abstract public class shape {
    protected double x, y, width, height;
    static protected DrawingTool pen;
    // static private SketchPad paper;

    static {
        pen = new DrawingTool();
        // paper = new SketchPad();
    }

    /**
     *
     * @return The area that the shape takes up.
     */
    abstract public double getArea();

    /**
     *
     * @return The perimeter that the shape takes up.
     */
    abstract public double getPerimeter();

    /**
     * Draw the shape on the canvas.
     */
    abstract public void draw();
    
}
