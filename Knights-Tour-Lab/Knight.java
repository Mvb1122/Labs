public class Knight {
  int x, y, moves;
  static int[][] world;

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
    // Generate a random number to decide which direction to move in.
      // NOTE: I put it in a loop to allow it to retry until it's certain that there are no valid moves.
    int numTries = 0;
    tryLoop:
    while (true) {
      if (numTries++ == 7) {
        // Handle the knight being stuck by throwing an error and catching it way back in the Main.java loop.
        throw new tickException();
      }
      int moveNum = (int) Math.floor(Math.random() * 8);

      // Use said randomly generated number to decide on a move to make.
      switch (moveNum) {
        case 0:
          if (validateMove(x + 1, y + 2)) {
            // Move up two and over one.
            x++;
            y += 2;

            // Since the move was successful, we can stop the loop that tries to move.
            break tryLoop;
          }

          // If the move wasn't successful, we run the loop again.
          continue tryLoop;

        case 1:
          if (validateMove(x + 2, y + 1)) {
            x += 2;
            y++;
            break tryLoop;
          }
          continue tryLoop;

        case 2:
          if (validateMove(x + 2, y - 1)) {
            x += 2;
            y--;
            break tryLoop;
          }
          continue tryLoop;

        case 3:
          if (validateMove(x + 1, y - 2)) {
            x++;
            y -= 2;
            break tryLoop;
          }
          continue tryLoop;

        case 4:
          if (validateMove(x - 1, y - 2)) {
            x--;
            y -= 2;
            break tryLoop;
          }
          continue tryLoop;

        case 5:
          if (validateMove(x - 2, y - 1)) {
            x -= 2;
            y--;
            break tryLoop;
          }
          continue tryLoop;

        case 6:
          if (validateMove(x - 2, y + 1)) {
            x -= 2;
            y++;
            break tryLoop;
          }
          continue tryLoop;

        case 7:
          if (validateMove(x - 1, y + 2)) {
            x--;
            y += 2;
            break tryLoop;
          }
          continue tryLoop;
        }
    }

    // Once a move has been decided on, attribute it.
    world[x][y] = ++moves;
    
  }

  /**
  *  This method checks if the space selected is valid to move to or not.
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