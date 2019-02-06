package poker.kata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Game {

    private ArrayList<Hand> hands;
    private ArrayList<Rank> ranks;
    private boolean[] winners;

    public Game(String[] handsStr) {
        hands = Arrays.stream(handsStr).map(Hand::new).collect(Collectors.toCollection(ArrayList::new));
        ranks = hands.stream().map(Hand::getScore).collect(Collectors.toCollection(ArrayList::new));
    }

    public Game(String filename){
        this(readFileFromFilename(filename));
    }

    private static String[] readFileFromFilename(String filename) {
        ArrayList<String> handStr = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while(line != null){
                handStr.add(line);
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("\n\nFile '" + filename + "' not found. Exiting program now.\n\n");
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handStr.toArray(String[]::new);
    }

    public ArrayList<Rank> getRanks(){
        return ranks;
    }


}
