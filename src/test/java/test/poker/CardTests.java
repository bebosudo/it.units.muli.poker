package test.poker;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import poker.kata.Card;
import poker.kata.CardRank;
import poker.kata.CardSuit;

public class CardTests {

    @Test
    public void parseAceOfSpades() {
        Card c = new Card("As");
        assertThat(c.getRank(), is(equalTo(CardRank.ACE)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.SPADES)));
    }

    @Test
    public void parseTwoOfSpades() {
        Card c = new Card("2s");
        assertThat(c.getRank(), is(equalTo(CardRank.TWO)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.SPADES)));
    }

    @Test
    public void parseTenOfDiamonds() {
        Card c = new Card("Td");
        assertThat(c.getRank(), is(equalTo(CardRank.TEN)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.DIAMONDS)));
    }

    @Test
    public void parseTenOfDiamondsInvertedUppercase() {
        Card c = new Card("tD");
        assertThat(c.getRank(), is(equalTo(CardRank.TEN)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.DIAMONDS)));
    }

    @Test
    public void parseTenOfDiamondsAllLower() {
        Card c = new Card("td");
        assertThat(c.getRank(), is(equalTo(CardRank.TEN)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.DIAMONDS)));
    }

    @Test
    public void parseTenOfDiamondsAllUpper() {
        Card c = new Card("TD");
        assertThat(c.getRank(), is(equalTo(CardRank.TEN)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.DIAMONDS)));
    }

    @Test
    public void parseAllAvailableCards() {
        String[] suitsStr = {"s", "c", "h", "d"};
        CardSuit[] suitsClass = {CardSuit.SPADES, CardSuit.CLUBS, CardSuit.HEARTS, CardSuit.DIAMONDS};

        String[] ranksStr = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q"};
        CardRank[] ranksClass = {CardRank.ACE, CardRank.TWO, CardRank.THREE, CardRank.FOUR,
                CardRank.FIVE, CardRank.SIX, CardRank.SEVEN, CardRank.EIGHT, CardRank.NINE,
                CardRank.TEN, CardRank.JACK, CardRank.QUEEN, CardRank.KING};

        for (int rankId = 0; rankId < ranksStr.length; rankId++) {
            for (int suitId = 0; suitId < suitsStr.length; suitId++) {

                Card c = new Card(ranksStr[rankId] + suitsStr[suitId]);

                assertThat(c.getRank(), is(equalTo(ranksClass[rankId])));
                assertThat(c.getSuit(), is(equalTo(suitsClass[suitId])));
            }
        }
    }
}