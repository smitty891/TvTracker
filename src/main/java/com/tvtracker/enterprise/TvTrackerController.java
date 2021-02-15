package com.tvtracker.enterprise;

import com.tvtracker.enterprise.dto.MediaEntry;
import com.tvtracker.enterprise.dto.UserAccount;
import com.tvtracker.enterprise.service.IMediaEntryService;
import com.tvtracker.enterprise.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller for Tv Tracker REST endpoints and web UI.
 *
 * <p>
 *     This class handles the CRUD operations for Tv Tracker's user accounts and media entries, via HTTP actions.
 * </p>
 * <p>
 *     This class also serves HTML based web pages for UI interactions.
 * </p>
 */
@Controller
public class TvTrackerController {
    @Autowired
    IUserAccountService userAccountService;
    @Autowired
    IMediaEntryService mediaEntryService;

    /**
     * Create a new user account record from the user account data provided.
     *
     * Returns one of the following status codes:
     * 201: successfully created a user account.
     * 409: unable to create a user account, because username already exists in the database.
     *
     * @param userAccount a JSON representation of a UserAccount object
     * @return a valid user token for session authentication
     */
    @PostMapping(value="/signUp", consumes="application/json", produces="application/json")
    public ResponseEntity signUpUser(@RequestBody UserAccount userAccount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            UserAccount newUserAccount = userAccountService.createUserAccount(userAccount);
            String token = newUserAccount.getToken();
            return new ResponseEntity(token, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(headers, HttpStatus.CONFLICT);
        }
    }

    /**
     * Authenticate user and return newly generated session token
     *
     * Returns one of the following status codes:
     * 201: successfully authenticated user.
     * 401: invalid username password combination.
     *
     * @param username String uniquely identifying a user
     * @param password String that authenticates a user
     * @return a valid user token for session authentication
     */
    @GetMapping("/authenticate/{username}/{password}")
    public ResponseEntity authenticateUser(@PathVariable("username") String username, @PathVariable("password") String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            UserAccount userAccount = userAccountService.fetchUserAccount(username);

            if (userAccount != null && userAccount.getPassword().equals(password)) {
                String token = userAccountService.updateUserToken(userAccount);
                return new ResponseEntity(token, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns all media entries tied to the given username
     *
     * 201: successfully updated media entry.
     * 401: authentication token is invalid.
     *
     * @param username String uniquely identifying a user
     * @return List user's media entries
     */
    @GetMapping("/getMediaEntries/{username}/{token}")
    public ResponseEntity getUsersMediaEntries(@PathVariable("username") String username, @PathVariable("token") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // authenticate request
            if (!userAccountService.isTokenValid(token, username)) {
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            List<MediaEntry> mediaEntries = mediaEntryService.fetchMediaEntriesByUsername(username);
            return new ResponseEntity(mediaEntries, headers, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing media entry record
     *
     * Returns one of the following status codes:
     * 201: successfully updated media entry.
     * 400: failed to update media entry.
     * 401: authentication token is invalid.
     *
     * @param mediaEntry a JSON representation of a MediaEntry object
     * @return HttpStatus
     */
    @PutMapping(value="/editMediaEntry/{username}/{token}", consumes="application/json", produces="application/json")
    public ResponseEntity editMediaEntry(@RequestBody MediaEntry mediaEntry, @PathVariable("username") String username, @PathVariable("token") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // authenticate request
            if (!userAccountService.isTokenValid(token, username)) {
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            boolean success = mediaEntryService.updateMediaEntry(mediaEntry);

            if (success) {
                return new ResponseEntity(headers, HttpStatus.OK);
            } else {
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete media entry record
     *
     * Returns one of the following status codes:
     * 201: successfully deleted media entry.
     * 400: failed to delete media entry.
     * 401: authentication token is invalid.
     *
     * @param entryId integer uniquely identifying the media entry record
     * @return HttpStatus
     */
    @DeleteMapping("/removeMediaEntry/{entryId}/{username}/{token}")
    public ResponseEntity removeMediaEntry(@PathVariable("entryId") int entryId, @PathVariable("username") String username, @PathVariable("token") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // authenticate request
            if (!userAccountService.isTokenValid(token, username)) {
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            boolean success = mediaEntryService.deleteMediaEntry(entryId);

            if (success) {
                return new ResponseEntity(headers, HttpStatus.OK);
            } else {
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new media entry record
     *
     * Returns one of the following status codes:
     * 201: successfully created media entry.
     * 400: failed to create media entry.
     * 401: authentication token is invalid.
     *
     * @param mediaEntry a JSON representation of a MediaEntry object
     * @return HttpStatus
     */
    @PostMapping(value="/addMediaEntry/{username}/{token}", consumes="application/json", produces="application/json")
    public ResponseEntity addMediaEntry(@RequestBody MediaEntry mediaEntry, @PathVariable("username") String username, @PathVariable("token") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // authenticate request
            if (!userAccountService.isTokenValid(token, username)) {
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            boolean success = mediaEntryService.createMediaEntry(mediaEntry);

            if (success) {
                return new ResponseEntity(headers, HttpStatus.OK);
            } else {
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
