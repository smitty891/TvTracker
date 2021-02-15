package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.util.List;

public interface IMediaEntryDAO {
        MediaEntry save(MediaEntry mediaEntry) throws Exception;

        List<MediaEntry>  fetchByUsername(String username);

        MediaEntry fetch(int entryId);

        void delete(int entryId);

}
