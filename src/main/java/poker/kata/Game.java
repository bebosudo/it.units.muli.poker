package poker.kata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private ArrayList<Hand> hands;
    private ArrayList<Rank> ranks;
    private boolean[] winners;

    public Game(ArrayList<String> handsStr) {
        hands = handsStr.stream().map(Hand::new).collect(Collectors.toCollection(ArrayList::new));
        ranks = hands.stream().map(Hand::getScore).collect(Collectors.toCollection(ArrayList::new));
    }

    public Game(String filename){
        this(readFileFromFilename(filename));
    }

    private static ArrayList<String> readFileFromFilename(String filename) {
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
        return handStr;
    }

    public ArrayList<Rank> getRanks(){
        return ranks;
    }

    public int getWinner(){
        return IntStream.range(0, ranks.size())
                .boxed()
                .max(Comparator.comparing(ranks::get))
                .get();
        //ranks.stream().max(Comparator.comparing(Rank::getValue));
    }


}
