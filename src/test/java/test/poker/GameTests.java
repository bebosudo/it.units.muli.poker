package test.poker;

import org.junit.Test;
import poker.kata.Game;
import poker.kata.Rank;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameTests {

    private Game createSampleGameFromArrayOfStrings(String[] handsStrings) {
        ArrayList<String> s = new ArrayList<>(Arrays.asList(handsStrings));
        return new Game(s);
    }

    @Test
    public void ParsingTest() {

        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "9c 5c 6c 7c 3c Kh 3s\n",
                "5d 4s 6c 7c 3c Kh 3s\n",
                "3h 3d 6c 7c 3c Kh 3s\n"
        });

        Rank[] expRanks = {Rank.FLUSH, Rank.STRAIGHT, Rank.FOUR_OF_A_KIND};
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(g.getRank(i), is(equalTo(expRanks[i])));
        }
    }

    @Test
    public void ParsingTest1() {
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "9c 5c 6c 7c 3c Kh 3s\n",
                "5d 4s 6c 7c 3c Kh 3s\n",
                "3h 3d 6c 7c 3c Kh 3s\n"
        });

        Rank[] expRanks = {Rank.FLUSH, Rank.STRAIGHT, Rank.FOUR_OF_A_KIND};
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(g.getRank(i), is(equalTo(expRanks[i])));
        }
    }

    @Test
    public void ParsingTest2() {

        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "Kc 2d 4s As Kh 5c 3c\n",
                "5c 5d 4s As Kh 5c 3c\n",
                "Kh Jh 2c 3d 9c Tc Qd\n",
                "Ac Js 2c 3d 9c Tc Qd\n",
                "4s 6s Td 8s 9h Kd 9s\n",
                "Qs Jd Td 8s 9h Kd 9s\n"
        });


        Rank[] expRanks = {Rank.STRAIGHT, Rank.THREE_OF_A_KIND, Rank.STRAIGHT, Rank.HIGH_CARD, Rank.PAIR, Rank.STRAIGHT};
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(g.getRank(i), is(equalTo(expRanks[i])));
        }
    }

    @Test
    public void ParsingTestFile1() {
        // throws Exception

        URL filename = GameTests.class.getClassLoader().getResource("testGame1");

        Game g = new Game(filename);

        Rank[] expRanks = {Rank.PAIR, Rank.PAIR, Rank.PAIR, Rank.FOLD, Rank.FOLD, Rank.FLUSH};
        for (int i = 0; i < expRanks.length; i++) {
            assertThat(g.getRank(i), is(equalTo(expRanks[i])));
        }
    }

    @Test
    public void getWinnerFromTwoHandsWithDifferentScores() {
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "9c 5c 6c 7c 3c Kh 3s\n",
                "3h 3d 6c 7c 3c Kh 3s\n"
        });

        assertThat(g.getWinners()[0], is(equalTo(1)));

    }

    @Test
    public void getWinnerFromThreeHandsWithDifferentScores() {
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "6d Qs 6c 7c 3c Kh 3s\n",
                "9c 5c 6c 7c 3c Kh 3s\n",
                "3h 3d 6c 7c 3c Kh 3s\n"
        });

        assertThat(g.getWinners()[0], is(equalTo(2)));

    }

    @Test
    public void getWinnerFromThreeHandsTwoWithSameScore() {
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "2h 2d 6c 7c 3c Kh 3s\n",
                "6d Qs 6c 7c 3c Kh 3s\n",
                "As 5c 6c 7c 3c Kh 3s\n"
        });

        assertThat(g.getWinners()[0], is(equalTo(1)));
    }

    @Test
    public void getWinnerFromThreeHandsTwoWithSameRankDifferentKickers() {
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "6s 2d 6c 7c 3c Kh 3s\n",
                "6d Ah 6c 7c 3c Kh 3s\n",
                "As 5c 6c 7c 3c Kh 3s\n"
        });

        assertThat(g.getWinners()[0], is(equalTo(1)));
    }

    @Test
    public void getMultipleWinnersFromSameHand() {
        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "6d 2h 6h 2s 3d Qh 4s\n",
                "As 5c 6c 7c 3c Kh 3s\n",
                "6s 2d 6c 2c 3c Qh 4s\n",
        });

        Set<Integer> expWinners = Stream.of(0, 2).collect(Collectors.toSet());
        int[] winners = g.getWinners();
        for (int i = 0; i < winners.length; i++) {
            assertThat(expWinners.contains(winners[i]), is(true));
        }
    }

    @Test
    public void getMultipleWinnersFromSameHandDifferentAfterFiveCards() {
        // In texas hold'em, no more than 5 cards are examined, so if two (or more) players have the same hand up to 5
        // cards, they are both winners.

        Game g = createSampleGameFromArrayOfStrings(new String[]{
                "6d 2h 6h 2s 3d Qh 4s\n",
                "As 5c 6c 7c 3c Kh 3s\n",
                "6s 2d 6c 2c 3c Jh Ts\n",
        });

        Set<Integer> expWinners = Stream.of(0, 2).collect(Collectors.toSet());
        for (int winner : g.getWinners()) {
            assertThat(expWinners.contains(winner), is(true));
        }
    }



}
