import gpdraw.*;

class Main {
  public static void main(String[] args) {
    SketchPad s = new SketchPad(500, 500);
    // (int) (Math.floor(Math.random() * 90)) * 10
    // Double.POSITIVE_INFINITY
    int numThreads = 0;
    try {
      for (double i = 0; i < 300f; i++) {
        Thread t = new Thread(() -> {
          try { Thread.sleep(1000); } catch (InterruptedException e) { ; }
          DrawingTool pencil = new DrawingTool(s);

          while (true) {
            pencil.move((int) Math.floor(Math.random() * 5));
            if (Math.random() * 10 > 5) pencil.turn((int) Math.floor(Math.random() * 45));
            else pencil.turn((int) Math.floor(-Math.random() * 45));
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