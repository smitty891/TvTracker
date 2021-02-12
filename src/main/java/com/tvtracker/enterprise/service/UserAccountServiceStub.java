package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.UserAccount;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserAccountServiceStub implements IUserAccountService {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private List<UserAccount> userAccounts = new ArrayList<UserAccount>();

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        // return null if user name isn't unique;
        for(UserAccount account: userAccounts){
            if(account.getUsername().equals(userAccount.getUsername())){
                return null;
            }
        }

        String token = generateNewToken();
        userAccount.setToken(token);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        userAccount.setLastLogin(timestamp);

        userAccounts.add(userAccount);

        return userAccount;
    }

    @Override
    public UserAccount fetchUserAccount(String username) {
        if(username == null)
            return null;

        for(UserAccount account: userAccounts){
            if(account.getUsername().equals(username)){
                return account;
            }
        }

        return null;
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        UserAccount userAccount = fetchUserAccount(username);

        if(userAccount == null || userAccount.getToken() == null || userAccount.getLastLogin() == null)
            return false;

        if(!userAccount.getToken().equals(token))
            return false;

        // Verify user's token was created within the last hour
        Instant lastLogin = userAccount.getLastLogin().toInstant();
        Duration hour = Duration.ofHours( 1 );
        Instant timeCutOff = lastLogin.plus(hour);

        return Instant.now().isBefore(timeCutOff);
    }

    @Override
    public String updateUserToken(UserAccount userAccount) {
        if(userAccount == null)
            return null;

        String token = generateNewToken();
        userAccount.setToken(token);
        userAccount.setLastLogin(new Timestamp(System.currentTimeMillis()));

        return token;
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
