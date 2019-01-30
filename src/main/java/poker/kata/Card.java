package poker.kata;

public class Card {
    public static int CLUBS = 1, SPADES = 2, HEARTS = 3, DIAMONDS = 4;
    public static int ACE = 1, TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6, SEVEN = 7, EIGHT = 8, NINE = 9,
                      TEN = 10, JACK = 11, QUEEN = 12, KING = 13;


    int rank;
    int suit;

    public Card(String card) {
        rank = ACE;
        suit = SPADES;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }
}
