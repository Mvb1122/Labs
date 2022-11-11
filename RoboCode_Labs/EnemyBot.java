package mb; 
import robocode.*;

public class EnemyBot {
  private double bearing, distance, energy, heading, velocity;
  private String name;

  public double getBearing() { return bearing; }
  public double getDistance(){ return distance; }
  public double getEnergy(){ return energy; }
  public double getHeading(){ return heading; }
  public double getVelocity(){ return velocity; }
  public String getName() { return name; }
  
  public void update(ScannedRobotEvent scannedRobotEvent) {
    bearing = scannedRobotEvent.getBearing();
    distance = scannedRobotEvent.getDistance();
    energy = scannedRobotEvent.getEnergy();
    heading = scannedRobotEvent.getHeading();
    velocity = scannedRobotEvent.getVelocity();
    name = scannedRobotEvent.getName();
  }

  /**
   * Resets the variable.
   */
  public void reset() {
    bearing = 0; distance = 0; energy = 0; heading = 0; velocity = 0;
    name = "";
  }

  /**
   * Evaluates if this variable is currently hosting an enemy.
   * @return True if a bot is detected, false if not.
   */
  public boolean none() {
    return name.equals("");
  }

  public EnemyBot() {
    reset();
  }

  public EnemyBot(ScannedRobotEvent host) {
    update(host);
  }
}