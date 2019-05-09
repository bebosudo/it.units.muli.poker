package poker.kata;

import java.util.Comparator;
import java.util.Objects;

public class Card {
    private CardFace face;
    private CardSuit suit;

    private CardFace parseFaceCharacter(char faceChar) {
        switch (java.lang.Character.toUpperCase(faceChar)) {
            case 'A':
                return CardFace.ACE;
            case '2':
                return CardFace.TWO;
            case '3':
                return CardFace.THREE;
            case '4':
                return CardFace.FOUR;
            case '5':
                return CardFace.FIVE;
            case '6':
                return CardFace.SIX;
            case '7':
                return CardFace.SEVEN;
            case '8':
                return CardFace.EIGHT;
            case '9':
                return CardFace.NINE;
            case 'T':
                return CardFace.TEN;
            case 'J':
                return CardFace.JACK;
            case 'Q':
                return CardFace.QUEEN;
            case 'K':
                return CardFace.KING;
            default:
                throw new IllegalArgumentException("Unknown face char passed: '" + faceChar + "'");
        }
    }

    private CardSuit parseSuitCharacter(char suitChar) {
        switch (java.lang.Character.toUpperCase(suitChar)) {
            case 'S':
                return CardSuit.SPADES;

            case 'C':
                return CardSuit.CLUBS;

            case 'H':
                return CardSuit.HEARTS;

            case 'D':
                return CardSuit.DIAMONDS;

            default:
                throw new IllegalArgumentException("Unknown suit char passed: '" + suitChar + "'");

        }
    }

    public Card(String card) {
        char faceChar = card.charAt(0);
        char suitChar = card.charAt(1);

        face = parseFaceCharacter(faceChar);
        suit = parseSuitCharacter(suitChar);

    }

    public CardFace getFace() {
        return face;
    }
    public boolean isAce() { return face == CardFace.ACE; }

    public CardSuit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return face == card.face &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(face, suit);
    }

    @Override
    public String toString() {
        return "Card{" +
                "face=" + face +
                ", suit=" + suit +
                '}';
    }

    public static Comparator<Card> COMPARE_BY_FACE_DECR = (o1, o2) -> o2.face.compareTo(o1.face);
}