package test.poker;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import poker.kata.Card;

public class CardTests {


    @Test
    public void parseAceOfSpades() {
        Card c = new Card("As");
        assertThat(c.getRank(), is(equalTo(Card.ACE)));
        assertThat(c.getSuit(), is(equalTo(Card.SPADES)));
    }

}