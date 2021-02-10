package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.UserAccount;

public class UserAccountServiceStub implements IUserAccountService {
    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return null;
    }

    @Override
    public UserAccount fetchUserAccount(String username) {
        return null;
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        return false;
    }

    @Override
    public String updateUserToken(UserAccount userAccount) {
        return null;
    }
}
