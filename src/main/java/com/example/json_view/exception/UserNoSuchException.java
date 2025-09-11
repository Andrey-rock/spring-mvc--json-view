package com.example.json_view.exception;

public class UserNoSuchException extends RuntimeException {
    public UserNoSuchException() {
        super("User not found");
    }
}
