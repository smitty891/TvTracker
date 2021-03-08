/**
 * @author add author name here
 * @since   Add Date Here
 */
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
     * @return UserAccount object representation of new database record
     * @throws Exception when database fails to save UserAccount
     */
    UserAccount save(UserAccount userAccount) throws Exception;


    /**
     * Method for fetching a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return UserAccount representation of the corresponding UserAccount database record
     */
    UserAccount fetch(String username) throws Exception;


    /**
     * Method for checking whether a record exists for the given username
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating whether a record exists for this username
     */
    boolean existsBy(String username) throws Exception;

    /**
     * Method for deleting a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     */
    void delete(String username) throws Exception;


    /**
     * Method for updating an existing UserAccount record in the database
     *
     * @param userAccount UserAccount object to be used for updating a database record
     */
    void update(UserAccount userAccount) throws Exception;
}
