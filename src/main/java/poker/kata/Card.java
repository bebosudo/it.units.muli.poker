package poker.kata;

public class Card {
    private CardRank rank;
    private CardSuit suit;

    public Card(String card) {
        rank = CardRank.ACE;
        suit = CardSuit.SPADES;
    }

    public CardRank getRank() {
        return rank;
    }

    public CardSuit getSuit() {
        return suit;
    }
}

