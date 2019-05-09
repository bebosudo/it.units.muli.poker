package test.poker;


import org.junit.Test;
import poker.kata.Rank;
import poker.kata.Hand;
import poker.kata.Card;


import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.assertThat;


public class HandTest {

    @Test
    public void TestHighCard() {
        Hand h = new Hand("Kc 9s Qs Ad 4d 3d 2s");
        assertThat(h.getScore(), is(equalTo(Rank.HIGH_CARD)));

        h = new Hand("5c 9s Ks Ad 4d 3d Qs");
        assertThat(h.getScore(), is(equalTo(Rank.HIGH_CARD)));

        h = new Hand("6c 9s 5s Ac 4d 7d 2s");
        assertThat(h.getScore(), is(equalTo(Rank.HIGH_CARD)));

        h = new Hand("7c 9s 8s Ad 4d 3d 2s");
        assertThat(h.getScore(), is(equalTo(Rank.HIGH_CARD)));
    }

    @Test
    public void TestPairKK() {
        Hand h = new Hand("Kc 9s Ks Ad 4d 3d 2s");
        assertThat(h.getScore(), is(equalTo(Rank.PAIR)));
    }

    @Test
    public void TestPair33() {
        Hand h = new Hand("9s Ks 3d 8d 7s Ad 3s");
        assertThat(h.getScore(), is(equalTo(Rank.PAIR)));
    }


    @Test
    public void TestPairAA() {
        Hand h = new Hand("3s 7d 8s Ac Ad Kd 6d");
        assertThat(h.getScore(), is(equalTo(Rank.PAIR)));
    }

    @Test
    public void TestDoubleKK99() {
        Hand h = new Hand("Kc 9s Ks 9d 5s 5d 6d");
        assertThat(h.getScore(), is(equalTo(Rank.TWO_PAIRS)));
    }

    @Test
    public void TestDouble5533() {
        Hand h = new Hand("3d 4h 5h 3s Ad 5s 6h");
        assertThat(h.getScore(), is(equalTo(Rank.TWO_PAIRS)));
    }

    @Test
    public void TestDoubleTT99() {
        Hand h = new Hand("Th 9d Td 8c 9h 3d Js");
        assertThat(h.getScore(), is(equalTo(Rank.TWO_PAIRS)));
    }

    @Test
    public void TestSet444() {
        Hand h = new Hand("3d 4h 4s Ad Kd 9c 4c");
        assertThat(h.getScore(), is(equalTo(Rank.THREE_OF_A_KIND)));
    }

    @Test
    public void TestSetQQQ() {
        Hand h = new Hand("Qh Ks Ad Qs 9c Qc 4c");
        assertThat(h.getScore(), is(equalTo(Rank.THREE_OF_A_KIND)));
    }

    @Test
    public void TestSet888() {
        Hand h = new Hand("8d 5d 8c Th 8s 4h Kc");
        assertThat(h.getScore(), is(equalTo(Rank.THREE_OF_A_KIND)));
    }


    @Test
    public void TestFull() {
        Hand h = new Hand("Kd 2d 3h Kh 2s 7c Ks");
        assertThat(h.getScore(), is(equalTo(Rank.FULL_HOUSE)));

        h = new Hand("Ad As 3s Ah Ks 7c Kc");
        assertThat(h.getScore(), is(equalTo(Rank.FULL_HOUSE)));

        h = new Hand("7d 2d 7h 8h 3h 7c 2c");
        assertThat(h.getScore(), is(equalTo(Rank.FULL_HOUSE)));

        h = new Hand("Kc 9s Ks Kd 9d 3c 6d");
        assertThat(h.getScore(), is(equalTo(Rank.FULL_HOUSE)));
    }

