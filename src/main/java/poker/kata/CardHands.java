package poker.kata;

public enum CardHands {
    HIGH_CARD(1), ONE_PAIR(2), TWO_PAIR(3), SET(4), STRAIGHT(5), FLUSH(6), FULL(7), QUADS(8), STRAIGHT_FLUSH(9);

    private final int value;

    CardHands(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
