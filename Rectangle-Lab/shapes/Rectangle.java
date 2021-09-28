package shapes;

import shapes.*;
import gpdraw.*;

public class Rectangle extends shape {

    public Rectangle() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getArea(){
        return height * width;
    }

    public double getPerimeter() {
        return (height * 2) + (width * 2);
    }

    public void draw() {
        pen.up();
        pen.move(x, y);
        pen.down();
        pen.setDirection(90);

        for (int i = 0; i < 2; i++) {
            pen.forward(height);
            pen.turnLeft();
            pen.forward(width);
            pen.turnLeft();
        }
    }
}
