package poker.kata;


import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
            case PAIR:
                r = comparePair(h);
                break;
            case TWO_PAIRS:
                r = compareDouble(h);
                break;
            case THREE_OF_A_KIND:
                r = compareSet(h);
                break;
            case STRAIGHT:
                r = compareStraight(h);
                break;
            case FLUSH:
                r = compareFlush(h);
                break;
            case FULL_HOUSE:
                r = compareFull(h);
                break;
            case FOUR_OF_A_KIND:
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
            score = CardHands.FOUR_OF_A_KIND;
            return;
        }
        if( this.isFull() ){
            score = CardHands.FULL_HOUSE;
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
            score = CardHands.THREE_OF_A_KIND;
            return;
        }
        if( this.isDouble() ){
            score = CardHands.TWO_PAIRS;
            return;
        }
        if( this.isPair() ){
            score = CardHands.PAIR;
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
        boolean b = false;
        this.sortByRank();
        for( int i=0; i<6; i++){
            if( cards.get(i).getRank().equals( cards.get(i+1).getRank()) ) {
                b = true;
                break;
            }
        }
        return b;
    }

    private boolean isDouble(){
        boolean b = false;
        this.sortByRank();
        for(int i=0; i<6; i++){
            if( cards.get(i).getRank().equals( cards.get(i+1).getRank()) ){
                for( int j=i+2; j<6; j++ ){
                    if( cards.get(j).getRank().equals( cards.get(j+1).getRank())){
                        b = true;
                        break;
                    }
                }
            }
        }
        return b;
    }

    // tris
    private boolean isSet(){
        boolean b = false;
        this.sortByRank();
        for( int i=0; i<5; i++ ){
            if( cards.get(i).getRank().equals( cards.get(i+2).getRank()) ){
                b = true;
                break;
            }
        }
        return b;
    }

    private boolean isStraight(){
        boolean b = false;
        // sorting is not enough, if there is a pair 1, 2, 3, 3, 4, 5 it's not valid
        // so remove pairs (unique)
        // treat the ACE apart
        return b;
    }


    private boolean isFlush(){
        boolean b = false;
        this.sortBySuit();
        for( int i=0; i<3; i++){
            if( cards.get(i).getSuit().equals( cards.get(i+4).getSuit()) ){
                b = true;
                break;
            }
        }
        return b;
    }


    private boolean isFull(){
        boolean b = false;
        // after sorting the pair can be in front 22444 or after 44455
        // write a function that put the tris in front of it (useful also for compareFull() )
        return b;
    }



    private boolean isQuad(){
        boolean b = false;
        this.sortByRank();
        for( int i=0; i<4; i++ ){
            if( cards.get(i).getRank().equals( cards.get(i+3).getRank()) ){
                b = true;
                break;
            }
        }
        return b;
    }


    // -1 this is smaller, 0 equals, 1 the other is better
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
        Hand h = new Hand("Ad Kd Qs Th 2c As 5d");
        h.printCards();
        System.out.println("\n");
        h.sortByRank();
        h.printCards();
        System.out.println("\n");
        h.sortBySuit();
        h.printCards();
        System.out.println("\n");
        System.out.println("\n");
        System.out.println(h.isPair());
        h.setScore();
        System.out.println(h.getScore());
    }
}
