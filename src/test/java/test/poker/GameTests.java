package test.poker;

import org.junit.Test;
import poker.kata.Game;
import poker.kata.Rank;

import java.net.URL;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameTests {

    @Test
    public void ParsingTest() {

        String[] s = {
                "Kc 9s Ks Kd 9d 3c 6d\n",
                "9c Ah Ks Kd 9d 3c 6d\n",
                "Ac Qc Ks Kd 9d 3c\n",
                "9h 5s\n",
                "4d 2d Ks Kd 9d 3c 6d\n",
                "7s Ts Ks Kd 9d\n"
        };

        Game g = new Game(s);

        Rank[] expRanks = {Rank.FULL_HOUSE, Rank.TWO_PAIRS, Rank.FOLD, Rank.FOLD, Rank.FLUSH, Rank.FOLD};
        ArrayList<Rank> ranks = g.getRanks();
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(ranks.get(i), is(equalTo(expRanks[i])));
        }
    }

    @Test
    public void ParsingTest1() {
        String[] s = {
                "9c 5c 6c 7c 3c Kh 3s\n",
                "5d 4s 6c 7c 3c Kh 3s\n",
                "3h 3d 6c 7c 3c Kh 3s\n"
        };

        Game g = new Game(s);

        Rank[] expRanks = {Rank.FLUSH, Rank.STRAIGHT, Rank.FOUR_OF_A_KIND};
        ArrayList<Rank> ranks = g.getRanks();
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(ranks.get(i), is(equalTo(expRanks[i])));
        }
    }

    @Test
    public void ParsingTest2() {
        String[] s = {
                "Kc 2d 4s As Kh 5c 3c\n",
                "5c 5d 4s As Kh 5c 3c\n",
                "Kh Jh 2c 3d 9c Tc Qd\n",
                "Ac Js 2c 3d 9c Tc Qd\n",
                "4s 6s Td 8s 9h Kd 9s\n",
                "Qs Jd Td 8s 9h Kd 9s\n"
        };

        Game g = new Game(s);

        Rank[] expRanks = {Rank.STRAIGHT, Rank.THREE_OF_A_KIND, Rank.STRAIGHT, Rank.HIGH_CARD, Rank.PAIR, Rank.STRAIGHT};
        ArrayList<Rank> ranks = g.getRanks();
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(ranks.get(i), is(equalTo(expRanks[i])));
        }
    }

    @Test
    public void ParsingTestFile1() {
        // throws Exception

        String filename = GameTests.class.getClassLoader().getResource("testGame1").getFile();

        Game g = new Game(filename);

        Rank[] expRanks = {Rank.PAIR, Rank.PAIR, Rank.PAIR, Rank.FOLD, Rank.FOLD, Rank.FLUSH};
        ArrayList<Rank> ranks = g.getRanks();
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(ranks.get(i), is(equalTo(expRanks[i])));
        }
    }



}
