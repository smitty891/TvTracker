package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dao.IMediaEntryDAO;
import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class MediaEntryService implements IMediaEntryService {

    @Autowired
    IMediaEntryDAO mediaEntryDAO;

    /**
     * Adds a new MediaEntry record to the database.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    @Override
    public boolean createMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        if(mediaEntry == null)
            return false;

        return mediaEntryDAO.save(mediaEntry);
    }

    /**
     * Updates an existing MediaEntry database record.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    @Override
    public boolean updateMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        if(mediaEntry == null){
            return false;
        }

        mediaEntryDAO.update(mediaEntry);

        return true;
    }

    /**
     * Removes a MediaEntry record from the database.
     *
     * @param entryId integer uniquely identifying a MediaEntry record
     * @return boolean indicating success or failure
     */
    @Override
    @CacheEvict(value="mediaEntry", key="entryId")
    public boolean deleteMediaEntry(int entryId) throws SQLException, IOException, ClassNotFoundException {
        return mediaEntryDAO.delete(entryId);
    }

    /**
     * Retrieves all MediaEntry objects for a given user
     *
     * @param username String uniquely identifying a user
     * @return List of user's MediaEntry objects
     */
    @Override
    @Cacheable(value="mediaEntries", key="username")
    public List<MediaEntry> fetchMediaEntriesByUsername(String username) throws SQLException, IOException, ClassNotFoundException {
        return mediaEntryDAO.fetchByUsername(username);
    }
}
