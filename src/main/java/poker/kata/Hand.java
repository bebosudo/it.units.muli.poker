package poker.kata;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import poker.kata.Card;



public class Hand{

    private String original;
    private ArrayList<Card> cards;
    private CardHands score;

    public Hand(String HandStr){

        original = HandStr;
        cards = new ArrayList<Card>();
        score = CardHands.HIGH_CARD;

        String reg = "([TtJjQqKkAa\\d][cdshCSDH])";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(HandStr);

        int i=0;
        while (mat.find()){
            cards.add(new Card(mat.group()));
            i++;
        }

    }

    public Card getCards(int i) { return cards.get(i); }

    public String getOriginal(){
        return original;
    }

    public CardHands getScore() { return score; }

    public int compare(Hand h){
        int r = 1;
        switch(score){
            case HIGH_CARD:
                r = compareHigh(h);
                break;
            case ONE_PAIR:
                r = comparePair(h);
                break;
            case TWO_PAIR:
                r = compareDouble(h);
                break;
            case SET:
                r = compareSet(h);
                break;
            case STRAIGHT:
                r = compareStraight(h);
                break;
            case FLUSH:
                r = compareFlush(h);
                break;
            case FULL:
                r = compareFull(h);
                break;
            case QUADS:
                r = compareQuad(h);
                break;
            case STRAIGHT_FLUSH:
                r = compareStraightFlush(h);
                break;
        }
        return r;
    }


    public void setScore(){
        if( this.isFlush() && this.isStraight() ) {
            score = CardHands.STRAIGHT_FLUSH;
            return;
        }
        if( this.isQuad() ){
            score = CardHands.QUADS;
            return;
        }
        if( this.isFull() ){
            score = CardHands.FULL;
            return;
        }
        if( this.isFlush() ){
            score = CardHands.FLUSH;
            return;
        }
        if( this.isStraight() ){
            score = CardHands.STRAIGHT;
            return;
        }
        if( this.isSet() ){
            score = CardHands.SET;
            return;
        }
        if( this.isDouble() ){
            score = CardHands.TWO_PAIR;
            return;
        }
        if( this.isPair() ){
            score = CardHands.ONE_PAIR;
            return;
        }

        score = CardHands.HIGH_CARD;
    }


    private void sortByRank(){
        Collections.sort(cards, Card.COMPARE_BY_RANK);
    }

    private void sortBySuit(){
        Collections.sort(cards, Card.COMPARE_BY_SUIT);
    }

    private boolean isPair(){
        boolean b = true;
        return b;
    }

    private boolean isDouble(){
        boolean b = true;
        return b;
    }

    // tris
    private boolean isSet(){
        boolean b = true;
        return b;
    }

    private boolean isStraight(){
        boolean b = true;
        return b;
    }

    private boolean isFlush(){
        boolean b = true;
        return b;
    }

    private boolean isFull(){
        boolean b = true;
        return b;
    }



    private boolean isQuad(){
        boolean b = true;
        return b;
    }



    private int compareHigh(Hand h){ return 1; }
    private int comparePair(Hand h){ return 1; }
    private int compareDouble(Hand h){ return 1; }
    private int compareSet(Hand h){ return 1; }
    private int compareStraight(Hand h){ return 1; }
    private int compareFlush(Hand h){ return 1; }
    private int compareFull(Hand h){ return 1; }
    private int compareQuad(Hand h){ return 1; }
    private int compareStraightFlush(Hand h){ return 1; }


    public void printCards(){
        for(int i=0; i<7; i++){
            System.out.println( String.valueOf(i+1) + ":  " + cards.get(i).getRank() + " of " + cards.get(i).getSuit());
        }
    }

    public static void main(String[] args) {
        Hand h = new Hand("Ad Kd Qs Jh 2c As 5d");
        h.printCards();
        System.out.println("\n");
        h.sortByRank();
        h.printCards();
        System.out.println("\n");
        h.sortBySuit();
        h.printCards();
    }
}
