package com.mygym.crm.backstages.interfaces.services.security;

public interface LoginAttemptService {

    void loginFailed(String userName);

    boolean isBlocked(String userName);

    public void loginSuccessful(String userName);
}
