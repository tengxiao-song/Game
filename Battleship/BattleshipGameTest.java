import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleshipGameTest {
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
    void askingInputsTest() {
        String data = "-1 ? 3 -2 abc 7";
        originalIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(originalIn);
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = new PrintStream(outputStream);
        System.setOut(originalOut);
        Scanner s = new Scanner(System.in);
        BattleshipGame.askingInputs(s);
        StringTokenizer tokenizer = new StringTokenizer(outputStream.toString(), ":");
        String[] out = new String[6];
        for (int i = 0; i < 6; i++) {
            out[i] = tokenizer.nextToken();
        }
        assertEquals("Enter the row number that you want to shot at", out[0]);
        assertEquals(" Invalid input. Please enter a valid number", out[1]);
        assertEquals(" Invalid input. Please enter a valid number", out[2]);
        assertEquals(" Enter the column number that you want to shot at", out[3]);
        assertEquals(" Invalid input. Please enter a valid number", out[4]);
        assertEquals(" Invalid input. Please enter a valid number", out[5]);
    }

    @Test
    void runShotTest() {
        OutputStream outputStream = new ByteArrayOutputStream();
        originalOut = new PrintStream(outputStream);
        System.setOut(originalOut);
        ArrayList<String> out = new ArrayList<>();
        Ocean o = new Ocean();
        o.placeAllShipsRandomly();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                BattleshipGame.runShot(o, i, j);
            }
        }
        StringTokenizer tokenizer = new StringTokenizer(outputStream.toString(), "\n");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            out.add(token);
        }
        assertEquals("You fired 100 shoots, hit 20 times, sunk 10 ships.", out.get(out.size() - 1));
    }

    @Test
    void rematchTest() {
        Ocean o = new Ocean();
        String data = "? y";
        originalIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(originalIn);
        Scanner s = new Scanner(System.in);
        OutputStream outputStream = new ByteArrayOutputStream();
        originalOut = new PrintStream(outputStream);
        System.setOut(originalOut);
        String res = BattleshipGame.rematch(s, o);
        assertEquals("y", res);
        StringTokenizer tokenizer = new StringTokenizer(outputStream.toString(), "\n");
        String[] out = new String[3];
        for (int i = 0; i < 3; i++) {
            out[i] = tokenizer.nextToken();
        }
        assertEquals("Would you like another game? Type y for Yes, n for No: ", out[1]);
        assertEquals("Invalid input. Enter again: ", out[2]);
    }

    @Test
    void getRow() {
        String data = "-1 x , 3 7 0";
        originalIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(originalIn);
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = new PrintStream(outputStream);
        System.setOut(originalOut);
        Scanner s = new Scanner(System.in);
        BattleshipGame.askingInputs(s);
        assertEquals(3, BattleshipGame.getRow());
    }

    @Test
    void getCol() {
        String data = "-1 x , 3 7 0";
        originalIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(originalIn);
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = new PrintStream(outputStream);
        System.setOut(originalOut);
        Scanner s = new Scanner(System.in);
        BattleshipGame.askingInputs(s);
        assertEquals(7, BattleshipGame.getCol());
    }
}