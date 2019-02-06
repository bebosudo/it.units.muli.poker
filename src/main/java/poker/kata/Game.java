package poker.kata;

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

    public ArrayList<Rank> getRanks(){
        return ranks;
    }


}
