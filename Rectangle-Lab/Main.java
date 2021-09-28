import gpdraw.*;
import shapes.*;

public class Main {
  public static void main(String[] args) {
    // Create the mouth.
    Rectangle r = new Rectangle(0, 0, 100, 20);

    // As an example, output the area of the mouth:
    System.out.println("The mouth takes up " + r.getArea() + " units squared.");

    // Draw the mouth onto the screen.
    r.draw();

    // Draw eyes.
    for (int i = 0; i < 2; i++) {
      Circle c = new Circle(i * 50 - 80, 50, 20);
      c.draw();
    }
  }


  /**
   * A function which draws a square with a DrawingTool and its size.
   * @param p The pencil with which to draw.
   * @param size The width of the square.
   */
  public static void drawSquare(DrawingTool p, int size) {
    p.forward(size);
    p.turnLeft(90);
    p.forward(size);
    p.turnLeft(90);
    p.forward(size);
    p.turnLeft(90);
    p.forward(size);
  }
}