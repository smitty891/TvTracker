package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class MediaEntryDAOStub implements  IMediaEntryDAO {
    HashMap<Integer, MediaEntry> entriesByID = new HashMap<Integer, MediaEntry>();
    HashMap<String, HashMap<Integer, MediaEntry>> entriesByUsername = new HashMap<String, HashMap<Integer, MediaEntry>>();

    /**
     * Method for creating a new MediaEntry record in the database
     *
     * @param mediaEntry MediaEntry object to be saved as a record in the database
     * @return MediaEntry object representation of new database record
     * @throws Exception when database fails to save MediaEntry
     */
    @Override
    public MediaEntry save(MediaEntry mediaEntry) throws Exception {
        mediaEntry.setEntryId(entriesByID.size());
        entriesByID.put(mediaEntry.getEntryId(), mediaEntry);

        if(entriesByUsername.containsKey(mediaEntry.getUsername())){
            entriesByUsername.get(mediaEntry.getUsername()).put(mediaEntry.getEntryId(), mediaEntry);
        } else {
            HashMap<Integer, MediaEntry> entries = new HashMap<Integer, MediaEntry>();
            entries.put(mediaEntry.getEntryId(), mediaEntry);
            entriesByUsername.put(mediaEntry.getUsername(), entries);
        }

        return mediaEntry;
    }

    /**
     * Method for fetching all MediaEntry records for a given user
     *
     * @param username String uniquely identify a UserAccount record
     * @return List of MediaEntry objects belonging to the given user
     */
    @Override
    public List<MediaEntry> fetchByUsername(String username) throws Exception {
        return new ArrayList(entriesByUsername.get(username).values());
    }

    /**
     * Method for fetching a distinct record in the database
     *
     * @param entryId integer uniquely identifying a MediaEntry record
     * @return MediaEntry object representation of corresponding database record
     */
    @Override
    public MediaEntry fetch(int entryId) throws Exception {
        return entriesByID.get(entryId);
    }

    /**
     * Method for deleting a single MediaEntry record in the database
     *
     * @param entryId integer uniquely identifying a MediaEntry record
     */
    @Override
    public void delete(int entryId) throws Exception {
        MediaEntry entry = entriesByID.remove(entryId);
        if(entry != null) {
            entriesByUsername.get(entry.getUsername()).remove(entryId);
        }
    }

    /**
     * Method for updating a MediaEntry record in the database
     *
     * @param mediaEntry MediaEntry object to be used for updating a database record
     */
    @Override
    public void update(MediaEntry mediaEntry) throws Exception {
        MediaEntry oldEntry = entriesByID.remove(mediaEntry.getEntryId());

        if(oldEntry != null) {
            entriesByID.put(mediaEntry.getEntryId(), mediaEntry);
            entriesByUsername.get(oldEntry.getUsername()).put(mediaEntry.getEntryId(), mediaEntry);
        }
    }
}
