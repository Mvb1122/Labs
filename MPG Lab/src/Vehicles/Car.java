package Vehicles;

public class Car {
    int startingMiles, endingMiles;
    double gallonsUsed;

    /**
     * Create a new instance of a Car object with a starting amount of miles.
     * @param odometer The amount of miles to start with.
     */
    public Car(int odometer) {
        startingMiles = odometer;
    }

    /**
     * Simulates filling up the tank, recording the current odometer reading
     * and the number of gallons filled.
     * @param odometer The current reading of the car's odometer.
     * @param gallons The amount of Gas to load into the car.
     */
    public void fillUp(int odometer, double gallons) {
        gallonsUsed += gallons;
        endingMiles = odometer;
    }

    /**
     * Fill up the car's tank.
     * @param gallons The amount of gallons to fill up by.
     */
    public void fillUp(double gallons) {
        gallonsUsed += gallons;
    }

    /**
     * Calculates the car's miles-per-gallon and returns it.
     * @return The miles per gallon that the car has.
     */
    public double calculateMPG() {
        int milesTraveled = endingMiles - startingMiles;
        return milesTraveled / gallonsUsed;
    }

    /**
     * Resets the myStartMiles to the current odometer reading and
     * resets the gallons to zero.
     */
    public void resetMPG() {
        startingMiles = endingMiles;
        gallonsUsed = 0;
    }

    /**
     * @return The odometer reading.
     */
    public int getOdometer() {
        return Math.abs(endingMiles - startingMiles);
    }



}
