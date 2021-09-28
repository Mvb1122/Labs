import gpdraw.*;

class Main {
  public static void main(String[] args) {
    SketchPad s = new SketchPad(500, 500);
    // (int) (Math.floor(Math.random() * 90)) * 10
    int numThreads = 0;
    try {
      for (double i = 0; i < Double.POSITIVE_INFINITY; i++) {
        Thread t = new Thread(() -> {
          DrawingTool pencil = new DrawingTool(s);

          while (true) {
            pencil.move((int) Math.floor(Math.random() * 10));
            pencil.turn((int) Math.floor(Math.random() * 90));
          }
        });
        t.start();
        numThreads++;
      }
    } catch (java.lang.OutOfMemoryError e) {
      System.out.println("Ran out of memory at " + numThreads + " threads.");
    }

  }
}