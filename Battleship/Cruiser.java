/**
 * A ship with a length of three tiles.
 */
public class Cruiser extends Ship {

    /**
     * Sets the inherited {@literal length} variable and initializes the {@literal hit} array,
     * based on the size of this ship (3 tiles).
     */
    public Cruiser() {
        length = 3;
        hit = new boolean[]{false, false, false};
    }

    /**
     * @return "Cruiser", indicating that this is a Cruiser.
     */
    public String getShipType() {
        return "Cruiser";
    }
}
