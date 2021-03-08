/**
 * @author add author name here
 * @since   Add Date Here
 */

package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.UserAccount;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserAccountDAOStub implements IUserAccountDAO {
    HashMap<String, UserAccount> userAccounts = new HashMap<String, UserAccount>();

    /**
     * Method for creating a new UserAccount record in the database
     *
     * @param userAccount UserAccount object to be saved as a record in the database
     * @return UserAccount object representation of new database record
     * @throws Exception when database fails to save UserAccount
     */
    @Override
    public UserAccount save(UserAccount userAccount) throws Exception {
        if(userAccounts.containsKey(userAccount.getUsername())) {
            return null;
        }

        userAccounts.put(userAccount.getUsername(), userAccount);

        return userAccount;
    }

    /**
     * Method for fetching a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return UserAccount representation of the corresponding UserAccount database record
     */
    @Override
    public UserAccount fetch(String username) throws Exception {
        return userAccounts.get(username);
    }

    /**
     * Method for checking whether a record exists for the given username
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating whether a record exists for this username
     */
    @Override
    public boolean existsBy(String username) throws Exception {
        return userAccounts.containsKey(username);
    }

    /**
     * Method for deleting a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     */
    @Override
    public void delete(String username) throws Exception {
        userAccounts.remove(username);
    }

    /**
     * Method for updating an existing UserAccount record in the database
     *
     * @param userAccount UserAccount object to be used for updating a database record
     */
    @Override
    public void update(UserAccount userAccount) throws Exception {
        if(userAccounts.containsKey(userAccount.getUsername())){
            userAccounts.put(userAccount.getUsername(), userAccount);
        }
    }
}
