package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Repository
@Profile("dev")
public class MediaEntryDAO implements IMediaEntryDAO {
    Map<Integer, MediaEntry> allMediaEntries = new HashMap<Integer, MediaEntry>();


    @Override
    public boolean save(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        Integer mediaEntryId = mediaEntry.getId();
        allMediaEntries.put(mediaEntryId, mediaEntry);
        return true;
    }

    /**
     * Method for fetching all MediaEntry records for a given user
     *
     * @param username String uniquely identify a UserAccount record
     * @return List of MediaEntry objects belonging to the given user
     */
    @Override
    public List<MediaEntry> fetchByUsername(String username) throws SQLException, IOException, ClassNotFoundException {
        List<MediaEntry> returnMediaEntries = new ArrayList(Collections.singleton(allMediaEntries.containsValue(username)));
        return returnMediaEntries;
    }

    /**
     * Method for fetching a distinct record in the database
     *
     * @param id integer uniquely identifying a MediaEntry record
     * @return MediaEntry object representation of corresponding database record
     */
    @Override
    public MediaEntry fetch(int id) throws SQLException, IOException, ClassNotFoundException {
        return allMediaEntries.get(id);
    }

    /**
     * Method for deleting a single MediaEntry record in the database
     *
     * @param id integer uniquely identifying a MediaEntry record
     * @return boolean indicating a successfully delete
     */
    @Override
    public boolean delete(int id) throws SQLException, IOException, ClassNotFoundException {
        allMediaEntries.remove(id);
        return true;
    }

    @Override
    public boolean update(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        return false;
    }


    /**
     * Method for parsing SQL results into List of MediaEntry objects
     *
     * @param results data structure representation of sql results
     * @return List of MediaEntry objects
     */
    private List<MediaEntry> parse(ArrayList<HashMap<String, Object>> results) {
        ArrayList<MediaEntry> mediaEntries = new ArrayList<>();
        for (HashMap valuesMap: results) {
            MediaEntry mediaEntry = new MediaEntry();
            mediaEntry.setId((Integer) valuesMap.get("id"));
            mediaEntry.setTitle((String) valuesMap.get("title"));
            mediaEntry.setType((String) valuesMap.get("type"));
            mediaEntry.setPlatform((String) valuesMap.get("platform"));
            mediaEntry.setDescription((String) valuesMap.get("description"));
            mediaEntry.setImageUrl((String) valuesMap.get("imageUrl"));
            mediaEntry.setWatched((Boolean) valuesMap.get("watched"));
            mediaEntry.setUsername((String) valuesMap.get("username"));
            mediaEntries.add(mediaEntry);
        }
        return mediaEntries;
    }
}
