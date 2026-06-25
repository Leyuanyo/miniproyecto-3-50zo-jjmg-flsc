package com.example.miniproyecto_50zo.exceptions;

/**
 * Checked exception thrown when a player attempts to play a card
 * that would cause the table sum to exceed 50 in the Cincuentazo game.
 * Being a checked exception, callers are required to handle it explicitly.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class InvalidCardPlayException extends Exception {

    /**
     * Constructs an InvalidCardPlayException with the given detail message.
     *
     * @param message a description of why the card play was invalid
     */
    public InvalidCardPlayException(String message) {
        super(message);
    }
}