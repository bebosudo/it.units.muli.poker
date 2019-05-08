package poker.kata;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.net.URL;


public class Game {

    private ArrayList<Hand> hands;
    private ArrayList<Rank> ranks;
    // private boolean[] winners;

    public Game(ArrayList<String> handsStr) {
        hands = handsStr.stream().map(Hand::new).collect(Collectors.toCollection(ArrayList::new));
        ranks = hands.stream().map(Hand::getScore).collect(Collectors.toCollection(ArrayList::new));
    }

    public Game(URL filename) {
        // Call the first Game ctor with the lines extracted from `filename'
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

    public Rank getRank(int i) {
        return ranks.get(i);
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

        //There's one single winner (or none)
        if (bestHandsIndices.length < 2) {
            return bestHandsIndices;
        }

        return sortIndicesByComparingHands(bestHandsIndices)
                .mapToInt(Integer::intValue)
                .toArray();
    }


    public void print() {
        ArrayList<String> players = hands.stream().map(x -> x.getOriginal().replace('\n', ' ') + x.scoreString()).collect(Collectors.toCollection(ArrayList::new));

        int[] winners = getWinners();

        for (int win : winners) {
            players.set(win, players.get(win).replaceAll("$", " (winner)"));
        }

        players.stream().forEach(System.out::println);
    }
}
