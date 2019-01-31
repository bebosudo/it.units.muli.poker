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
    public void parseKingOfClubs() {
        Card c = new Card("Kc");
        assertThat(c.getRank(), is(equalTo(CardRank.KING)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.CLUBS)));
    }

    @Test
    public void parseQueenOfHearts() {
        Card c = new Card("Qh");
        assertThat(c.getRank(), is(equalTo(CardRank.QUEEN)));
        assertThat(c.getSuit(), is(equalTo(CardSuit.HEARTS)));
    }

}