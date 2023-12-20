/**
 * A Ship with a length of four tiles.
 */
public class Battleship extends Ship {

    /**
     * Sets the inherited {@literal length} variable and initializes the {@literal hit} array,
     * based on the size of this ship (4 tiles).
     */
    public Battleship() {
        length = 4;
        hit = new boolean[]{false, false, false, false};
    }

    /**
     * @return "Battleship", indicating that this is a Battleship.
     */
    public String getShipType() {
        return "Battleship";
    }
}
