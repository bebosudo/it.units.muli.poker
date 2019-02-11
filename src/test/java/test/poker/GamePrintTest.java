package test.poker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import poker.kata.Game;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GamePrintTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private Game createSampleGameFromArrayOfStrings(String[] handsStrings) {
        ArrayList<String> s = new ArrayList<>(Arrays.asList(handsStrings));
        return new Game(s);
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printSinglePlayer(){
        Game g = createSampleGameFromArrayOfStrings(new String[] {
                "6d Qs 6c 7c 3c Kh 3s\n"
        });

        g.print();

        assertThat(outContent.toString(), is(equalTo("6d Qs 6c 7c 3c Kh 3s Two Pairs (winner)\n")));
    }

    @Test
    public void printMultiPlayers(){
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "6d 2h 6h 2s 3d Qh 4s\n",
                "Js 3c 6h 2s 3d Qh 4s\n",
                "6s 7d 6h 2s 3d Qh 4s\n",
        });

        g.print();

        assertThat(outContent.toString(), is(equalTo("6d 2h 6h 2s 3d Qh 4s Two Pairs (winner)\n" +
                                                             "Js 3c 6h 2s 3d Qh 4s Pair\n" +
                                                             "6s 7d 6h 2s 3d Qh 4s Pair\n")));
    }

    @Test
    public void printMultiPlayersTwoWinners(){
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "6d 2h 6h 2s 3d Qh 4s\n",
                "Js 3c 6h 2s 3d Qh 4s\n",
                "6s 2d 6h 2s 3d Qh 4s\n",
        });

        g.print();

        assertThat(outContent.toString(), is(equalTo("6d 2h 6h 2s 3d Qh 4s Two Pairs (winner)\n" +
                                                             "Js 3c 6h 2s 3d Qh 4s Pair\n" +
                                                             "6s 2d 6h 2s 3d Qh 4s Two Pairs (winner)\n")));
    }

}
