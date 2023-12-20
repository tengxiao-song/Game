import java.util.HashSet;
import java.util.Random;

/**
 * This class manages the game state by keeping track of what entity is
 * contained in each position on the game board.
 *
 * @author harry
 */
public class Ocean implements OceanInterface {

    /**
     * A 10x10 2D array of Ships, which can be used to quickly determine which ship
     * is in any given location.
     */
    protected Ship[][] ships;

    /**
     * The total number of shots fired by the user
     */
    protected int shotsFired;

    /**
     * The number of times a shot hit a ship. If the user shoots the same part of a
     * ship more than once, every hit is counted, even though the additional "hits"
     * don't do the user any good.
     */
    protected int hitCount;

    /**
     * The number of ships totally sunk.
     */
    protected int shipsSunk;

    /**
     * A set to keep track the locations shot at
     */
    private HashSet<Integer> location;

    /**
     * Creates an "empty" ocean, filling every space in the <code>ships</code> array
     * with EmptySea objects. Should also initialize the other instance variables
     * appropriately.
     */
    public Ocean() {
        ships = new Ship[10][10];
        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[0].length; j++) {
                ships[i][j] = new EmptySea();
            }
        }
        hitCount = 0;
        shotsFired = 0;
        shipsSunk = 0;
        location = new HashSet<>();
    }

    /**
     * Place all ten ships randomly on the (initially empty) ocean. Larger ships
     * must be placed before smaller ones to avoid cases where it may be impossible
     * to place the larger ships.
     *
     * @see java.util.Random
     */
    public void placeAllShipsRandomly() {
        int battleshipNum = 1;
        int cruiserNum = 2;
        int destroyerNum = 3;
        int submarineNum = 4;
        Random random = new Random();
        int row;
        int col;
        boolean horizontal;
        while (battleshipNum != 0) {
            row = random.nextInt(10);
            col = random.nextInt(10);
            horizontal = random.nextBoolean();
            Battleship battleship = new Battleship();
            if (battleship.okToPlaceShipAt(row, col, horizontal, this)) {
                battleship.placeShipAt(row, col, horizontal, this);
                battleshipNum -= 1;
            }
        }
        while (cruiserNum != 0) {
            row = random.nextInt(10);
            col = random.nextInt(10);
            horizontal = random.nextBoolean();
            Cruiser cruiser = new Cruiser();
            if (cruiser.okToPlaceShipAt(row, col, horizontal, this)) {
                cruiser.placeShipAt(row, col, horizontal, this);
                cruiserNum -= 1;
            }
        }
        while (destroyerNum != 0) {
            row = random.nextInt(10);
            col = random.nextInt(10);
            horizontal = random.nextBoolean();
            Destroyer destroyer = new Destroyer();
            if (destroyer.okToPlaceShipAt(row, col, horizontal, this)) {
                destroyer.placeShipAt(row, col, horizontal, this);
                destroyerNum -= 1;
            }
        }
        while (submarineNum != 0) {
            row = random.nextInt(10);
            col = random.nextInt(10);
            horizontal = random.nextBoolean();
            Submarine submarine = new Submarine();
            if (submarine.okToPlaceShipAt(row, col, horizontal, this)) {
                submarine.placeShipAt(row, col, horizontal, this);
                submarineNum -= 1;
            }
        }
    }

    /**
     * Checks if this coordinate is not empty; that is, if this coordinate does not
     * contain an EmptySea reference.
     *
     * @param row    the row (0 to 9) in which to check for a floating ship
     * @param column the column (0 to 9) in which to check for a floating ship
     * @return {@literal true} if the given location contains a ship, and
     * {@literal false} otherwise.
     */
    public boolean isOccupied(int row, int column) {
        return !ships[row][column].getShipType().equals("empty");
    }

    /**
     * Fires a shot at this coordinate. This will update the number of shots that
     * have been fired (and potentially the number of hits, as well). If a location
     * contains a real, not sunk ship, this method should return {@literal true}
     * every time the user shoots at that location. If the ship has been sunk,
     * additional shots at this location should return {@literal false}.
     *
     * @param row    the row (0 to 9) in which to shoot
     * @param column the column (0 to 9) in which to shoot
     * @return {@literal true} if the given location contains an afloat ship (not an
     * EmptySea), {@literal false} if it does not.
     */
    public boolean shootAt(int row, int column) {
        shotsFired++;
        location.add(row * 10 + column);
        if (ships[row][column].shootAt(row, column)) {
            hitCount++;
            if (ships[row][column].isSunk()) {
                shipsSunk++;
                System.out.printf("You just sunk a %s.\n", ships[row][column].getShipType());
            }
            return true;
        }
        return false;
    }

    /**
     * @return the number of shots fired in this game.
     */
    public int getShotsFired() {
        return this.shotsFired;
    }

    /**
     * @return the number of hits recorded in this game.
     */
    public int getHitCount() {
        return this.hitCount;
    }

    /**
     * @return the number of ships sunk in this game.
     */
    public int getShipsSunk() {
        return this.shipsSunk;
    }

    /**
     * @return {@literal true} if all ships have been sunk, otherwise
     * {@literal false}.
     */
    public boolean isGameOver() {
        return shipsSunk == 10;
    }

    /**
     * Provides access to the grid of ships in this Ocean. The methods in the Ship
     * class that take an Ocean parameter must be able to read and even modify the
     * contents of this array. While it is generally undesirable to allow methods in
     * one class to directly access instancce variables in another class, in this
     * case there is no clear and elegant alternatives.
     *
     * @return the 10x10 array of ships.
     */
    public Ship[][] getShipArray() {
        return ships;
    }

    /**
     * Prints the ocean. To aid the user, row numbers should be displayed along the
     * left edge of the array, and column numbers should be displayed along the top.
     * Numbers should be 0 to 9, not 1 to 10. The top left corner square should be
     * 0, 0.
     * <ul>
     * <li>Use 'S' to indicate a location that you have fired upon and hit a (real)
     * ship</li>
     * <li>'-' to indicate a location that you have fired upon and found nothing
     * there</li>
     * <li>'x' to indicate a location containing a sunken ship</li>
     * <li>'.' (a period) to indicate a location that you have never fired
     * upon.</li>
     * </ul>
     * <p>
     * This is the only method in Ocean that has any printing capability, and it
     * should never be called from within the Ocean class except for the purposes of
     * debugging.
     */
    public void print() {
        System.out.print("  ");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < ships.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < ships[0].length; j++) {
                if (location.contains(i * 10 + j)) {
                    System.out.print(ships[i][j].toString() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
