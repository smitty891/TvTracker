package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.UserAccount;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Implementations of IUserAccountService contain all the methods necessary for TvTracker's UserAccount functionality.
 */
public interface IUserAccountService {
    /**
     * Creates a new UserAccount database record from a UserAccount object.
     *
     * Returns null if an error occurs or if an account already exists with the given username.
     *
     * @param userAccount UserAccount object representing a user to be created
     * @return newly created UserAccount object
     */
    UserAccount createUserAccount(UserAccount userAccount) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Indicates whether a user with the given username already exists.
     *
     * @param userAccount UserAccount to compare against database records
     * @return boolean indicating whether a user with this username exists
     */
    boolean userAccountExists(UserAccount userAccount) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Retrieves a UserAccount object with the given username.
     *
     * Returns null if a user account with the given username could not be found.
     *
     * @param username String uniquely identifying a user
     * @return UserAccount object for the given username
     */
    UserAccount fetchUserAccount(String username) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Indicates whether a token is valid for a given UserAccount
     *
     * @param userAccount UserAccount object to compare token against
     * @param token String to validate for the given user
     * @return boolean indicating whether the token is valid for the given user
     */
    boolean isTokenValid(UserAccount userAccount, String token) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Updates the token and lastLogin for a UserAccount
     *
     * @param userAccount UserAccount object to create a new valid token for
     * @return UserAccount object containing a valid token
     */
    UserAccount updateUserToken(UserAccount userAccount) throws SQLException, IOException, ClassNotFoundException;
}
