package com.abhiroop.facebookfriends;

/**
 * Created by abhiroop on 10/2/13.
 */
public class ServerError {

    private final String message;
    private final int status;

    public ServerError(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}