/**
 * A ship with a length of two tiles.
 */
public class Destroyer extends Ship {

    /**
     * Sets the inherited {@literal length} variable and initializes the {@literal hit} array,
     * based on the size of this ship (2 tiles).
     */
    public Destroyer() {
        length = 2;
        hit = new boolean[]{false, false};
    }

    /**
     * @return "Destroyer", indicating that this is a Destroyer.
     */
    public String getShipType() {
        return "Destroyer";
    }
}
