package poker.kata;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;


public class Hand {

    private String original;
    private ArrayList<Card> cards;
    private Rank score;

    private static final int MAX_HAND_SIZE = 7;
    private static final int VALID_HAND_SIZE = 5;
    private static final int NO_GROUP = 0;
    private static final int FIND_PAIR = 2;
    private static final int FIND_SET = 3;
    private static final int FIND_QUAD = 4;

    public Hand(String HandStr) {

        original = HandStr;
        cards = new ArrayList<>();

        String reg = "([TtJjQqKkAa\\d][cdshCSDH])";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(HandStr);


        while (mat.find()) {
            cards.add(new Card(mat.group()));
        }

        if (cards.size() != MAX_HAND_SIZE) {
            score = Rank.FOLD;
            return;
        }

        setScore();
    }

    private Card getCard(int i) {
        return cards.get(i);
    }
    String getOriginal() {
        return original;
    }

    // public ArrayList<Card> getCards() { return cards; }

    public Rank getScore() {
        return score;
    }

    public String printScore() {
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
                return "";
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


    private void sortByRankDecreasing() {
        cards.sort(Card.COMPARE_BY_FACE_DECR);
    }

    private void sortBySuit() {
        cards.sort(Card.COMPARE_BY_SUIT);
    }

    public boolean compareToCardsArray(Card[] other) {
        if (other.length != size()) {
            throw new ArrayIndexOutOfBoundsException("The two hands do not share the same size!");
        } else {
            // limit(5) is used to compare only first 5, no need to compare more
            return IntStream.range(0, other.length).mapToLong(i -> other[i].getFace()
                    .compareTo(getCard(i).getFace())).limit(VALID_HAND_SIZE).allMatch(x -> x == 0);
        }
    }

    private void searchForGroupOfCardsInArrayThenPopGroup(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int groupLength) {
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

    private boolean foundGroupIntoArray(ArrayList<Card> arr, int groupSize) {
        return arr.size() == groupSize;
    }

    private boolean findGroupsIntoOrderedCards(int lengthGroup1, int lengthGroup2) {
        int largerGroupSize = Math.max(lengthGroup1, lengthGroup2);
        int smallerGroupSize = Math.min(lengthGroup1, lengthGroup2);

        ArrayList<Card> cardsBackup = new ArrayList<>(cards);
        //This arraylist will store new cards ordered by (largerGroupSize + smallerGroupSize + everything else)
        ArrayList<Card> orderedCards = new ArrayList<>();

        searchForGroupOfCardsInArrayThenPopGroup(cards, orderedCards, largerGroupSize);

        //if the new arraylist has the size of the group, it means it has found the desided group, then proceeds
        //on to the next group
        if (foundGroupIntoArray(orderedCards, largerGroupSize)) {
            if (smallerGroupSize > 0) {
                searchForGroupOfCardsInArrayThenPopGroup(cards, orderedCards, smallerGroupSize);
                if (orderedCards.size() == largerGroupSize + smallerGroupSize) {
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

    private boolean orderByStraight(int straightUpToNumber) {
        ArrayList<Card> partialCards =
                cards.stream()
                        .limit(straightUpToNumber)
                        .sorted(Card.COMPARE_BY_FACE_DECR)
                        .filter(Utils.distinctByKey(Card::getFace))
                        .collect(Collectors.toCollection(ArrayList::new));

        // There must be at least 5 unique cards to make a Straight.
        if (partialCards.size() < VALID_HAND_SIZE) {
            return false;
        }

        // Check if the first three quintets are a Straight.
        for (int i = 0; i < partialCards.size() - 4; i++) {
            if (partialCards.get(i).getFace().getValue() - partialCards.get(i + 4).getFace().getValue() == 4) {

                // Overwrite the cards array with straight we just found.
                for (int fillCardsArrayId = 0; fillCardsArrayId < VALID_HAND_SIZE; fillCardsArrayId++) {
                    // The card to pop in the temp array will be always the same, because at the
                    // previous iteration we removed its left.
                    cards.set(fillCardsArrayId, partialCards.remove(i));
                }

                // Fill `cards' with the leftovers.
                int cardsToRemove = partialCards.size();
                for (int leftoversCardsId = 0; leftoversCardsId < cardsToRemove; leftoversCardsId++) {
                    cards.set(leftoversCardsId + VALID_HAND_SIZE, partialCards.remove(0));
                }

                return true;
            }
        }

        // Treat the Ace separately: check if there's a straight with 'A .. 5 4 3 2'.
        if (partialCards.get(0).getFace() == CardFace.ACE &&
                partialCards.get(partialCards.size() - 1).getFace() == CardFace.TWO &&
                partialCards.get(partialCards.size() - 4).getFace() == CardFace.FIVE) {

            for (int lastCardsId = 0; lastCardsId < 4; lastCardsId++) {
                cards.set(3 - lastCardsId, partialCards.remove(partialCards.size() - 1));
            }

            // Pop out the Ace to the last position of the straight.
            cards.set(4, partialCards.remove(0));

            // Fill `cards' with the leftovers.
            for (int leftoversCardsId = 0; leftoversCardsId < partialCards.size(); leftoversCardsId++) {
                cards.set(leftoversCardsId + VALID_HAND_SIZE, partialCards.remove(0));
            }

            return true;
        }

        return false;
    }

// Search for 5 cards with the same suit.
private boolean orderByFlush() {

    Map<CardSuit, Long> numberBySuit =
            cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));

    boolean found = false;
    CardSuit suitFound = CardSuit.CLUBS;  // Just a random suit, that it's overwritten later.
    for (Map.Entry<CardSuit, Long> entry: numberBySuit.entrySet()){
        if (entry.getValue() >= 5) {
            found = true;
            suitFound = entry.getKey();
        }
    }

    if (! found) { return false; }

    CardSuit finalSuitFound = suitFound;
    ArrayList<Card> orderedCards = cards.stream()
            .filter(x -> x.getSuit() == finalSuitFound)
            .sorted(Card.COMPARE_BY_FACE_DECR)
            .collect(Collectors.toCollection(ArrayList::new));

    for (Card card: cards) {
        if (card.getSuit() != suitFound) {
            orderedCards.add(card);
        }
    }

    cards = orderedCards;
    return true;
}

    private boolean orderByFull() {

        this.sortByRankDecreasing();

        return findGroupsIntoOrderedCards(FIND_SET, FIND_PAIR);

    }

    private boolean orderByQuad() {
        this.sortByRankDecreasing();
        return findGroupsIntoOrderedCards(FIND_QUAD, NO_GROUP);
    }

    private boolean orderByStraightFlush() {
        if (orderByFlush()) {

            // We know for sure that is a flush, so up to index 4 they are all the same suit; we need to understand how
            // many same-suit cards there are more than the 5 detected by the flush.
            int sameSuitIndex = 4;
            while (cards.get(0).getSuit().equals(cards.get(sameSuitIndex + 1).getSuit())) {
                sameSuitIndex++;
            }

            // The index is 1 less than the Number of same-suits.
            return orderByStraight(sameSuitIndex + 1);
        }

        return false;
    }
}