    @Test
    public void TestStraightA5Ordered() {
        Hand h = new Hand("As 2d 3s 4h 5c Kd Qs");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT)));
    }

    @Test
    public void TestStraightATo5Unordered() {
        Hand h = new Hand("3s Kd 4h As 5c Qs 2d");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT)));
    }

    @Test
    public void TestStraightGeneralOrdered() {
        Hand h = new Hand("7d 8c 9c Ts Jd 3c Ac");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT)));
    }

    @Test
    public void TestStraight10ToA() {
        Hand h = new Hand("Kd Ac 6c Td Jd 2c Qc");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT)));
    }

    @Test
    public void TestStraight10ToANoPair() {
        Hand h = new Hand("Kd Ac 2c Td Jd 2c Qc");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT)));
    }

    @Test
    public void TestStraight10ToANoSetCardsFromSameCardAsFinalCardOfStraight() {
        Hand h = new Hand("Kd Ac Ad Td Jd Ah Qc");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT)));
    }


    @Test
    public void TestQuad() {
        Hand h = new Hand("Ad As 3s Td 4d Ah Ac");
        assertThat(h.getScore(), is(equalTo(Rank.FOUR_OF_A_KIND)));

        h = new Hand("5d 7h 5c 5h Td Th 5s");
        assertThat(h.getScore(), is(equalTo(Rank.FOUR_OF_A_KIND)));

        h = new Hand("Td 3h Ts 7s 4d Th Tc");
        assertThat(h.getScore(), is(equalTo(Rank.FOUR_OF_A_KIND)));

        h = new Hand("3h 3d 6c 7c 3c Kh 3s");
        assertThat(h.getScore(), is(equalTo(Rank.FOUR_OF_A_KIND)));

    }


    @Test
    public void TestStraightFlush() {
        Hand h = new Hand("Ad Kd Td 3h Jd 2h Qd");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT_FLUSH)));

        h = new Hand("Ah Th 3h 4h 7d 2h 5h");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT_FLUSH)));

        h = new Hand("Qs Js 3d Ts 9s 2h 8s");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT_FLUSH)));

        h = new Hand("9c Td 8c 5c 3h 6c 7c");
        assertThat(h.getScore(), is(equalTo(Rank.STRAIGHT_FLUSH)));
    }


    @Test
    public void TestFlushSpades() {
        Hand h = new Hand("Ks As 2s Ts Js 3c Qh");
        assertThat(h.getScore(), is(equalTo(Rank.FLUSH)));
    }

    @Test
    public void TestFlushSpadesInverse() {
        Hand h = new Hand("3c Qh Ks As 2s Ts Js");
        assertThat(h.getScore(), is(equalTo(Rank.FLUSH)));
    }

    @Test
    public void TestFlushSpadesUnsorted() {
        Hand h = new Hand("Ks 3c As 2s Qh Ts Js");
        assertThat(h.getScore(), is(equalTo(Rank.FLUSH)));
    }

    @Test
    public void TestFlushClubs() {
        Hand h = new Hand("9c 6c 7c 3c Kh 3s 5c");
        assertThat(h.getScore(), is(equalTo(Rank.FLUSH)));
    }

    @Test
    public void TestFlushClubs2() {
        Hand h = new Hand("9c 5c 6c 7c 3c Kh 3s\n"
        );
        assertThat(h.getScore(), is(equalTo(Rank.FLUSH)));
    }

    @Test
    public void TestStraightOrdering() {
        Hand h = new Hand("9c 6c 7c 4s Kh 3s 5d");
        Card[] orderedCards = {new Card("7c"), new Card("6c"), new Card("5d"), new Card("4s"), new Card("3s"), new Card("Kh"), new Card("9c")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(true));
    }

    @Test
    public void TestPairOrdering() {
        Hand h = new Hand("9c 6c 7c 4s Kh 3s 7d");
        Card[] orderedCards = {new Card("7c"), new Card("7d"), new Card("Kh"), new Card("9c"), new Card("6c"), new Card("4s"), new Card("3s")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(true));
    }

    @Test
    public void TestDoubleOrdering() {
        Hand h = new Hand("3c 6c 7c 4s Kh 3s 7d");
        Card[] orderedCards = {new Card("7c"), new Card("7d"), new Card("3c"), new Card("3s"), new Card("Kh"), new Card("6c"), new Card("4s")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(true));
    }

    @Test
    public void TestSetOrdering() {
        Hand h = new Hand("Qh 6c 7c Qs Kh 3s Qd");
        Card[] orderedCards = {new Card("Qh"), new Card("Qs"), new Card("Qd"), new Card("Kh"), new Card("7c"), new Card("6c"), new Card("3s")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(true));
    }

    @Test
    public void TestFullOrdering() {
        Hand h = new Hand("Qh Kc 7c Qs Kh 3s Qd");
        Card[] orderedCards = {new Card("Qh"), new Card("Qs"), new Card("Qd"), new Card("Kh"), new Card("Kc"), new Card("7c"), new Card("3s")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(true));
    }

    @Test
    public void TestQuadOrdering() {
        Hand h = new Hand("Qh Kc 7c Qs Kh Qc Qd");
        Card[] orderedCards = {new Card("Qh"), new Card("Qs"), new Card("Qd"), new Card("Qc"), new Card("Kc"), new Card("Kh"), new Card("7c")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(true));
    }

    @Test
    public void TestWrongOrderingForQuad() {
        Hand h = new Hand("Qh Kc 7c Qs Kh Qc Qd");
        Card[] orderedCards = {new Card("7c"), new Card("Qs"), new Card("Qd"), new Card("Qc"), new Card("Kc"), new Card("Kh"), new Card("Qh")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(false));
    }

    @Test
    public void TestStraightOrderingWithRepeatingCards() {
        // this test is to make sure only the first 5 cards are compared
        Hand h = new Hand("6h 6c 7c 4s Kh 3s 5d");
        Card[] orderedCards = {new Card("7c"), new Card("6h"), new Card("5d"), new Card("4s"), new Card("3s"), new Card("Kh"), new Card("6c")};
        ArrayList<Card> orderedCardsList = new ArrayList(Arrays.asList(orderedCards));

        assertThat(h.compareToCardsArray(orderedCardsList), is(true));
    }


    @Test
    public void TestLessThanSevenCardsGetFoldRank() {
        Hand h = new Hand("Qh Kc 7c Qs Qc Qd");

        assertThat(h.getScore(), is(Rank.FOLD));

        h = new Hand("Qh Kc");
        assertThat(h.getScore(), is(Rank.FOLD));

    }

    @Test
    public void compareSimpleHandsFirstGreater() {
        Hand h1 = new Hand("Kc 9s Ks 9d 5s 5d 6d");
        Hand h2 = new Hand("Kd Td 2s 3h 6h 8d 5h");
        //assertThat(h1.compareTo(h2), is(equalTo(1)));
        assertThat(h1.compareTo(h2) > 0, is(equalTo(true)));
    }

    @Test
    public void compareSimpleHandsSecondGreater() {
        Hand h1 = new Hand("Kd Td 2s 3h 6h 8d 5h");
        Hand h2 = new Hand("Kc 9s Ks 9d 5s 5d 6d");
        assertThat(h1.compareTo(h2) > 0, is(equalTo(false)));
    }

    @Test
    public void compareTwoHandsSameScoreDifferentKicker() {
        Hand h1 = new Hand("Kc 8s Ks 9d 4s 5d 6d");
        Hand h2 = new Hand("Kd Kh 2s 3h 6h 8d 5h");
        assertThat(h1.compareTo(h2) > 0, is(equalTo(true)));
    }

    @Test
    public void compareTwoHandsHighCardsOnly() {
        Hand h1 = new Hand("Kc 8s As 9d 4s 5d 6d");
        Hand h2 = new Hand("Kd Th 2s 3h 6h 8d 5h");
        assertThat(h1.compareTo(h2) > 0, is(equalTo(true)));
    }

    @Test
    public void compareTwoHandsSameScoreDifferentSecondKicker() {
        Hand h1 = new Hand("Kc 2s Ks 9d 4s 5d 6d");
        Hand h2 = new Hand("Kd Kh 2d 3h 8h 9s 5h");
        assertThat(h2.compareTo(h1) > 0, is(equalTo(true)));
    }

    @Test
    public void compareCompleteDrawOnStraight() {
        Hand h1 = new Hand("2c 2s 3s 9d 4s 5d 6d");
        Hand h2 = new Hand("2d 6h Kd 3d 4h Ah 5h");
        assertThat(h2.compareTo(h1), is(equalTo(0)));
    }

    @Test
    public void compareCompleteDrawOnStraightAceToTen() {
        Hand h1 = new Hand("Ac Ks Ts Qd 4s Jd 6d");
        Hand h2 = new Hand("Jd 6h Kd Td 4h Ah Qh");
        assertThat(h2.compareTo(h1), is(equalTo(0)));
    }

    @Test
    public void compareOnStraightAceNoDraw() {
        Hand h1 = new Hand("Ac Ks Ts Qd 4s Jd 6d");
        Hand h2 = new Hand("Jd 6h Kd Td 4h 9h Qh");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnFullNoSameScore() {
        Hand h1 = new Hand("Ah Td Ac Ad Th Qc Js");
        Hand h2 = new Hand("Ts Tc Ac 3d Th Qc Js");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnFullSameScore() {
        Hand h1 = new Hand("Ah Td Ac Ad Th Qc Js");
        Hand h2 = new Hand("Ts Tc Ac Ad Th Qc Js");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnTwoPairs() {
        Hand h1 = new Hand("9c Ah Ks Kd 9d 3c 6d");
        Hand h2 = new Hand("Ts Tc 3c Ad 6h Qc Js");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnTwoPairsSameScore() {
        Hand h1 = new Hand("9c Ah Ks Kd Td Tc 6d");
        Hand h2 = new Hand("Ts Tc 9s 3d Ks Kc 2s");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnTwoPairsCompleteDraw() {
        Hand h1 = new Hand("9c Ah Ks Kd Td Tc 6d");
        Hand h2 = new Hand("Ts Tc 9s Ad Ks Kc 2s");
        assertThat(h1.compareTo(h2), is(equalTo(0)));
    }

    @Test
    public void compareOnThreeOfAKind() {
        Hand h1 = new Hand("Kc Ah Ks Kd 9d 3c 6d");
        Hand h2 = new Hand("Ts Tc 3c Ad Ah Qc Js");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnThreeOfAKindSameScore() {
        Hand h1 = new Hand("3d 4h 4s Ad Kd 9c 4c");
        Hand h2 = new Hand("3d 4h 4d As Qs 9c 4c");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnThreeOfAKindCompleteDraw() {
        Hand h1 = new Hand("3d 4h 4s Ad Kd 7c 4c");
        Hand h2 = new Hand("6d 4h 4d As Ks 9c 4c");
        assertThat(h1.compareTo(h2), is(equalTo(0)));
    }

    @Test
    public void compareOnFlush() {
        Hand h1 = new Hand("2h Ah Td 3h Th 9h 7d");
        Hand h2 = new Hand("As 2s 4d Qc Ah 3c 5h");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnFlushSameScore() {
        Hand h1 = new Hand("2h Ah Td 3h Th 9h 7d");
        Hand h2 = new Hand("Ts 3s 3d 4h 8s As 2s");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnFlushCompleteDraw() {
        Hand h1 = new Hand("2h Ah Td 3h Th 9h 7d");
        Hand h2 = new Hand("Ts 3s 3d 4h 9s As 2s");
        assertThat(h1.compareTo(h2), is(equalTo(0)));
    }

    @Test
    public void compareOnStraightFlush() {
        Hand h1 = new Hand("Ad Kd Td 3h Jd 2h Qd");
        Hand h2 = new Hand("Ts 3s 3d 4h 9s As 2s");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnStraightFlushSameScoreOneAceTo10OtherFiveToAce() {
        Hand h1 = new Hand("Ad Kd Td 3h Jd 2h Qd");
        Hand h2 = new Hand("Ah Th 3h 4h 7d 2h 5h");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void compareOnStraightFlushCompleteDraw() {
        Hand h1 = new Hand("Ad Kd Td 3h Jd 2h Qd");
        Hand h2 = new Hand("As Ks Ts 3h Js 2h Qs");
        assertThat(h1.compareTo(h2), is(equalTo(0)));
    }

    @Test
    public void compareThreeOfAKindWithFoldedHand() {
        Hand h1 = new Hand("Ad Ah As 3h Jd 2h Qd");
        Hand h2 = new Hand("Ah Th Kh Ad Ac");
        assertThat((h1.compareTo(h2)) > 0, is(equalTo(true)));
    }

    @Test
    public void scoreStringFold() {
        Hand h = new Hand("Ah Th Kh Ad Ac");
        assertThat(h.scoreString(), is(equalTo("")));
    }

    @Test
    public void scoreStringPair() {
        Hand h = new Hand("Ah Th Kh 2d Ac 3s 8d");
        assertThat(h.scoreString(), is(equalTo("Pair")));
    }

    @Test
    public void printFullHouse() {
        Hand h = new Hand("Kc 9s Ks Kd 9d 3c 6d");
        assertThat(h.scoreString(), is(equalTo("Full House")));
    }

    @Test
    public void testSortByFaceDecr() {
        Hand h = new Hand("Ac 9s As Kd Ad 3c 6d");
        h.sortByFaceDecreasing();
        assertThat(h.getCard(0), is(equalTo(new Card("Ac"))));
        assertThat(h.getCard(1), is(equalTo(new Card("As"))));
        assertThat(h.getCard(2), is(equalTo(new Card("Ad"))));
        assertThat(h.getCard(3), is(equalTo(new Card("Kd"))));
    }

    @Test
    public void testSortByFaceDecrWithAceEqual1() {
        Hand h = new Hand("Ac 9s As Kd Ad 3c 6d");
        h.sortByFaceDecreasingAce1();
        assertThat(h.getCard(0), is(equalTo(new Card("Kd"))));
        assertThat(h.getCard(4), is(equalTo(new Card("Ac"))));
        assertThat(h.getCard(5), is(equalTo(new Card("As"))));
        assertThat(h.getCard(6), is(equalTo(new Card("Ad"))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandSizeTooLarge() {
        new Hand("Kc 9s Ks Kd 9d 3c 6d 6c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyHand() {
        new Hand("");
    }


}
