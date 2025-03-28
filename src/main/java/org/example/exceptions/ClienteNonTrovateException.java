package org.example.exceptions;

public class ClienteNonTrovateException extends RuntimeException {
    public ClienteNonTrovateException(String message) {
        super(message);
    }
}
