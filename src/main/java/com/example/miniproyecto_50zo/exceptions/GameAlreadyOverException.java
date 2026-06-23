package com.example.miniproyecto_50zo.exceptions;

public class GameAlreadyOverException extends RuntimeException {
    public GameAlreadyOverException(String message) {
        super(message);
    }
}