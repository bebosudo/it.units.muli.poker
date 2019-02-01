package poker.kata;

import java.util.Comparator;

public class Card{
    private CardRank rank;
    private CardSuit suit;

    public Card(String card) {
        char rankChar = card.charAt(0);
        char suitChar = card.charAt(1);

        // TODO: add exceptions in defaults?
        switch (rankChar) {
            case 'A':
            case 'a':
                rank = CardRank.ACE;
                break;
            case '2':
                rank = CardRank.TWO;
                break;
            case '3':
                rank = CardRank.THREE;
                break;
            case '4':
                rank = CardRank.FOUR;
                break;
            case '5':
                rank = CardRank.FIVE;
                break;
            case '6':
                rank = CardRank.SIX;
                break;
            case '7':
                rank = CardRank.SEVEN;
                break;
            case '8':
                rank = CardRank.EIGHT;
                break;
            case '9':
                rank = CardRank.NINE;
                break;
            case 'T':
            case 't':
                rank = CardRank.TEN;
                break;
            case 'J':
            case 'j':
                rank = CardRank.JACK;
                break;
            case 'Q':
            case 'q':
                rank = CardRank.QUEEN;
                break;
            case 'K':
            case 'k':
                rank = CardRank.KING;
                break;
        }

        switch (suitChar) {
            case 'S':
            case 's':
                suit = CardSuit.SPADES;
                break;
            case 'C':
            case 'c':
                suit = CardSuit.CLUBS;
                break;
            case 'H':
            case 'h':
                suit = CardSuit.HEARTS;
                break;
            case 'D':
            case 'd':
                suit = CardSuit.DIAMONDS;
                break;
        }
    }

    public CardRank getRank() {
        return rank;
    }

    public CardSuit getSuit() {
        return suit;
    }


    public static Comparator<Card> COMPARE_BY_RANK = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.rank.compareTo(o2.rank);
        }
    };

    public static Comparator<Card> COMPARE_BY_SUIT = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.suit.compareTo(o2.suit);
        }
    };
}

