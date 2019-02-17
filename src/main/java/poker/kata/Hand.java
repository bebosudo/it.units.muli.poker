package poker.kata;

import java.util.*;
import java.util.Map.Entry;
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

    public boolean compareToCardsArray(Card[] other) {
        if (other.length != size()) {
            throw new ArrayIndexOutOfBoundsException("The two hands do not share the same size!");
        } else {
            // only VALID_HAND_SIZE(=5) cards need to be examined, no need to compare more in Texas Hold'em.
            return IntStream.range(0, VALID_HAND_SIZE)
                    .mapToLong(i -> other[i].getFace().compareTo(getCard(i).getFace()))
                    .allMatch(x -> x == 0);
        }
    }

    private boolean findAndOrderFaceDuplicates(int lengthGroup1, int lengthGroup2) {
        // TODO: Maybe we can avoid re-ordering input here, and assume the caller will put the bigger group size first?
        int largerGroupSize = Math.max(lengthGroup1, lengthGroup2);
        int smallerGroupSize = Math.min(lengthGroup1, lengthGroup2);

        // Sort the pairs first on the higher number of occurrences, then on the value of faces.
        ArrayList<Entry<CardFace, Long>> occurByFace =
                cards.stream()
                        .collect(Collectors.groupingBy(Card::getFace, Collectors.counting()))
                        .entrySet().stream()
                        .filter(keyVal -> keyVal.getValue() == largerGroupSize || keyVal.getValue() == smallerGroupSize)
                        .sorted(Comparator.comparingLong(Entry::getValue))
                        .sorted((e1, e2) -> e2.getKey().getValue() - e1.getKey().getValue())
                        .collect(Collectors.toCollection(ArrayList::new));

        // If no groups are found, exit.
        if (occurByFace.size() == 0) {
            return false;
        }

        // If we were asked to search for a second group, but we only found one set of faces, groups are `404'.
        if (smallerGroupSize != NO_GROUP && occurByFace.size() == 1) {
            return false;
        }

        // If the largest groups found don't match with the size requested, 404.
        if (occurByFace.get(0).getValue() != largerGroupSize
                || (occurByFace.size() == 2 && occurByFace.get(1).getValue() != smallerGroupSize)) {
            return false;
        }

        // Order on the two groups found above.
        ArrayList<Card> orderedCards = new ArrayList<>();

        for (Entry<CardFace, Long> groupId : occurByFace) {
            for (Card card : cards) {
                if (card.getFace() == groupId.getKey()) {
                    orderedCards.add(card);
                }
            }
        }

        // Sort all the cards, but later ignore those with suits already extracted, so that the kickers are the cards
        // with higher Face values.
        sortByRankDecreasing();

        for (Card card : cards) {
            // Conversion to Set to filter the remaining cards don't have the same face of the previous groups.
            if (!occurByFace.stream().map(Entry::getKey).collect(Collectors.toSet()).contains(card.getFace())) {
                orderedCards.add(card);
            }
        }

        cards = orderedCards;
        return true;
    }

    private boolean orderByPair() {
        return findAndOrderFaceDuplicates(FIND_PAIR, NO_GROUP);
    }

    private boolean orderByDouble() {
        return findAndOrderFaceDuplicates(FIND_PAIR, FIND_PAIR);
    }

    private boolean orderBySet() {
        return findAndOrderFaceDuplicates(FIND_SET, NO_GROUP);
    }

    private boolean orderByFull() {
        return findAndOrderFaceDuplicates(FIND_SET, FIND_PAIR);
    }

    private boolean orderByQuad() {
        return findAndOrderFaceDuplicates(FIND_QUAD, NO_GROUP);
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
        for (Entry<CardSuit, Long> entry : numberBySuit.entrySet()) {
            if (entry.getValue() >= 5) {
                found = true;
                suitFound = entry.getKey();
            }
        }

        if (!found) {
            return false;
        }

        CardSuit finalSuitFound = suitFound;
        ArrayList<Card> orderedCards = cards.stream()
                .filter(x -> x.getSuit() == finalSuitFound)
                .sorted(Card.COMPARE_BY_FACE_DECR)
                .collect(Collectors.toCollection(ArrayList::new));

        for (Card card : cards) {
            if (card.getSuit() != suitFound) {
                orderedCards.add(card);
            }
        }

        cards = orderedCards;
        return true;
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
