import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {
    @Test
    void getBowColumn() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(3, 5, true, o);
        assertEquals(5, d.getBowColumn());
    }

    @Test
    void getBowRow() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(3, 5, true, o);
        assertEquals(3, d.getBowRow());
    }

    @Test
    void getLengthTest() {
        Destroyer d = new Destroyer();
        assertEquals(2, d.getLength());
    }

    @Test
    void isHorizontalTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(3, 5, true, o);
        assertTrue(d.isHorizontal());
    }

    @Test
    void isSunkTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(3, 5, true, o);
        o.shootAt(3, 5);
        assertFalse(d.isSunk());
        o.shootAt(3, 6);
        assertTrue(d.isSunk());
    }

    @Test
    void okToPlaceShipAtTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        assertFalse(d.okToPlaceShipAt(0, 9, true, o));
        assertFalse(d.okToPlaceShipAt(9, 8, false, o));
        assertTrue(d.okToPlaceShipAt(3, 3, false, o));
        assertTrue(d.okToPlaceShipAt(5, 5, true, o));
        d.placeShipAt(5, 5, true, o);
        Destroyer d2 = new Destroyer();
        assertFalse(d2.okToPlaceShipAt(5, 4, true, o));
        assertFalse(d.okToPlaceShipAt(4, 5, false, o));
    }

    @Test
    void placeShipAtTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(5, 5, true, o);
        assertTrue(o.getShipArray()[5][5].getShipType().equals("Destroyer"));
        assertTrue(o.getShipArray()[5][6].getShipType().equals("Destroyer"));
        assertFalse(o.getShipArray()[5][7].getShipType().equals("Destroyer"));
    }

    @Test
    void setBowColumnTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.setBowColumn(5);
        assertEquals(5, d.getBowColumn());
    }

    @Test
    void setBowRowTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.setBowRow(1);
        assertEquals(1, d.getBowRow());
    }

    @Test
    void setHorizontal() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.setHorizontal(true);
        assertTrue(d.isHorizontal());
    }

    @Test
    void shootAtTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(5, 5, true, o);
        assertTrue(d.shootAt(5, 5));
        assertTrue(d.shootAt(5, 5));
        assertFalse(d.shootAt(10, 60));
        assertTrue(d.shootAt(5, 6));
        assertFalse(d.shootAt(5, 6));
    }

    @Test
    void toStringTest() {
        Ocean o = new Ocean();
        Destroyer d = new Destroyer();
        d.placeShipAt(5, 5, true, o);
        assertEquals("S", d.toString());
        d.shootAt(5, 6);
        d.shootAt(5, 5);
        assertEquals("x", d.toString());
    }

    @Test
    void EmptySeaTest() {
        EmptySea e = new EmptySea();
        assertEquals(1, e.getLength());
        assertEquals("empty", e.getShipType());
        assertFalse(e.isSunk());
        assertFalse(e.shootAt(0, 0));
        assertEquals("-", e.toString());
    }

    @Test
    void BattleshipTest() {
        Battleship b = new Battleship();
        assertEquals(4, b.getLength());
        assertEquals("Battleship", b.getShipType());
    }

    @Test
    void CruiserTest() {
        Cruiser c = new Cruiser();
        assertEquals(3, c.getLength());
        assertEquals("Cruiser", c.getShipType());
    }

    @Test
    void DestroyerTest() {
        Destroyer d = new Destroyer();
        assertEquals(2, d.getLength());
        assertEquals("Destroyer", d.getShipType());
    }

    @Test
    void SubmarineTest() {
        Submarine s = new Submarine();
        assertEquals(1, s.getLength());
        assertEquals("Submarine", s.getShipType());
    }
}
