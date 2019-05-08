package test.poker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import poker.kata.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MainTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

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
    public void mainNormalRun() throws URISyntaxException, MalformedURLException {
        String phonyGame = Main.class.getClassLoader().getResource("testGame1").getPath();
        Main.main(new String[]{phonyGame});
        assertThat(outContent.toString().replaceAll("\\r",""), is(equalTo("Kc 9s Ks Ad 4d 3d 2s Pair\n" +
                "9s Ks 3d 8d 7s Ad 3s Pair\n" +
                "3s 7d 8s Ac Ad Kd 6d Pair\n" +
                "2h Ah Td 3h Th \n" +
                "Kd Td Ah As 2d 3d \n" +
                "Ts 3s 3d 4h 8s As 2s Flush (winner)\n")));
    }
}
