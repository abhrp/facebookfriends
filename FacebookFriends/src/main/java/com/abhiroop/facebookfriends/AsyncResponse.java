package com.abhiroop.facebookfriends;

/**
 * Created by abhiroop on 10/2/13.
 */
public class AsyncResponse<T> {
    private T asyncResponseObject;
    private ServerError serverError;

    public AsyncResponse(T asyncResponseObject, ServerError serverError){

        this.asyncResponseObject = asyncResponseObject;
        this.serverError = serverError;
    }

    public T getAsyncResponseObject() {
        return asyncResponseObject;
    }

    public ServerError getServerError() {
        return serverError;
    }
}