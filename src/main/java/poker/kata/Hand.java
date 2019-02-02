package poker.kata;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Hand {

    private String original;
    private ArrayList<Card> cards;
    private CardHands score;

    public Hand(String HandStr) {
    private static final int FIND_PAIR = 2;
    private static final int FIND_SET = 3;

    public Hand(String HandStr){

        original = HandStr;
        cards = new ArrayList<Card>();

        String reg = "([TtJjQqKkAa\\d][cdshCSDH])";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(HandStr);

        int i = 0;
        while (mat.find()) {
            cards.add(new Card(mat.group()));
            i++;
        }

        setScore();
    }

    public Card getCards(int i) {
        return cards.get(i);
    }

    public String getOriginal() {
        return original;
    }

    public CardHands getScore() {
        return score;
    }

    public int compare(Hand h) {
        int r = 1;
        switch (score) {
            case HIGH_CARD:
                r = compareHigh(h);
                break;
            case PAIR:
                r = comparePair(h);
                break;
            case TWO_PAIRS:
                r = compareDouble(h);
                break;
            case THREE_OF_A_KIND:
                r = compareSet(h);
                break;
            case STRAIGHT:
                r = compareStraight(h);
                break;
            case FLUSH:
                r = compareFlush(h);
                break;
            case FULL_HOUSE:
                r = compareFull(h);
                break;
            case FOUR_OF_A_KIND:
                r = compareQuad(h);
                break;
            case STRAIGHT_FLUSH:
                r = compareStraightFlush(h);
                break;
        }
        return r;
    }


    private void setScore(){
        if( this.isFlush() && this.isStraight() ) {
            score = CardHands.STRAIGHT_FLUSH;
            return;
        }
        if( this.isQuad() ){
            score = CardHands.FOUR_OF_A_KIND;
            return;
        }
        if( this.isFull() ){
            score = CardHands.FULL_HOUSE;
            return;
        }
        if( this.isFlush() ){
            score = CardHands.FLUSH;
            return;
        }
        if( this.isStraight() ){
            score = CardHands.STRAIGHT;
            return;
        }
        if( this.isSet() ){
            score = CardHands.THREE_OF_A_KIND;
            return;
        }
        if( this.isDouble() ){
            score = CardHands.TWO_PAIRS;
            return;
        }
        if( this.isPair() ){
            score = CardHands.PAIR;
            return;
        }

        score = CardHands.HIGH_CARD;
    }


    private void sortByRank(){
        Collections.sort(cards, Card.COMPARE_BY_RANK);
    }

    private void sortByRankDecreasing() {
        Collections.sort(cards, Card.COMPARE_BY_RANK_DECR);
    }

    private void sortBySuit(){
        Collections.sort(cards, Card.COMPARE_BY_SUIT);
    }

    private void searchForGroupOfCardsAndPop(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int groupLength){
        //search into arrayFrom if it can find a given group of cards, then it pops the group from arrayFrom
        //and pushes it into arrayTo
        for(int i=0; i<arrayFrom.size()-groupLength+1; i++) {
            if (cards.get(i).getRank().equals(cards.get(i+groupLength-1).getRank()))
                popCards(arrayFrom, arrayTo, i, i+groupLength);
        }
    }

    private void popCards(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int minIndex, int maxIndex){
        int j=minIndex;
        while(j<maxIndex){
            arrayTo.add(arrayFrom.remove(minIndex));
            j++;
        }
    }


    private boolean findGroupsIntoOrderedCards(int findGroup1, int findGroup2) {
        int groupLarger = Math.max(findGroup1, findGroup2);
        int groupSmaller = Math.min(findGroup1, findGroup2);

        ArrayList<Card> cardsBackup = cards.stream().collect(Collectors.toCollection(ArrayList::new));
        //This arraylist will store new cards ordered by (groupLarger + groupSmaller + everything else)
        ArrayList<Card> orderedCards = new ArrayList();

        searchForGroupOfCardsAndPop(cards, orderedCards, groupLarger);

        //if the new arraylist has the size of the group, it means it has found the desided group, then proceeds
        //on to the next group
        if(orderedCards.size() == groupLarger){
            if(groupSmaller > 0) {
                searchForGroupOfCardsAndPop(cards, orderedCards, groupSmaller);
                if (orderedCards.size() == groupLarger + groupSmaller) {
                    //duplicate code - remove
                    popCards(cards, orderedCards, 0, cards.size());
                    //replaces the cards arrayList with the new one
                    cards = orderedCards;
                    return true;

                } else {
                    //found first group, but not the second, revert to initial status and return false
                    cards = cardsBackup;
                    return false;
                }
            }else{
                //duplicate code - remove
                popCards(cards, orderedCards, 0, cards.size());
                //replaces the cards arrayList with the new one
                cards = orderedCards;
                return true;
            }
        }else{
            return false;
        }
    }

    private boolean isPair(){
        boolean b = false;
        this.sortByRankDecreasing();
        for( int i=0; i<6; i++){
            if( cards.get(i).getRank().equals( cards.get(i+1).getRank()) ) {
                b = true;
                break;
            }
        }
        return b;
    }

    private boolean isDouble() {
        boolean b = false;
        this.sortByRankDecreasing();
        for(int i=0; i<6; i++){
            if( cards.get(i).getRank().equals( cards.get(i+1).getRank()) ){
                for( int j=i+2; j<6; j++ ){
                    if( cards.get(j).getRank().equals( cards.get(j+1).getRank())){
                        b = true;
                        break;
                    }
                }
            }
        }
        return b;
    }

    // tris
    private boolean isSet() {
        boolean b = false;
        this.sortByRankDecreasing();
        for( int i=0; i<5; i++ ){
            if( cards.get(i).getRank().equals( cards.get(i+2).getRank()) ){
                b = true;
                break;
            }
        }
        return b;
    }

    private boolean isStraight() {
        this.sortByRank();
        Card[] uniqueSorted = cards.stream().filter(Utils.distinctByKey(Card::getRank)).toArray(Card[]::new);

        // There must be at least 5 unique cards to make a Straight.
        if (uniqueSorted.length < 5) {
            return false;
        }

        // Check if the first three quintets are a Straight.
        for (int i = 0; i < uniqueSorted.length - 4; i++) {
            if (uniqueSorted[i + 4].getRank().getValue() - uniqueSorted[i].getRank().getValue() == 4) {
                return true;
            }
        }

        // Treat the Ace separately: check if there's a straight with '2 3 4 5 .. A'.
        if (getCards(uniqueSorted.length - 1).getRank() == CardRank.ACE &&
                uniqueSorted[0].getRank() == CardRank.TWO &&
                uniqueSorted[3].getRank() == CardRank.FIVE) {
            return true;
        }

        return false;
    }


    private boolean isFlush() {
        boolean b = false;
        this.sortBySuit();
        for (int i = 0; i < 3; i++) {
            if (cards.get(i).getSuit().equals(cards.get(i + 4).getSuit())) {
                b = true;
                break;
            }
        }
        return b;
    }


    private boolean isFull(){

        // after sorting the pair can be in front 22444 or after 44455
        // write a function that put the tris in front of it (useful also for compareFull() )
        this.sortByRankDecreasing();

        return findGroupsIntoOrderedCards(FIND_SET, FIND_PAIR);

    }


    private boolean isQuad() {
        boolean b = false;
        this.sortByRankDecreasing();
        for( int i=0; i<4; i++ ){
            if( cards.get(i).getRank().equals( cards.get(i+3).getRank()) ){
                b = true;
                break;
            }
        }
        return b;
    }


    // -1 this is smaller, 0 equals, 1 the other is better
    private int compareHigh(Hand h) {
        return 1;
    }

    private int comparePair(Hand h) {
        return 1;
    }

    private int compareDouble(Hand h) {
        return 1;
    }

    private int compareSet(Hand h) {
        return 1;
    }

    private int compareStraight(Hand h) {
        return 1;
    }

    private int compareFlush(Hand h) {
        return 1;
    }

    private int compareFull(Hand h) {
        return 1;
    }

    private int compareQuad(Hand h) {
        return 1;
    }

    private int compareStraightFlush(Hand h) {
        return 1;
    }


    public void printCards() {
        for (int i = 0; i < 7; i++) {
            System.out.println(String.valueOf(i + 1) + ":  " + cards.get(i).getRank() + " of " + cards.get(i).getSuit());
        }
    }

    public static void main(String[] args) {
        Hand h = new Hand("8d 5d 8c Th 8s 4h Kc");
        h.printCards();
        System.out.println("\n");

        System.out.println(h.isStraight());


//        h.sortBySuit();
//        h.printCards();
//        System.out.println("\n");
//        System.out.println("\n");
//        System.out.println(h.isPair());
//        h.setScore();
//        System.out.println(h.getScore());
    }
}
