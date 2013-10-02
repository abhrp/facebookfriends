package com.abhiroop.facebookfriends;

/**
 * Created by abhiroop on 10/2/13.
 */
public interface ResponseHandler<T> {

    public void onResponse(ServerError error, T response);
}