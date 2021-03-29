package com.tvtracker.enterprise;

import com.tvtracker.enterprise.dto.MediaEntry;
import com.tvtracker.enterprise.dto.UserAccount;
import com.tvtracker.enterprise.service.IMediaEntryService;
import com.tvtracker.enterprise.service.IUserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Handle the / endpoint
     * @return start.html
     */
    @RequestMapping("/")
    public String index(){
        return "start";
    }

    @RequestMapping("/browse")
    public String browse(){
        return "browse";
    }

    @RequestMapping("/signup")
    public String signup(){
        return"signup";
    }

    /**
     * Handle the /favorites endpoint
     * @return favorites.html
     */
    @RequestMapping("/favorites")
    public String favorites(){
        return "favorites";
    }

    /**
     * Create a new user account record from the user account data provided.
     *
     * Returns one of the following status codes:
     * 201: successfully created a user account.
     * 409: unable to create a user account, because username already exists in the database.
     * 500: SQL Database error occurred.
     *
     * @param userAccount a JSON representation of a UserAccount object
     * @return a valid user token for session authentication
     */
    @PostMapping(value="/signUp", consumes="application/json", produces="application/json")
    public ResponseEntity signUpUser(@RequestBody UserAccount userAccount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token;

        try {
            if (userAccountService.userAccountExists(userAccount)) {
                log.debug("Error during signup.");
                return new ResponseEntity(headers, HttpStatus.CONFLICT);
            }

            token = userAccountService.createUserAccount(userAccount);

        } catch (Exception e) {
            log.debug("There was a problem with user signup. Message: " + e.getMessage(), e);
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (token == null) {
            log.debug("Null token encountered.");
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("User created successfully!");
        return new ResponseEntity(token, headers, HttpStatus.CREATED);
    }

    /**
     * Authenticate user and return newly generated session token
     *
     * Returns one of the following status codes:
     * 200: successfully authenticated user.
     * 401: invalid username password combination.
     * 500: SQL Database error occurred.
     *
     * @param username String uniquely identifying a user
     * @param password String that authenticates a user
     * @return a valid user token for session authentication
     */
    @GetMapping("/authenticate")
    public ResponseEntity authenticateUser(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", defaultValue="") String password, @RequestParam(value="token", defaultValue="") String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.debug("User Authentication endpoint.");

        if(password.isEmpty() && token.isEmpty()) {
            log.info("No credentials.");
            return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }

        try {
            if (!token.isEmpty()) {
                if (!userAccountService.isTokenValid(token, username)) {
                    log.info("Invalid token");
                    return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
                }
            } else {
                UserAccount userAccount = userAccountService.fetchUserAccount(username);

                if (userAccount != null && userAccount.getPassword().equals(password)) {
                    log.info("Token updated.");
                    token = userAccountService.updateUserToken(userAccount);

                    if (token == null) {
                        log.debug("Null token encountered.");
                        return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    log.debug("Unauthorized token.");
                    return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
                }
            }
        } catch (Exception e) {
            log.debug("There was an authentication problem encountered. Message: " + e.getMessage(), e);
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(token, headers, HttpStatus.OK);
    }

    /**
     * Returns all media entries tied to the given username
     *
     * 200: successfully returned media entry records.
     * 401: authentication token is invalid.
     * 500: SQL Database error occurred.
     *
     * @param username String uniquely identifying a user
     * @return List user's media entries
     */
    @GetMapping("/getMediaEntries")
    public ResponseEntity getUsersMediaEntries(@RequestParam(value="username", required=true) String username, @RequestParam(value="token", required=true) String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaEntry> mediaEntries;
        log.debug("Enterying retrieve user media entries endpoint.");

        try {
            // authenticate request
            if (!userAccountService.isTokenValid(token, username)) {
                log.info("Unauthorized token");
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            mediaEntries = mediaEntryService.fetchMediaEntriesByUsername(username);

        } catch (Exception e) {
            log.error("There was a problem retrieving media entries. Message: " + e.getMessage(), e);
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (mediaEntries == null) {
            log.info("Null entry received.");
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(mediaEntries, headers, HttpStatus.OK);
    }

    /**
     * Update an existing media entry record
     *
     * Returns one of the following status codes:
     * 200: successfully updated media entry.
     * 400: failed to update media entry.
     * 401: authentication token is invalid.
     * 500: SQL Database error occurred.
     *
     * @param mediaEntry a JSON representation of a MediaEntry object
     * @return HttpStatus
     */
    @PutMapping(value="/editMediaEntry", consumes="application/json", produces="application/json")
    public ResponseEntity editMediaEntry(@RequestBody MediaEntry mediaEntry, @RequestParam(value="username", required=true) String username, @RequestParam(value="token", required=true) String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.debug("Entering Edit Media Entry endpoint.");
        // authenticate request
        try {
            if (!userAccountService.isTokenValid(token, username)) {
                log.info("Unauthorized token");
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            if (!mediaEntryService.updateMediaEntry(mediaEntry)) {
                log.info("Bad HTTP Request");
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("There was a problem editing media entry. Message: " + e.getMessage(), e);
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(headers, HttpStatus.OK);
    }

    /**
     * Delete media entry record
     *
     * Returns one of the following status codes:
     * 200: successfully deleted media entry.
     * 400: failed to delete media entry.
     * 401: authentication token is invalid.
     * 500: SQL Database error occurred.
     *
     * @param entryId integer uniquely identifying the media entry record
     * @return HttpStatus
     */
    @DeleteMapping("/removeMediaEntry")
    public ResponseEntity removeMediaEntry(@RequestParam(value="entryId", required=true) int entryId, @RequestParam(value="username", required=true) String username, @RequestParam(value="token", required=true) String token) {
        log.debug("Entering delete media entry endpoint");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // authenticate request
        try {
            if (!userAccountService.isTokenValid(token, username)) {
                log.info("Unauthorized token");
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            if (!mediaEntryService.deleteMediaEntry(entryId)) {
                log.info("Bad HTTP Request");
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Unable to delete the media entry with ID " + entryId + ", message: " + e.getMessage(), e);
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Media entry with ID " + entryId + " was deleted successfully.");
        return new ResponseEntity(headers, HttpStatus.OK);

    }

    /**
     * Create a new media entry record
     *
     * Returns one of the following status codes:
     * 201: successfully created media entry.
     * 400: failed to create media entry.
     * 401: authentication token is invalid.
     * 500: SQL Database error occurred.
     *
     * @param mediaEntry a JSON representation of a MediaEntry object
     * @return HttpStatus
     */
    @PostMapping(value="/addMediaEntry", consumes="application/json", produces="application/json")
    public ResponseEntity addMediaEntry(@RequestBody MediaEntry mediaEntry, @RequestParam(value="username", required=true) String username, @RequestParam(value="token", required=true) String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.debug("Entering Add Media Entry endpoint.");
        // authenticate request
        try {
            if (!userAccountService.isTokenValid(token, username)) {
                log.info("Unauthorized token.");
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }

            if (!mediaEntryService.createMediaEntry(mediaEntry)) {
                log.info("Bad HTTP Request.");
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Unable to add the media entry. Message: " + e.getMessage(), e);
            return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Entry created!");
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
}
