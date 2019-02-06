package poker.kata;

public enum Rank {
    HIGH_CARD(1), PAIR(2), TWO_PAIRS(3), THREE_OF_A_KIND(4), STRAIGHT(5), FLUSH(6), FULL_HOUSE(7), FOUR_OF_A_KIND(8), STRAIGHT_FLUSH(9);

    private final int value;

    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}