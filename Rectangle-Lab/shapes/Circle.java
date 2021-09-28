package shapes;

import shapes.*;

public class Circle extends shape {
    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.width = radius;
    }

    public double getArea() {
        return Math.PI * Math.pow(width, 2);
    }

    public double getPerimeter() {
        return 2 * Math.PI * width;
    }
    
    public void draw() {
        pen.up();
        pen.move(x, y);
        pen.down();

        pen.drawCircle(width);
    }
}
