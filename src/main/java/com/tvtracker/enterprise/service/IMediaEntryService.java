package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.util.List;

/**
 * Implementations of IMediaEntryService contain all the methods necessary for TvTracker's MediaEntry functionality.
 */
public interface IMediaEntryService {
    /**
     * Adds a new MediaEntry record to the database.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    boolean createMediaEntry(MediaEntry mediaEntry);

    /**
     * Updates an existing MediaEntry database record.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    boolean updateMediaEntry(MediaEntry mediaEntry);

    /**
     * Removes a MediaEntry record from the database.
     *
     * @param entryId integer uniquely identifying a MediaEntry record
     * @return boolean indicating success or failure
     */
    boolean deleteMediaEntry(int entryId);

    /**
     * Retrieves all MediaEntry objects for a given user
     *
     * @param username String uniquely identifying a user
     * @return List of user's MediaEntry objects
     */
    List<MediaEntry> fetchMediaEntriesByUsername(String username);
}
