package poker.kata;

import java.util.Comparator;

public class Card{
    private CardFace face;
    private CardSuit suit;

    public Card(String card) {
        char rankChar = card.charAt(0);
        char suitChar = card.charAt(1);

        // TODO: add exceptions in defaults?
        switch (rankChar) {
            case 'A':
            case 'a':
                face = CardFace.ACE;
                break;
            case '2':
                face = CardFace.TWO;
                break;
            case '3':
                face = CardFace.THREE;
                break;
            case '4':
                face = CardFace.FOUR;
                break;
            case '5':
                face = CardFace.FIVE;
                break;
            case '6':
                face = CardFace.SIX;
                break;
            case '7':
                face = CardFace.SEVEN;
                break;
            case '8':
                face = CardFace.EIGHT;
                break;
            case '9':
                face = CardFace.NINE;
                break;
            case 'T':
            case 't':
                face = CardFace.TEN;
                break;
            case 'J':
            case 'j':
                face = CardFace.JACK;
                break;
            case 'Q':
            case 'q':
                face = CardFace.QUEEN;
                break;
            case 'K':
            case 'k':
                face = CardFace.KING;
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

    public CardFace getFace() {
        return face;
    }

    public CardSuit getSuit() {
        return suit;
    }


    public static Comparator<Card> COMPARE_BY_RANK = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.face.compareTo(o2.face);
        }
    };

    public static Comparator<Card> COMPARE_BY_RANK_DECR = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o2.face.compareTo(o1.face);
        }
    };

    public static Comparator<Card> COMPARE_BY_SUIT = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.suit.compareTo(o2.suit);
        }
    };


}

