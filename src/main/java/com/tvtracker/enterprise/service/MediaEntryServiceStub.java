package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dao.IMediaEntryDAO;
import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaEntryServiceStub implements IMediaEntryService {

    @Autowired
    IMediaEntryDAO mediaEntryDAO;

    /**
     * Adds a new MediaEntry record to the database.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    @Override
    public boolean createMediaEntry(MediaEntry mediaEntry) throws Exception {
        if(mediaEntry == null){
            return false;
        }

        mediaEntryDAO.save(mediaEntry);

        return true;
    }

    /**
     * Updates an existing MediaEntry database record.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    @Override
    public boolean updateMediaEntry(MediaEntry mediaEntry) throws Exception {
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
    public boolean deleteMediaEntry(int entryId) throws Exception {
        mediaEntryDAO.delete(entryId);

        return true;
    }

    /**
     * Retrieves all MediaEntry objects for a given user
     *
     * @param username String uniquely identifying a user
     * @return List of user's MediaEntry objects
     */
    @Override
    public List<MediaEntry> fetchMediaEntriesByUsername(String username) throws Exception {
        return mediaEntryDAO.fetchByUsername(username);
    }
}
