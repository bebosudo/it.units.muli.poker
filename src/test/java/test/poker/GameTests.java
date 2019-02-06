package test.poker;

import org.junit.Test;
import poker.kata.Game;
import poker.kata.Hand;
import poker.kata.Rank;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameTests {

    @Test
    public void ParsingTest() {

        String[] s = {"Kc 9s Ks Kd 9d 3c 6d\n",
                "9c Ah Ks Kd 9d 3c 6d\n",
                "Ac Qc Ks Kd 9d 3c\n",
                "9h 5s\n",
                "4d 2d Ks Kd 9d 3c 6d\n",
                "7s Ts Ks Kd 9d\n"};

        Game g = new Game(s);

        Rank[] expRanks = {Rank.FULL_HOUSE, Rank.TWO_PAIRS, Rank.FOLD, Rank.FOLD, Rank.FLUSH, Rank.FOLD};
        Rank[] ranks = g.getRanks().toArray(new Rank[0]);
        for (int i = 0; i < s.length; i++) {
            assertThat(ranks[i], is(equalTo(expRanks[i])));
        }

    }

}
