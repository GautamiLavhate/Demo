package com.levare.verificare.activity;

import com.levare.verificare.model.User;

public class CurrentUser {

    private User user;

    private static CurrentUser mCurrentUser;

    private CurrentUser() {

    }

    public static CurrentUser getInstance() {
        if (mCurrentUser == null) {
            synchronized (CurrentUser.class) {
                mCurrentUser = new CurrentUser();
            }
        }
        return mCurrentUser;
    }

    public User getUser() {
        return user;
    }

    public void setCurrentUser(User mUser) {
        this.user = mUser;
    }
}
