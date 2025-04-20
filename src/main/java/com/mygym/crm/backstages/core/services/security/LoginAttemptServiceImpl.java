package com.mygym.crm.backstages.core.services.security;

import com.mygym.crm.backstages.interfaces.services.security.LoginAttemptService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private static final int MAX_ATTEMPT = 3; // Maximum allowed failed attempts
    private static final long LOCK_TIME_DURATION = 5 * 60 * 1000; // Locks the account for 5 minutes in milliseconds

    private final Map<String, FailedLoginInfo> attemptsCache = new ConcurrentHashMap<>();

    @Override
    public void loginFailed(String userName) {
        FailedLoginInfo info = attemptsCache.getOrDefault(userName, new FailedLoginInfo());
        info.incrementAttempts();
        info.setPreviousToLastFailedTime(info.lastFailedTime);
        info.setLastFailedTime(System.currentTimeMillis());
        attemptsCache.put(userName, info);

    }

    @Override
    public boolean isBlocked(String userName) {
        if (!attemptsCache.containsKey(userName)) return false;

        FailedLoginInfo info = attemptsCache.get(userName);

        // Check if the user has exceeded the max attempts threshold
        if (info.getAttempts() >= MAX_ATTEMPT) {
            System.out.println(info.getLastFailedTime());
            long lockTime = info.getPreviousToLastFailedTime() + LOCK_TIME_DURATION;
            if (lockTime > System.currentTimeMillis()) {
                return true; // User is still in lockout period
            } else {
                // Lock duration has expired, reset attempts
                attemptsCache.remove(userName);
            }
        }
        return false;
    }

    @Override
    public void loginSuccessful(String userName) {
        attemptsCache.remove(userName);
    }

    @Getter
    @Setter
    private static class FailedLoginInfo {
        private int attempts = 0;
        private long previousToLastFailedTime = 0;
        private long lastFailedTime;

        public void incrementAttempts() {
            this.attempts++;
        }


    }
}
