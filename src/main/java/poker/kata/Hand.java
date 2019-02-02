package poker.kata;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Hand {

    private String original;
    private ArrayList<Card> cards;
    private Rank score;

    private static final int NO_GROUP = 0;
    private static final int FIND_PAIR = 2;
    private static final int FIND_SET = 3;
    private static final int FIND_QUAD = 4;

    public Hand(String HandStr) {

        original = HandStr;
        cards = new ArrayList();

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

    public Card getCard(int i) {
        return cards.get(i);
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public String getOriginal() {
        return original;
    }

    public Rank getScore() {
        return score;
    }

    public int size() {
        return cards.size();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return Objects.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "original='" + original + '\'' +
                ", cards=" + cards +
                ", score=" + score +
                '}';
    }

    private void setScore() {
        if( this.orderByStraightFlush() ) {
            score = Rank.STRAIGHT_FLUSH;
            return;
        }
        if (this.orderByQuad()) {
            score = Rank.FOUR_OF_A_KIND;
            return;
        }
        if (this.orderByFull()) {
            score = Rank.FULL_HOUSE;
            return;
        }
        if (this.orderByFlush()) {
            score = Rank.FLUSH;
            return;
        }
        if (this.orderByStraight()) {
            score = Rank.STRAIGHT;
            return;
        }
        if (this.orderBySet()) {
            score = Rank.THREE_OF_A_KIND;
            return;
        }
        if (this.orderByDouble()) {
            score = Rank.TWO_PAIRS;
            return;
        }
        if (this.orderByPair()) {
            score = Rank.PAIR;
            return;
        }

        score = Rank.HIGH_CARD;
    }


    private void sortByRank() {
        Collections.sort(cards, Card.COMPARE_BY_RANK);
    }

    private void sortByRankDecreasing() {
        Collections.sort(cards, Card.COMPARE_BY_RANK_DECR);
    }

    private void sortBySuit() {
        Collections.sort(cards, Card.COMPARE_BY_SUIT);
    }

    private void searchForGroupOfCardsAndPop(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int groupLength) {
        //search into arrayFrom if it can find a given group of cards, then it pops the group from arrayFrom
        //and pushes it into arrayTo
        for (int i = 0; i < arrayFrom.size() - groupLength + 1; i++) {
            if (cards.get(i).getFace().equals(cards.get(i + groupLength - 1).getFace())) {
                popCards(arrayFrom, arrayTo, i, i + groupLength);
                return;
            }
        }
    }

    private void popCards(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int minIndex, int maxIndex) {
        int j = minIndex;
        while (j < maxIndex) {
            arrayTo.add(arrayFrom.remove(minIndex));
            j++;
        }
    }

    private boolean switchCardArraysAndReturnTrue(ArrayList<Card> orderedCards) {
        popCards(cards, orderedCards, 0, cards.size());
        //replaces the cards arrayList with the new one
        cards = orderedCards;
        return true;
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
        if (orderedCards.size() == groupLarger) {
            if (groupSmaller > 0) {
                searchForGroupOfCardsAndPop(cards, orderedCards, groupSmaller);
                if (orderedCards.size() == groupLarger + groupSmaller) {
                    return switchCardArraysAndReturnTrue(orderedCards);

                } else {
                    cards = cardsBackup;
                    return false;
                }
            } else {
                return switchCardArraysAndReturnTrue(orderedCards);
            }
        } else {
            return false;
        }
    }

    private boolean orderByPair() {
        this.sortByRankDecreasing();
        return findGroupsIntoOrderedCards(FIND_PAIR, NO_GROUP);
    }

    private boolean orderByDouble() {
        this.sortByRankDecreasing();
        return findGroupsIntoOrderedCards(FIND_PAIR, FIND_PAIR);
    }

    private boolean orderBySet() {
        this.sortByRankDecreasing();
        return findGroupsIntoOrderedCards(3, 0);
    }

    private boolean orderByStraight() {
        return orderByStraight(cards.size());
    }

    private boolean orderByStraight(int straightUpToIndex) {
        ArrayList<Card> partialCards =
                cards.stream()
                        .limit(straightUpToIndex)
                        .sorted(Card.COMPARE_BY_RANK_DECR)
                        .filter(Utils.distinctByKey(Card::getFace))
                        .collect(Collectors.toCollection(ArrayList::new));

        // There must be at least 5 unique cards to make a Straight.
        if (partialCards.size() < 5) {
            return false;
        }

        // Treat the Ace separately: check if there's a straight with 'A .. 5 4 3 2'.
        if (partialCards.get(0).getFace() == CardFace.ACE &&
                partialCards.get(partialCards.size() - 1).getFace() == CardFace.TWO &&
                partialCards.get(partialCards.size() - 4).getFace() == CardFace.FIVE) {

            for (int lastCardsId = 0; lastCardsId < 4; lastCardsId++) {
                cards.set(3 - lastCardsId, partialCards.remove(partialCards.size() - 1 ));
            }

            // Pop out the Ace to the last position of the straight.
            cards.set(4, partialCards.remove(0));

            // Fill `cards' with the leftovers.
            for (int leftoversCardsId = 0; leftoversCardsId < partialCards.size(); leftoversCardsId++) {
                cards.set(leftoversCardsId + 5, partialCards.remove(0));
            }

            return true;
        }

        // Check if the first three quintets are a Straight.
        for (int i = 0; i < partialCards.size() - 4; i++) {
            if (partialCards.get(i).getFace().getValue() - partialCards.get(i + 4).getFace().getValue() == 4) {

                // Overwrite the cards array with straight we just found.
                for (int fillCardsArrayId = 0; fillCardsArrayId < 5; fillCardsArrayId++) {
                    // The card to pop in the temp array will be always the same, because at the
                    // previous iteration we removed its left.
                    cards.set(fillCardsArrayId, partialCards.remove(i));
                }

                // Fill `cards' with the leftovers.
                int cardsToRemove = partialCards.size();
                for (int leftoversCardsId = 0; leftoversCardsId < cardsToRemove; leftoversCardsId++) {
                    cards.set(leftoversCardsId + 5, partialCards.remove(0));
                }

                return true;
            }
        }

        return false;
    }

    private boolean orderByFlush() {
        boolean b = false;
        this.sortBySuit();
        int i=0, j=0;


        for( i=0; i<3; i++){
            while( j<6 && cards.get(j+1).getSuit().equals( cards.get(i).getSuit() )  ){
                j++;
            }
            if ( (j-i) >= 4 ){
                b = true;
                break;
            }
            i = j;
        }

        ArrayList<Card> orderedCards = new ArrayList();

        for( int ii=i; ii<=j; ii++ ){
            orderedCards.add( cards.get(ii) );
        }

        for( int ii=0; ii<7; ii++ ){
            if( ii<i || ii>j ){
                orderedCards.add( cards.get(ii));
            }
        }

        cards = orderedCards;

        return b;
    }

    private boolean orderByFull() {

        // after sorting the pair can be in front 22444 or after 44455
        // write a function that put the tris in front of it (useful also for compareFull() )
        this.sortByRankDecreasing();

        return findGroupsIntoOrderedCards(FIND_SET, FIND_PAIR);

    }

    private boolean orderByQuad() {
        this.sortByRankDecreasing();
        return findGroupsIntoOrderedCards(FIND_QUAD, NO_GROUP);
    }

    private boolean orderByStraightFlush(){
        return false;
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
            System.out.println(String.valueOf(i + 1) + ":  " + cards.get(i).getFace() + " of " + cards.get(i).getSuit());
        }
    }

    public static void main(String[] args) {
        Hand h = new Hand("8d 5d 8c Td 9h 4d Kd");
        h.printCards();
        System.out.println("\n");


//        h.sortBySuit();
//        h.printCards();
//        System.out.println("\n");
//        System.out.println("\n");
//        System.out.println(h.isPair());
//        h.setScore();
//        System.out.println(h.getScore());
    }
}
