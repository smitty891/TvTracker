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

    @Override
    public boolean createMediaEntry(MediaEntry mediaEntry) throws Exception {
        if(mediaEntry == null){
            return false;
        }

        mediaEntryDAO.save(mediaEntry);

        return true;
    }

    @Override
    public boolean updateMediaEntry(MediaEntry mediaEntry) throws Exception {
        if(mediaEntry == null){
            return false;
        }

        mediaEntryDAO.update(mediaEntry);

        return true;
    }

    @Override
    public boolean deleteMediaEntry(int entryId) throws Exception {
        mediaEntryDAO.delete(entryId);

        return true;
    }

    @Override
    public List<MediaEntry> fetchMediaEntriesByUsername(String username) throws Exception {
        return mediaEntryDAO.fetchByUsername(username);
    }
}
