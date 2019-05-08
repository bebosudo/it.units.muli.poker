package poker.kata;

import java.io.File;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        //first element should be the filename containing the game
        if (args.length < 1) {
            System.out.println("Usage: scriptName path/to/gamefile");
            System.exit(1);
        }

        File gameFile = new File(args[0]);

        Game g = new Game(gameFile.toURI().toURL());
        g.print();
    }
}
