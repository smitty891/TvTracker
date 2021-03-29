package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.io.IOException;
import java.sql.SQLException;
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
    boolean createMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Updates an existing MediaEntry database record.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    boolean updateMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Removes a MediaEntry record from the database.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    boolean deleteMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Retrieves all MediaEntry objects for a given user
     *
     * @param username String uniquely identifying a user
     * @return List of user's MediaEntry objects
     */
    List<MediaEntry> fetchMediaEntriesByUsername(String username) throws SQLException, IOException, ClassNotFoundException;
}
