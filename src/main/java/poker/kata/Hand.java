package poker.kata;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;


public class Hand {

    private String original;
    private ArrayList<Card> cards;
    private Rank score;

    public static final int MAX_HAND_SIZE = 7;
    public static final int MIN_HAND_SIZE = 2;
    public static final int VALID_HAND_SIZE = 5;
    public static final int FIND_PAIR = 2;
    public static final int FIND_SET = 3;
    public static final int FIND_QUAD = 4;

    public Hand(String HandStr) {

        original = HandStr;
        cards = new ArrayList<>();

        String reg = "([TtJjQqKkAa\\d][cdshCSDH])";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(HandStr);


        while (mat.find()) {
            cards.add(new Card(mat.group()));
        }
        if (cards.size() < MIN_HAND_SIZE){
            throw new IllegalArgumentException("Hand has too few cards. Need at least 2 for a fold");
        }else if (cards.size() < MAX_HAND_SIZE) {
            score = Rank.FOLD;
            return;
        }else if(cards.size() > MAX_HAND_SIZE){
            throw new IllegalArgumentException("Hand has got too many cards");
        }

        setScore();
    }

    private Card getCard(int i) {
        return cards.get(i);
    }
    String getOriginal() {
        return original;
    }


    public Rank getScore() {
        return score;
    }

    public String scoreString() {
        switch (score) {
            case FOLD:
                return "";
            case HIGH_CARD:
                return "High Cards";
            case PAIR:
                return "Pair";
            case TWO_PAIRS:
                return "Two Pairs";
            case THREE_OF_A_KIND:
                return "Three Of A Kind";
            case STRAIGHT:
                return "Straight";
            case FLUSH:
                return "Flush";
            case FULL_HOUSE:
                return "Full House";
            case FOUR_OF_A_KIND:
                return "Four Of A Kind";
            case STRAIGHT_FLUSH:
                return "Straight Flush";
            default:
                throw new IllegalArgumentException("Unknown score requested: '" + score.toString() + "'");
        }
    }

    public int size() {
        return cards.size();
    }

