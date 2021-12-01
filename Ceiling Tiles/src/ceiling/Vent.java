package ceiling;

public class Vent extends Tile {
    static boolean isOn;

    public static void setIsOn(boolean state) {
        isOn = state;
    }

    public static boolean getIsOn() {
        return isOn;
    }

    @Override
    public String toString() {
        return "Vent";
    }
}
