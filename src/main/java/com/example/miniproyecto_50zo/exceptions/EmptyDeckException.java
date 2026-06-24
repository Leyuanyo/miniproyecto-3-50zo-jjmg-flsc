package com.example.miniproyecto_50zo.exceptions;

/**
 * Checked exception thrown when the deck runs out of cards
 * and the table pile does not have enough cards to be recycled.
 * Being a checked exception, callers are required to handle it explicitly.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class EmptyDeckException extends Exception {

    /**
     * Constructs an EmptyDeckException with the given detail message.
     *
     * @param message a description of the empty deck condition
     */
    public EmptyDeckException(String message) {
        super(message);
    }
}