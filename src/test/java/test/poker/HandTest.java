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
    public void TestPairKK(){
        Hand h = new Hand("Kc 9s Ks Ad 4d 3d 2s");
        assertThat(h.getScore(), is(equalTo(CardHands.PAIR)));
    }


    @Test
    public void TestPair33(){
        Hand h = new Hand("9s Ks 3d 8d 7s Ad 3s");
        assertThat(h.getScore(), is(equalTo(CardHands.PAIR)));
    }

    @Test
    public void TestPairAA(){
        Hand h = new Hand("3s 7d 8s Ac Ad Kd 6d");
        assertThat(h.getScore(), is(equalTo(CardHands.PAIR)));
    }

    

}
