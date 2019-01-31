package poker.kata;

public enum CardHands {
    NOTHING(1), HIGH_CARD(2), ONE_PAIR(3), TWO_PAIR(4), SET(5), STRAIGHT(6), FLUSH(7), FULL(8), QUADS(9);

    private final int value;

    CardRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
