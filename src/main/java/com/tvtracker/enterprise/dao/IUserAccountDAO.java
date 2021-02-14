package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.UserAccount;

public interface IUserAccountDAO {
    UserAccount save(UserAccount userAccount) throws Exception;

    UserAccount fetch(String userAccount);

    Boolean existsBy(String username);

    void delete(String userAccount);
}
