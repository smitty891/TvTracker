/**
 * @author add author name here
 * @since   Add Date Here
 */
package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.util.List;

/**
 * Data Access Object for Media Entries
 * <p>
 *     This class allows access to MediaEntry records in our underlying database.
 * </p>
 */
public interface IMediaEntryDAO {
        /**
         * Method for creating a new MediaEntry record in the database
         *
         * @param mediaEntry MediaEntry object to be saved as a record in the database
         * @return MediaEntry object representation of new database record
         * @throws Exception when database fails to save MediaEntry
         */
        MediaEntry save(MediaEntry mediaEntry) throws Exception;


        /**
         * Method for fetching all MediaEntry records for a given user
         *
         * @param username String uniquely identify a UserAccount record
         * @return List of MediaEntry objects belonging to the given user
         */
        List<MediaEntry>  fetchByUsername(String username) throws Exception;


        /**
         * Method for fetching a distinct record in the database
         *
         * @param entryId integer uniquely identifying a MediaEntry record
         * @return MediaEntry object representation of corresponding database record
         */
        MediaEntry fetch(int entryId) throws Exception;

        /**
         * Method for deleting a single MediaEntry record in the database
         *
         * @param entryId integer uniquely identifying a MediaEntry record
         */
        void delete(int entryId) throws Exception;


        /**
         * Method for updating a MediaEntry record in the database
         *
         * @param mediaEntry MediaEntry object to be used for updating a database record
         */
        void update(MediaEntry mediaEntry) throws Exception;
}
