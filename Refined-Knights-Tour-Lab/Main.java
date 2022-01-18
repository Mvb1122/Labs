import java.util.Scanner;

public class Main {
  static final int[][] chess_board = new int[8][8];
  public static void main(String[] args) {
    // Ask the user if they'd like to solve or to get a random one.
    Scanner s = new Scanner(System.in);
    System.out.println("Do you want to solve for a solution that reaches all cells or an accessibly-based solution? (Y for solve/N for the latter)");
    String input = s.next().toUpperCase();
    s.close();

    // Get a random path
    if (input.equals("N")) {
      // Create a knight at (0, 0)
      Knight k = new Knight(0, 0, chess_board);

      // Run a simulation until it's completed.
      int genNum = 0;
      while (true) {
        try {
          k.process();
          System.out.println("Tick number " + ++genNum + ": ");
          output(chess_board);
        } catch (tickException e) {
          System.out.println("The knight is unable to make any moves and the game is ended.");
          break;
        }
      }

    // Solve for a complete path.
    } else if (input.equals("Y")) {
      // Alert the user that this may take a while.
      System.out.println("This may take a while, I suggest going to get lunch.");

      // Solve for a viable path, using Multithreading, so it goes faster.
        // Get the number of cores-- the number of threads you can use before experiencing slow-down.
      int cores = Runtime.getRuntime().availableProcessors();

      System.out.println("Searching with " + cores + " thread" + (cores > 1 ? "s." : "."));

        // Create solver threads for each core.
      Thread[] solvers = new Thread[cores];

        // Create a multithreading-able reference so that the threads can check for completion every time they solve.
          // Note, we have to do it like this, since references to objects outside threads must be final
          // or effectively final.
      final var ref = new Object() {
        boolean complete = false;
        int genCounts = 0;
        int tries = 0;

        // These two methods have to be synchronized to avoid corrupting the data-- if a thread tries to write while another is reading, it could have disastrous effects.
          // Note, it doesn't actually matter, since this is only keeping track of statistics.
          // BTW, if you don't know what "synchronized" does, it basically means that only one thread can use this method at once.
        synchronized void increaseGenCountsBy(int numGens) {
          genCounts += numGens;
        }

        synchronized void incrementTries() {
          tries++;
        }
      };

      for (int i = 0; i < solvers.length; i++) {
        solvers[i] = new Thread(() -> {
          // Keep trying to find a solution until one is found.
          mainTryLoop:
          while (!ref.complete) {
            // End the thread if it's interrupted.
            if (Thread.interrupted()) return;

            int[][] solverBoard = new int[8][8];
            Knight k = new Knight(0, 0, solverBoard);
            int genNum = 0;
            justThisTryLoop:
            while (true) {
              try {
                k.process();
                genNum++;
              } catch (tickException e) {
                // Push the number of generations done this round onto the total counter.
                ref.increaseGenCountsBy(genNum);
                ref.incrementTries();

                // Check for completion by checking for unreached cells.
                for (int[] row : solverBoard) for (int cell : row) if (cell == 0) break justThisTryLoop;

                // If the table is complete, stop all other solvers by flipping the completion switch and interrupting all other threads.
                ref.complete = true;
                for (Thread t : solvers) t.interrupt();

                // output the table, so the user knows what path was taken.
                System.out.println("Valid solution found after " + ref.genCounts + " generations:");
                output(solverBoard);
                break mainTryLoop;
              }
            }
          }
        }, "Solver #" + i);
          /*
           Note, I set the priority on the threads to be as low as possible so that way you can do
           other stuff on your computer while this runs. It doesn't actually change the speed at which
           it goes, it just makes it so that your computer will focus on other stuff first, and then on the
           solving.
          */
        solvers[i].setPriority(Thread.MIN_PRIORITY);
        solvers[i].start();
      }

        // Create a thread to update the user on the solver's progress.
      Thread updater = new Thread(() -> {
        double numSeconds = 0;
        while (!ref.complete) {
          try {
            Thread.sleep(1000);
            numSeconds++;

            // Output information about the try.
            System.out.printf("Time taken, %2d minutes, %2d seconds. There have been %10d ticks, over %5d tries.%n", (int) Math.floor(numSeconds / 60f), (int) numSeconds % 60, ref.genCounts, ref.tries);
          } catch (InterruptedException e) { e.printStackTrace(); }
        }
      }, "Updater Thread");
      updater.start();
    }
  }

  public static void output(int[][] board) {
    // output data.
    for (int[] row : board) {
      for (int data : row) System.out.printf("%3d", data);
      System.out.println("");
    }
  }
}