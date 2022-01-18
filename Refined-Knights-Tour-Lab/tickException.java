import java.lang.Exception;

public class tickException extends Exception {
  public tickException() {
    super("The knight was unable to make a move-- game ended.");
  }
}