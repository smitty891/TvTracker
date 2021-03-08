package com.tvtracker.enterprise.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author add author name here
 * @since   Add Date Here
 */
import java.sql.Timestamp;

@Getter
@Setter

public @Data
class UserAccount {
    /**
     * UserAccount's unique identifier
     */
    private String username;
    private String password;
    private String email;
    private Timestamp birthDate;
    /**
     * Random generated character string used for authentication
     */
    private String token;
    /**
     * <p>Holds the time of the user's last successful login.</p>
     * <p>Use this to check whether a token needs to be updated.</p>
     */
    private Timestamp lastLogin;
}