    public int compareTo(Hand h) {
        int compareScores = this.getScore().compareTo(h.getScore());
        //compareScores is !=0 if one of the two hands' score is larger than the other one
        if (compareScores != 0) {
            return compareScores;
        }
        //if the scores are the same, I need to compare kickers
        //since the hands' cards are ordered, I can operate a pairwise comparison
        for (int i = 0; i < VALID_HAND_SIZE; i++) {
            int compareCards = this.getCard(i).getFace().compareTo(h.getCard(i).getFace());
            if (compareCards != 0) {
                return compareCards;
            }
        }
        return 0;
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
        if (this.orderByStraightFlush()) {
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


    public void sortByRankDecreasing() {
        cards.sort(Card.COMPARE_BY_FACE_DECR);
    }

    /*private void sortBySuit() {
        cards.sort(Card.COMPARE_BY_SUIT);
    }*/

    public boolean compareToCardsArray(Card[] other) {
        if (other.length != size()) {
            return false;
        } else {
            // limit(5) is used to compare only first 5, no need to compare more
            return IntStream.range(0, other.length).mapToLong(i -> other[i].getFace()
                    .compareTo(getCard(i).getFace())).limit(VALID_HAND_SIZE).allMatch(x -> x == 0);
        }
    }
    private boolean searchForGroupOfCardsInArrayThenPopGroup(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int groupLength) {
        //search into arrayFrom if it can find a given group of cards, then it pops the group from arrayFrom
        //and pushes it into arrayTo
        for (int i = 0; i < arrayFrom.size() - groupLength + 1; i++) {
            if (cards.get(i).getFace().equals(cards.get(i + groupLength - 1).getFace())) {
                popCards(arrayFrom, arrayTo, i, i + groupLength);
                return true;
            }
        }
        return false;
    }

    private void popCards(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo){
        popCards(arrayFrom, arrayTo, 0, arrayFrom.size());
    }

    private void popCards(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int minIndex, int maxIndex) {
        int j = minIndex;
        while (j < maxIndex) {
            arrayTo.add(arrayFrom.remove(minIndex));
            j++;
        }
    }


    private boolean orderAndFindGroupsIntoCards(int... groupsLength){
        this.sortByRankDecreasing();
        ArrayList<Card> cardsBackup = new ArrayList<>(cards);
        ArrayList<Card> orderedCards = new ArrayList<>();

        //order groupsLength in decreasing order
        groupsLength = Arrays.stream(groupsLength).boxed()
                                                  .sorted(Comparator.reverseOrder())
                                                  .mapToInt(Integer::intValue)
                                                  .toArray();


        for(int groupLength : groupsLength){
            boolean found = searchForGroupOfCardsInArrayThenPopGroup(cards, orderedCards, groupLength);
            if(!found){
                //if not found, revert to backup
                cards = cardsBackup;
                return false;
            }
        }

        //pop leftovers of cards into orderedcards, then overwrite
        popCards(cards, orderedCards);
        cards = orderedCards;
        return true;

    }

    private boolean orderByPair() {

        return orderAndFindGroupsIntoCards(FIND_PAIR);
    }

    private boolean orderByDouble() {

        return orderAndFindGroupsIntoCards(FIND_PAIR, FIND_PAIR);
    }

    private boolean orderBySet() {

        return orderAndFindGroupsIntoCards(FIND_SET);
    }

    private ArrayList<Card> getDistinctCardsByFace(int limitSearchTo){
        return cards.stream()
                .limit(limitSearchTo)
                .sorted(Card.COMPARE_BY_FACE_DECR)
                .filter(Utils.distinctByKey(Card::getFace))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int getIndexOfNormalStraightInFaceArray(ArrayList<Card> searchArray){
        for (int i = 0; i < searchArray.size() - (VALID_HAND_SIZE - 1); i++) {
            if (searchArray.get(i).getFace().getValue() - searchArray.get(i + VALID_HAND_SIZE - 1).getFace().getValue() == (VALID_HAND_SIZE - 1)) {
                return i;
            }
        }
        return -1;
    }

    private int getIndexOfAceToFiveStraightInFaceArray(ArrayList<Card> searchArray){
        if (searchArray.get(0).getFace() == CardFace.ACE &&
                searchArray.get(searchArray.size() - 1).getFace() == CardFace.TWO &&
                searchArray.get(searchArray.size() - 4).getFace() == CardFace.FIVE) {
            return 0;
        }
        return -1;
    }

    private void overwriteCardsForNormalStraight(ArrayList<Card> searchArray, int firstCardOfStraight){
        //first remove straight and place it on top of the cards array
        for (int fillCardsArrayId = 0; fillCardsArrayId < VALID_HAND_SIZE; fillCardsArrayId++) {
            // The card to pop in the temp array will be always the same, because at the
            // previous iteration we removed its left.
            cards.set(fillCardsArrayId, searchArray.remove(firstCardOfStraight));
        }

        //then overwrite with the leftovers
        for (int leftoversCardsId = 0; leftoversCardsId < searchArray.size(); leftoversCardsId++) {
            cards.set(leftoversCardsId + VALID_HAND_SIZE, searchArray.remove(0));
        }
    }

    private void overwriteCardsForAceToFiveStraight(ArrayList<Card> searchArray){
        //first set the cards from 5 to 2 on top of the cards array
        for (int lastCardsId = 0; lastCardsId < VALID_HAND_SIZE - 1; lastCardsId++) {
            cards.set(3 - lastCardsId, searchArray.remove(searchArray.size() - 1));
        }

        // Pop out the Ace to the last position of the straight.
        cards.set(VALID_HAND_SIZE - 1, searchArray.remove(0));

        // Fill `cards' with the leftovers.
        for (int leftoversCardsId = 0; leftoversCardsId < searchArray.size(); leftoversCardsId++) {
            cards.set(leftoversCardsId + VALID_HAND_SIZE, searchArray.remove(0));
        }
    }

    private boolean orderByStraight() {
        return orderByStraight(cards.size());
    }


    // we need to search up to a number when we are looking for a straight flush
    private boolean orderByStraight(int straightUpToNumber) {
        ArrayList<Card> partialCards = getDistinctCardsByFace(straightUpToNumber);

        // There must be at least 5 unique cards to make a Straight.
        if (partialCards.size() < VALID_HAND_SIZE) {
            return false;
        }

        int indexOfStraight = getIndexOfNormalStraightInFaceArray(partialCards);

        if(indexOfStraight >= 0){
            overwriteCardsForNormalStraight(partialCards, indexOfStraight);
            return true;
        }

        //check if there's a straight with 'A .. 5 4 3 2'.
        indexOfStraight = getIndexOfAceToFiveStraightInFaceArray(partialCards);

        if(indexOfStraight >= 0){
            overwriteCardsForAceToFiveStraight(partialCards);
            return true;
        }

        return false;
    }

    // Search for 5 cards with the same suit.
    private boolean orderByFlush() {

        Map<CardSuit, Long> numberBySuit =
                cards.stream()
                        .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));

        CardSuit suitFound = null;

        for (Map.Entry<CardSuit, Long> entry: numberBySuit.entrySet()){
            if (entry.getValue() >= VALID_HAND_SIZE) {
                suitFound = entry.getKey();
            }
        }

        if (suitFound == null) { return false; }

        CardSuit finalSuitFound = suitFound;

        //add cards of the found suit to the orderedCards array
        ArrayList<Card> orderedCards = cards.stream()
                .filter(x -> x.getSuit() == finalSuitFound)
                .sorted(Card.COMPARE_BY_FACE_DECR)
                .collect(Collectors.toCollection(ArrayList::new));

        //push the leftovers at the end of the array
        for (Card card: cards) {
            if (card.getSuit() != suitFound) {
                orderedCards.add(card);
            }
        }

        cards = orderedCards;
        return true;
    }

    private boolean orderByFull() {

        return orderAndFindGroupsIntoCards(FIND_SET, FIND_PAIR);

    }

    private boolean orderByQuad() {

        return orderAndFindGroupsIntoCards(FIND_QUAD);
    }

    private boolean orderByStraightFlush() {
        if (orderByFlush()) {

            // We know for sure that is a flush, so up to index 4 they are all the same suit; we need to understand how
            // many same-suit cards there are more than the 5 detected by the flush.
            int sameSuitIndex = VALID_HAND_SIZE - 1;
            while (cards.get(0).getSuit().equals(cards.get(sameSuitIndex + 1).getSuit())) {
                sameSuitIndex++;
            }

            // The index is 1 less than the Number of same-suits.
            return orderByStraight(sameSuitIndex + 1);
        }

        return false;
    }
}
