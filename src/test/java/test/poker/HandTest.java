package test.poker;


import org.junit.Test;
import poker.kata.CardHands;
import poker.kata.CardRank;
import poker.kata.Hand;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class HandTest {

    @Test
    public void TestPairKK() {
        Hand h = new Hand("Kc 9s Ks Ad 4d 3d 2s");
        assertThat(h.getScore(), is(equalTo(CardHands.PAIR)));
    }


    @Test
    public void TestPair33() {
        Hand h = new Hand("9s Ks 3d 8d 7s Ad 3s");
        assertThat(h.getScore(), is(equalTo(CardHands.PAIR)));
    }

    @Test
    public void TestPairAA() {
        Hand h = new Hand("3s 7d 8s Ac Ad Kd 6d");
        assertThat(h.getScore(), is(equalTo(CardHands.PAIR)));
    }

    @Test
    public void TestStraightA5Ordered() {
        Hand h = new Hand("As 2d 3s 4h 5c Kd Qs");
        assertThat(h.getScore(), is(equalTo(CardHands.STRAIGHT)));
    }

    @Test
    public void TestStraightATo5Unordered() {
        Hand h = new Hand("3s Kd 4h As 5c Qs 2d");
        assertThat(h.getScore(), is(equalTo(CardHands.STRAIGHT)));
    }

    @Test
    public void TestStraightGeneralOrdered(){
        Hand h = new Hand("7d 8c 9c Ts Jd 3c Ac");
        assertThat(h.getScore(), is(equalTo(CardHands.STRAIGHT)));
    }

    @Test
    public void TestStraight10ToA(){
        Hand h = new Hand("Kd Ac 6c Td Jd 2c Qc");
        assertThat(h.getScore(), is(equalTo(CardHands.STRAIGHT)));
    }

    @Test
    public void TestStraight10ToANoPair(){
        Hand h = new Hand("Kd Ac 2c Td Jd 2c Qc");
        assertThat(h.getScore(), is(equalTo(CardHands.STRAIGHT)));
    }

    @Test
    public void TestStraight10ToANoSetCardsFromSameCardAsFinalCardOfStraight(){
        Hand h = new Hand("Kd Ac Ad Td Jd Ah Qc");
        assertThat(h.getScore(), is(equalTo(CardHands.STRAIGHT)));
    }
}
