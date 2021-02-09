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

@Controller
public class TvTrackerController {
    IUserAccountService userAccountService = new UserAccountServiceStub();
    IMediaEntryService mediaEntryService = new MediaEntryServiceStub();

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

    @GetMapping("/getMediaEntries/{username}")
    @ResponseBody
    public List<MediaEntry> getUsersMediaEntries(@PathVariable("username") String username) {
        return mediaEntryService.fetchMediaEntriesByUsername(username);
    }

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

    @PostMapping(value="/addMediaEntry", consumes="application/json", produces="application/json")
    public ResponseEntity mediaMediaEntry(@RequestBody MediaEntry mediaEntry){
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
