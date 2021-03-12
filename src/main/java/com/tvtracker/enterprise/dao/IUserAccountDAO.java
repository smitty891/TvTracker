package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.UserAccount;

/**
 * Data Access Object for UserAccounts
 * <p>
 *     This class allows access to UserAccount records in our underlying database.
 * </p>
 */
public interface IUserAccountDAO {
    /**
     * Method for creating a new UserAccount record in the database
     *
     * @param userAccount UserAccount object to be saved as a record in the database
     * @return boolean indicating a successful save
     */
    boolean save(UserAccount userAccount);


    /**
     * Method for fetching a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return UserAccount representation of the corresponding UserAccount database record
     */
    UserAccount fetch(String username);


    /**
     * Method for checking whether a record exists for the given username
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating whether a record exists for this username
     */
    boolean existsBy(String username);

    /**
     * Method for deleting a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating a successful update
     */
    boolean delete(String username);


    /**
     * Method for updating an existing UserAccount record in the database
     *
     * @param userAccount UserAccount object to be used for updating a database record
     * @return boolean indicating a successful update
     */
    boolean update(UserAccount userAccount);
}
