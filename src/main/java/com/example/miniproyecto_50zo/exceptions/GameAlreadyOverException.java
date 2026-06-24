package com.example.miniproyecto_50zo.exceptions;

/**
 * Unchecked exception thrown when a game action is attempted
 * after the game has already ended in the Cincuentazo game.
 * Being unchecked, it signals a programming error rather than
 * an expected flow condition.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class GameAlreadyOverException extends RuntimeException {

    /**
     * Constructs a GameAlreadyOverException with the given detail message.
     *
     * @param message a description of the invalid post-game action attempted
     */
    public GameAlreadyOverException(String message) {
        super(message);
    }
}