package com.tvtracker.enterprise;

import com.tvtracker.enterprise.dto.MediaEntry;
import com.tvtracker.enterprise.dto.UserAccount;
import com.tvtracker.enterprise.service.IMediaEntryService;
import com.tvtracker.enterprise.service.IUserAccountService;
import com.tvtracker.enterprise.service.MediaEntryServiceStub;
import com.tvtracker.enterprise.service.UserAccountServiceStub;
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
    IUserAccountService userAccountService = new UserAccountServiceStub();
    IMediaEntryService mediaEntryService = new MediaEntryServiceStub();

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
    public ResponseEntity signUpUser(@RequestBody UserAccount userAccount){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            UserAccount newUserAccount = userAccountService.createUserAccount(userAccount);
            String token = newUserAccount.getToken();
            return new ResponseEntity(token, headers, HttpStatus.OK);
        } catch (Exception e) {
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
    public ResponseEntity authenticateUser(@PathVariable("username") String username, @PathVariable("password") String password){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserAccount userAccount = userAccountService.fetchUserAccount(username);

        if(userAccount != null && userAccount.getPassword().equals(password)){
            String token = userAccountService.updateUserToken(userAccount);
            return new ResponseEntity(token, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Returns all media entries tied to the given username
     *
     * @param username String uniquely identifying a user
     * @return List user's media entries
     */
    @GetMapping("/getMediaEntries/{username}")
    @ResponseBody
    public List<MediaEntry> getUsersMediaEntries(@PathVariable("username") String username) {
        return mediaEntryService.fetchMediaEntriesByUsername(username);
    }

    /**
     * Update an existing media entry record
     *
     * Returns one of the following status codes:
     * 201: successfully updated media entry.
     * 400: failed to update media entry.
     *
     * @param mediaEntry a JSON representation of a MediaEntry object
     * @return HttpStatus
     */
    @PutMapping(value="/editMediaEntry", consumes="application/json", produces="application/json")
    public ResponseEntity editMediaEntry(@RequestBody MediaEntry mediaEntry){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        boolean success = mediaEntryService.updateMediaEntry(mediaEntry);

        if(success){
            return new ResponseEntity(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete media entry record
     *
     * Returns one of the following status codes:
     * 201: successfully deleted media entry.
     * 400: failed to delete media entry.
     *
     * @param entryId integer uniquely identifying the media entry record
     * @return HttpStatus
     */
    @DeleteMapping("/removeMediaEntry/{entryId}")
    public ResponseEntity removeMediaEntry(@PathVariable("entryId") int entryId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        boolean success = mediaEntryService.deleteMediaEntry(entryId);

        if(success){
            return new ResponseEntity(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a new media entry record
     *
     * Returns one of the following status codes:
     * 201: successfully created media entry.
     * 400: failed to create media entry.
     *
     * @param mediaEntry a JSON representation of a MediaEntry object
     * @return HttpStatus
     */
    @PostMapping(value="/addMediaEntry", consumes="application/json", produces="application/json")
    public ResponseEntity addMediaEntry(@RequestBody MediaEntry mediaEntry){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        boolean success = mediaEntryService.createMediaEntry(mediaEntry);

        if(success){
            return new ResponseEntity(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
    }
}
