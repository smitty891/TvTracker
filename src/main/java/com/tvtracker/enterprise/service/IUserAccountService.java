package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.UserAccount;

public interface IUserAccountService {
    UserAccount createUserAccount(UserAccount userAccount);
    UserAccount fetchUserAccount(String username);
    boolean isTokenValid(String token, UserAccount userAccount);
    String updateUserToken(UserAccount userAccount);
}
