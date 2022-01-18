import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Knight {
  int x, y, moves;
  static int[][] world;
  static int[][] accessibleWorld;

  static {
    try {
      Scanner s = new Scanner(new File("access.txt"));

      // IMPLNOTE: Assumes that the world is a 8x8 grid.
      accessibleWorld = new int[8][8];
      for (int i = 0; i < accessibleWorld.length; i++) for (int j = 0; j < accessibleWorld.length; j++) {
        accessibleWorld[i][j] = s.nextInt();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a new knight at the specified location.
   * @param x The X-location of the knight.
   * @param y The Y-location of the knight.
   * @param world The world in which the knight is located.
   */
  public Knight(int x, int y, int[][] world) {
    this.x = x; this.y = y; Knight.world = world;
    world[x][y] = ++moves;
  }

  /**
   *  Moves the knight in a random location.
   */
  public void process() throws tickException {
    // Find the best move from the Knight's location.
    int bestMove = findBestMove();

    if (bestMove != Integer.MAX_VALUE) {
      // Move to the space.
      move(bestMove);
    } else {
      throw new tickException();
    }
  }

  /**
   * This method moves the knight to the specified location.
   * @param bestMove The best move, specified by the findBestMove() mathod.
   */
  private void move(int bestMove) {
    x += xTrans[bestMove];
    y += yTrans[bestMove];
    world[x][y] = ++moves;
  }


  // Create arrays to hold the transforms on the knight's location.
  static int[] xTrans = new int[]{1, 2, 2, 1, -1, -2, -2, -1};
  static int[] yTrans = new int[]{2, 1, -1, -2, -2, -1, 1, 2};
  /**
   * This method chooses the best available move based on the Knight's location
   * and the accessibility grid.
   * @return A number, Zero through 7 which determines a move.
   * It can also return the Maximum Integer value, if there are no valid moves.
   */
  private int findBestMove() {
    // Find the best move.
    // Create variables to hold the move's index 'n stuff.
    int lowestAccessibilityScore = Integer.MAX_VALUE;
    int lowestAccessibleIndex = Integer.MAX_VALUE;
    // Loop through all the moves.
    for (int i = 0; i < xTrans.length; i++) {
      // Evaluate if the move is the best one yet, or if it's worse.
      // Note, the AIOOBE exception here is ignored because moves outside the world obviously aren't valid.
      try {
        if (accessibleWorld[x + xTrans[i]][y + yTrans[i]] < lowestAccessibilityScore && validateMove(x + xTrans[i], y + yTrans[i])) {
          // If this is the best move so far, write it down.
          lowestAccessibilityScore = accessibleWorld[x + xTrans[i]][y + yTrans[i]];
          lowestAccessibleIndex = i;
        }
      } catch (ArrayIndexOutOfBoundsException ignored) {;}
    }

    // Return said best move.
    return lowestAccessibleIndex;
  }

  /**
   *  This method checks if the space selected is valid to move to or not.
   * @return true if the move is valid, false if it's invalid or falls outside the grid.
   */
  private boolean validateMove(int x, int y) {
    try {
      int t = world[x][y];
      return t == 0;
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
}