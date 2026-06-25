package com.example.miniproyecto_50zo.model;

/**
 * Represents the rank of a playing card and its base game value
 * in the Cincuentazo card game.
 *
 * <ul>
 *   <li>Cards TWO through EIGHT and TEN add their face value to the table sum.</li>
 *   <li>NINE neither adds nor subtracts (value = 0).</li>
 *   <li>JACK, QUEEN, and KING subtract 10 from the table sum.</li>
 *   <li>ACE adds 1 or 10 depending on context, resolved in {@link Card#getValue(int)}.</li>
 * </ul>
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public enum Rank {

    /** Numeric card with base value 2. */
    TWO(2),

    /** Numeric card with base value 3. */
    THREE(3),

    /** Numeric card with base value 4. */
    FOUR(4),

    /** Numeric card with base value 5. */
    FIVE(5),

    /** Numeric card with base value 6. */
    SIX(6),

    /** Numeric card with base value 7. */
    SEVEN(7),

    /** Numeric card with base value 8. */
    EIGHT(8),

    /** Neutral card that neither adds nor subtracts (value = 0). */
    NINE(0),

    /** Numeric card with base value 10. */
    TEN(10),

    /** Face card that subtracts 10 from the table sum. */
    JACK(-10),

    /** Face card that subtracts 10 from the table sum. */
    QUEEN(-10),

    /** Face card that subtracts 10 from the table sum. */
    KING(-10),

    /**
     * Flexible card with a base value of 1.
     * Its effective value (1 or 10) is resolved in {@link Card#getValue(int)}.
     */
    ACE(1);

    /** The fixed integer value of this rank in the game. */
    private final int baseValue;

    /**
     * Constructs a Rank with the given base game value.
     *
     * @param baseValue the fixed integer value of this rank in the game
     */
    Rank(int baseValue) {
        this.baseValue = baseValue;
    }

    /**
     * Returns the base game value of this rank.
     * For {@link #ACE}, this always returns 1;
     * use {@link Card#getValue(int)} for context-aware resolution.
     *
     * @return the base integer value of this rank
     */
    public int getBaseValue() {
        return baseValue;
    }
}