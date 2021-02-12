package com.tvtracker.enterprise;

import com.tvtracker.enterprise.dto.MediaEntry;
import com.tvtracker.enterprise.dto.UserAccount;
import com.tvtracker.enterprise.service.IMediaEntryService;
import com.tvtracker.enterprise.service.IUserAccountService;
import com.tvtracker.enterprise.service.MediaEntryServiceStub;
import com.tvtracker.enterprise.service.UserAccountServiceStub;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class EnterpriseApplicationTests {

    IUserAccountService userAccountService = new UserAccountServiceStub();
    IMediaEntryService mediaEntryService;

    @Test
    void contextLoads() {
    }

    @Test
    void userCreatesUserAccount_ReturnsValidAuthenticationToken() {
        UserAccount userAccount = whenUserSendsUserAccountWithUniqueUsername();
        returnsUserAccountWithValidToken(userAccount);
    }

    private UserAccount whenUserSendsUserAccountWithUniqueUsername() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("testUser");
        userAccount.setPassword("testPassword");
        userAccount.setEmail("testUser@testSite.com");

        UserAccount newUserAccount = userAccountService.createUserAccount(userAccount);

        Assert.notNull(newUserAccount, "Creating user account returned null indicating username is not unique.");

        return newUserAccount;
    }

    private void returnsUserAccountWithValidToken(UserAccount userAccount) {
        Assert.notNull(userAccount, "Creating user account returned null indicating username is not unique.");

        boolean isValid = userAccountService.isTokenValid(userAccount.getToken(), userAccount.getUsername());

        Assert.isTrue(isValid, "Token returned from user account creation was not valid.");
    }

    @Test
    void userUpdatesMediaEntry_ReturnsSuccessBoolean() {
        // start with fresh media entry collection
        mediaEntryService = new MediaEntryServiceStub();

        givenUserHasRetrievedMediaEntries();
        int entryId = whenUserUpdatesMediaEntry();
        returnIndicationOfSuccess(entryId);
    }

    private void givenUserHasRetrievedMediaEntries() {
        List<MediaEntry> mediaEntries = mediaEntryService.fetchMediaEntriesByUsername("testUser");

        Assert.notNull(mediaEntries, "User's collection of media entries was null.");
        Assert.noNullElements(mediaEntries, "A null element was found in user's collection of media entries.");
        Assert.notEmpty(mediaEntries, "User's collection of media entries was empty.");
    }

    private int whenUserUpdatesMediaEntry() {
        List<MediaEntry> mediaEntries = mediaEntryService.fetchMediaEntriesByUsername("testUser");
        // update a media entry
        MediaEntry mediaEntry = mediaEntries.get(0);
        MediaEntry newMediaEntry = new MediaEntry();
        newMediaEntry.setEntryId(mediaEntry.getEntryId());
        newMediaEntry.setUsername(mediaEntry.getUsername());
        newMediaEntry.setTitle(mediaEntry.getTitle());
        newMediaEntry.setType(mediaEntry.getType());
        newMediaEntry.setDescription("Terrible");
        newMediaEntry.setPlatform("Hulu");
        newMediaEntry.setWatched(true);

        boolean success = mediaEntryService.updateMediaEntry(newMediaEntry);
        Assert.isTrue(success, "An error occurred while updating a media entry.");

        return newMediaEntry.getEntryId();
    }

    private void returnIndicationOfSuccess(int entryId) {
        // check if media entry was successfully updated
        List<MediaEntry> mediaEntries = mediaEntryService.fetchMediaEntriesByUsername("testUser");
        MediaEntry foundEntry = null;
        for(MediaEntry entry: mediaEntries) if (entry.getEntryId() == entryId) foundEntry = entry;

        Assert.notNull(foundEntry, "The updated media entry was not found.");
        boolean updated = foundEntry.isWatched() && foundEntry.getPlatform().equals("Hulu") && foundEntry.getDescription().equals("Terrible");
        Assert.isTrue(updated, "Media entry's fields were not updated.");
    }
}
