package com.onjee.loginApi.exception;

public class JoinWithOccupiedUsernameException extends Exception{
    public JoinWithOccupiedUsernameException() {
        super("Client sent JoinRequest with OccupiedUsername");
    }

    public JoinWithOccupiedUsernameException(String message) {
        super(message);
    }
}
