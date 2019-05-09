package poker.kata;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class Hand {

    private String original;
    private ArrayList<Card> cards;
    private Rank score;

    private static final int MAX_HAND_SIZE = 7;
    private static final int MIN_HAND_SIZE = 2;
    private static final int VALID_HAND_SIZE = 5;
    private static final int FIND_PAIR = 2;
    private static final int FIND_SET = 3;
    private static final int FIND_QUAD = 4;
    private static final int FACE_DIFF_FOR_STRAIGHT = VALID_HAND_SIZE - 1;
    private static final int FACE_DIFF_FOR_STRAIGHT_5_TO_ACE = CardFace.FIVE.getValue() - CardFace.ACE.getValue();


    public Hand(String HandStr) {

        original = HandStr;
        cards = new ArrayList<>();

        String reg = "([TtJjQqKkAa\\d][cdshCSDH])";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(HandStr);


        while (mat.find()) {
            cards.add(new Card(mat.group()));
        }
        if (cards.size() < MIN_HAND_SIZE) {
            throw new IllegalArgumentException("Hand has too few cards. Need at least 2 for a fold");
        } else if (cards.size() < MAX_HAND_SIZE) {
            score = Rank.FOLD;
            return;
        } else if (cards.size() > MAX_HAND_SIZE) {
            throw new IllegalArgumentException("Hand has got too many cards");
        }

        setScore();
    }

    public Card getCard(int i) {
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


    public void sortByFaceDecreasing() {
        sortByFaceDecreasing(cards);
    }

    private void sortByFaceDecreasing(ArrayList<Card> toBeOrdered) {
        toBeOrdered.sort(Card.COMPARE_BY_FACE_DECR);
    }

    public void sortByFaceDecreasingAce1() {
        sortByFaceDecreasingAce1(cards);
    }

    private void sortByFaceDecreasingAce1(ArrayList<Card> toBeOrdered) {
        sortByFaceDecreasing(toBeOrdered);

        // All the Ace faces should be at the beginning of `cards'.
        ArrayList<Card> acesFound = toBeOrdered.stream().filter(Card::isAce)
                .collect(Collectors.toCollection(ArrayList::new));

        toBeOrdered.removeIf(Card::isAce);
        toBeOrdered.addAll(acesFound);
    }

    public boolean compareToCardsArray(ArrayList<Card> other) {
        if (other.size() != size()) {
            return false;
        } else {
            // limit(5) is used to compare only first 5, no need to compare more
            return IntStream.range(0, other.size()).mapToLong(i -> other.get(i).getFace()
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

    private void popCards(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo) {
        popCards(arrayFrom, arrayTo, 0, arrayFrom.size());
    }

    private void popCards(ArrayList<Card> arrayFrom, ArrayList<Card> arrayTo, int minIndex, int maxIndex) {
        int j = minIndex;
        while (j < maxIndex) {
            arrayTo.add(arrayFrom.remove(minIndex));
            j++;
        }
    }

    private boolean orderAndFindGroupsIntoCards(int... groupsLength) {
        this.sortByFaceDecreasing();
        ArrayList<Card> cardsBackup = new ArrayList<>(cards);
        ArrayList<Card> orderedCards = new ArrayList<>();

        //order groupsLength in decreasing order
        groupsLength = Arrays.stream(groupsLength).boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();


        for (int groupLength : groupsLength) {
            boolean found = searchForGroupOfCardsInArrayThenPopGroup(cards, orderedCards, groupLength);
            if (!found) {
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

    private ArrayList<Card> getDistinctCardsByFace(int limitSearchTo) {
        return cards.stream()
                .limit(limitSearchTo)
                .filter(Utils.distinctByKey(Card::getFace))
                .collect(Collectors.toCollection(ArrayList::new));
    }

        private int getIndexOfStraight(ArrayList<Card> searchArray) {
        // We need to check that the difference between 5 ORDERED and DISTINCT cards is exactly 5-1 to make a Straight,
        // or that there are card faces from 5 to Ace. 
        for (int i = 0; i < searchArray.size() - (VALID_HAND_SIZE - 1); i++) {
            int rankDifference = searchArray.get(i).getFace().getValue() - searchArray.get(i + VALID_HAND_SIZE - 1).getFace().getValue();
            if (rankDifference == FACE_DIFF_FOR_STRAIGHT || rankDifference == FACE_DIFF_FOR_STRAIGHT_5_TO_ACE) {
                return i;
            }
        }
        return -1;
    }

    private void overwriteCardsForNormalStraight(ArrayList<Card> searchArray, int firstCardOfStraight) {
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

    private boolean orderByStraight() {
        return orderByStraight(cards.size());
    }

    private boolean checkIsStraight(ArrayList<Card> toBeChecked) {
        int indexOfStraight = getIndexOfStraight(toBeChecked);

        if (indexOfStraight >= 0) {
            overwriteCardsForNormalStraight(toBeChecked, indexOfStraight);
            return true;
        }
        return false;
    }

    // we need to search up to a number when we are looking for a straight flush
    private boolean orderByStraight(int straightUpToNumber) {
        ArrayList<Card> partialCards = getDistinctCardsByFace(straightUpToNumber);
        sortByFaceDecreasing(partialCards);

        // There must be at least 5 unique cards to make a Straight.
        if (partialCards.size() < VALID_HAND_SIZE) {
            return false;
        }

        if (checkIsStraight(partialCards)) return true;

        sortByFaceDecreasingAce1(partialCards);
        return checkIsStraight(partialCards);
    }

    // Search for 5 cards with the same suit.
    private boolean orderByFlush() {

        Map<CardSuit, Long> numberBySuit =
                cards.stream()
                        .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));

        CardSuit suitFound = null;

        for (Map.Entry<CardSuit, Long> entry : numberBySuit.entrySet()) {
            if (entry.getValue() >= VALID_HAND_SIZE) {
                suitFound = entry.getKey();
            }
        }

        if (suitFound == null) {
            return false;
        }

        CardSuit finalSuitFound = suitFound;

        //add cards of the found suit to the orderedCards array
        ArrayList<Card> orderedCards = cards.stream()
                .filter(x -> x.getSuit() == finalSuitFound)
                .sorted(Card.COMPARE_BY_FACE_DECR)
                .collect(Collectors.toCollection(ArrayList::new));

        //push the leftovers at the end of the array
        for (Card card : cards) {
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
