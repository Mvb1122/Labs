package ceiling;

public class Light extends Tile {
    boolean isOn;

    public Light() {
        this(false);
    }

    public Light(boolean lightState) {
        isOn = lightState;
    }

    public boolean getState() { return isOn; }
    public void setState(Boolean b) { isOn = b; }

    @Override
    public String toString() {
        return "Light";
    }
}
