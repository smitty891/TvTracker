package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.UserAccount;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserAccountDAO extends BaseDAO implements IUserAccountDAO {

    public UserAccountDAO() {
        super.setTableName("UserAccount");
    }

    /**
     * Method for creating a new UserAccount record in the database
     *
     * @param userAccount UserAccount object to be saved as a record in the database
     * @return boolean indicating a successful save
     * @throws Exception when database fails to save UserAccount
     */
    @Override
    public boolean save(UserAccount userAccount) {
        setInsertValue("username", userAccount.getUsername());
        setInsertValue("password", userAccount.getPassword());
        setInsertValue("email", userAccount.getEmail());
        setInsertValue("birthDate", userAccount.getBirthDate());
        setInsertValue("token", userAccount.getToken());
        setInsertValue("lastLogin", userAccount.getLastLogin());
        return insert();
    }

    /**
     * Method for fetching a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return UserAccount representation of the corresponding UserAccount database record
     */
    @Override
    public UserAccount fetch(String username) {
        addWhere("username", username);
        List<UserAccount> users = parse(select());

        if(users.isEmpty())
            return null;

        return users.get(0);
    }

    /**
     * Method for checking whether a record exists for the given username
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating whether a record exists for this username
     */
    @Override
    public boolean existsBy(String username) {
        addWhere("username", username);
        return !select().isEmpty();
    }

    /**
     * Method for deleting a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating a successful delete
     */
    @Override
    public boolean delete(String username) {
        addWhere("username", username);
        return delete();
    }

    /**
     * Method for updating an existing UserAccount record in the database
     *
     * @param userAccount UserAccount object to be used for updating a database record
     * @return boolean indicating a successful update
     */
    @Override
    public boolean update(UserAccount userAccount) {
        updateColumn("token", userAccount.getToken());
        updateColumn("lastLogin", userAccount.getLastLogin());
        addWhere("username", userAccount.getUsername());
        return update();
    }

    /**
     * Method for parsing SQL results into List of UserAccount objects
     *
     * @param results data structure representation of sql results
     * @return List of UserAccount objects
     */
    private List<UserAccount> parse(ArrayList<HashMap<String, Object>> results) {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        for (HashMap valuesMap: results) {
            UserAccount userAccount = new UserAccount();
            userAccount.setUsername((String) valuesMap.get("username"));
            userAccount.setPassword((String) valuesMap.get("password"));
            userAccount.setEmail((String) valuesMap.get("email"));
            userAccount.setBirthDate((Timestamp) valuesMap.get("birthdate"));
            userAccount.setLastLogin((Timestamp) valuesMap.get("lastLogin"));
            userAccount.setToken((String) valuesMap.get("token"));
            userAccounts.add(userAccount);
        }
        return userAccounts;
    }
}
