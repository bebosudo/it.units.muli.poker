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

}