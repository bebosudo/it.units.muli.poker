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
import java.util.stream.Stream;

import java.net.URL;


public class Game {

    private ArrayList<Hand> hands;
    private ArrayList<Rank> ranks;
    private boolean[] winners;

    public Game(ArrayList<String> handsStr) {
        hands = handsStr.stream().map(Hand::new).collect(Collectors.toCollection(ArrayList::new));
        ranks = hands.stream().map(Hand::getScore).collect(Collectors.toCollection(ArrayList::new));
    }

    public Game(URL filename) {
        this(readFileFromFilename(filename.getFile()));
    }

    private static ArrayList<String> readFileFromFilename(String filename) {
        ArrayList<String> handStr = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while (line != null) {
                handStr.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n\nFile '" + filename + "' not found. Exiting program now.\n\n");
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handStr;
    }

    public ArrayList<Rank> getRanks() {
        return ranks;
    }

    private Stream<Integer> sortIndicesByComparingHands(int[] indicesToCompare) {
        return Arrays.stream(indicesToCompare)
                .boxed()
                .sorted((index1, index2) -> -hands.get(index1).compareTo(hands.get(index2)));
    }

    public int[] getWinners() {
        Rank maxScore = ranks.stream().max(Comparator.comparing(Rank::getValue)).get();

        int[] bestHandsIndices = IntStream.range(0, ranks.size())
                .filter(i -> ranks.get(i).equals(maxScore))
                .toArray();

        if (bestHandsIndices.length == 0 || bestHandsIndices.length == 1) {
            return bestHandsIndices;
        }

        return sortIndicesByComparingHands(bestHandsIndices)
                .mapToInt(Integer::intValue)
                .toArray();
    }


    public void print(){

        System.out.println(this.hands.get(0).getOriginal());

    }

}
