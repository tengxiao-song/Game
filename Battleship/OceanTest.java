import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

public class OceanTest {
    static PrintStream originalOut;
    static InputStream originalIn;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        originalOut = System.out;
        originalIn = System.in;
    }

    @AfterEach
    void tearDown() throws Exception {
        System.setOut(originalOut);
        System.in.close();
        System.setIn(originalIn);
    }

    @Test
    void OceanTest() {
        Ocean o = new Ocean();
        assertEquals(0, o.getHitCount());
        assertEquals(0, o.getShipsSunk());
        assertEquals(0, o.getShotsFired());
    }

    @Test
    void placeAllShipsRandomlyTest() {
        Ocean o = new Ocean();
        o.placeAllShipsRandomly();
        int emptyCount = 0;
        int battleshipCount = 0;
        int cruiserCount = 0;
        int destroyerCount = 0;
        int submarineCount = 0;
        String curr = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                curr = o.ships[i][j].getShipType();
                if (curr.equals("empty")) {
                    emptyCount++;
                } else if (curr.equals("Battleship")) {
                    battleshipCount++;
                } else if (curr.equals("Cruiser")) {
                    cruiserCount++;
                } else if (curr.equals("Destroyer")) {
                    destroyerCount++;
                } else {
                    submarineCount++;
                }
            }
        }
        assertEquals(80, emptyCount);
        assertEquals(4, battleshipCount);
        assertEquals(6, cruiserCount);
        assertEquals(6, destroyerCount);
        assertEquals(4, submarineCount);
    }

    @Test
    void isOccupiedTest() {
        Ocean o = new Ocean();
        assertFalse(o.isOccupied(0, 0));
    }

    @Test
    void shootAtTest() {
        Ocean o = new Ocean();
        Submarine s = new Submarine();
        s.placeShipAt(5, 5, true, o);
        assertTrue(o.isOccupied(5, 5));
        assertFalse(o.shootAt(0, 0));
        OutputStream outputStream = new ByteArrayOutputStream();
        originalOut = new PrintStream(outputStream);
        System.setOut(originalOut);
        assertTrue(o.shootAt(5, 5));
        assertEquals(1, o.getShipsSunk());
        assertEquals("You just sunk a Submarine.\n", outputStream.toString());
    }

    @Test
    void getShotsFiredTest() {
        Ocean o = new Ocean();
        for (int i = 0; i < 7; i++) {
            o.shootAt(0, 0);
        }
        assertEquals(7, o.getShotsFired());
    }

    @Test
    void getHitCountTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(5, 5, true, o);
        o.shootAt(5, 5);
        assertEquals(1, o.getHitCount());
        o.shootAt(5, 5);
        assertEquals(2, o.getHitCount());
        o.shootAt(5, 6);
        assertEquals(3, o.getHitCount());
        o.shootAt(5, 5);
        o.shootAt(5, 6);
        assertEquals(3, o.getHitCount());
    }

    @Test
    void getShipsSunkTest() {
        Ocean o = new Ocean();
        Submarine s = new Submarine();
        s.placeShipAt(5, 5, true, o);
        o.shootAt(5, 5);
        assertEquals(1, o.getShipsSunk());
        o.shootAt(5, 5);
        assertEquals(1, o.getShipsSunk());
    }

    @Test
    void isGameOver() {
        Ocean o = new Ocean();
        assertFalse(o.isGameOver());
        o.placeAllShipsRandomly();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                o.shootAt(i, j);
            }
        }
        assertTrue(o.isGameOver());
    }

    @Test
    void getShipArrayTest() {
        Ocean o = new Ocean();
        Ship[][] s;
        s = o.getShipArray();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals("empty", s[i][j].getShipType());
            }
        }
    }

    @Test
    void OceanPrintTest() {
        Ocean o = new Ocean();
        OutputStream outputStream = new ByteArrayOutputStream();
        originalOut = new PrintStream(outputStream);
        System.setOut(originalOut);
        Submarine s = new Submarine();
        s.placeShipAt(0, 0, true, o);
        Battleship b = new Battleship();
        b.placeShipAt(1, 1, false, o);
        o.shootAt(0, 0);
        o.shootAt(1, 1);
        o.shootAt(2, 0);
        o.print();
        StringTokenizer tokenizer = new StringTokenizer(outputStream.toString(), "\n");
        String[] out = new String[6];
        for (int i = 0; i < 6; i++) {
            out[i] = tokenizer.nextToken();
        }
        assertEquals("  0 1 2 3 4 5 6 7 8 9 ", out[1]);
        assertEquals("0 x . . . . . . . . . ", out[2]);
        assertEquals("1 . S . . . . . . . . ", out[3]);
        assertEquals("2 - . . . . . . . . . ", out[4]);
        assertEquals("3 . . . . . . . . . . ", out[5]);
    }
}
