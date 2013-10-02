package com.abhiroop.facebookfriends;

import java.io.Serializable;

/**
 * Created by abhiroop on 10/2/13.
 */
public class UserFriend implements Serializable{
   private String userId;
   private String name;

    public UserFriend(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
