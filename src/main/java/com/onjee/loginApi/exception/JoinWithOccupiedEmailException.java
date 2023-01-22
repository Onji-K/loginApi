package com.onjee.loginApi.exception;

public class JoinWithOccupiedEmailException extends Exception{
    public JoinWithOccupiedEmailException() {
        super("Client sent JoinRequest with OccupiedEmail");
    }

    public JoinWithOccupiedEmailException(String message) {
        super(message);
    }
}
