package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.util.List;

public class MediaEntryServiceStub implements IMediaEntryService {
    @Override
    public boolean createMediaEntry(MediaEntry mediaEntry) {
        return false;
    }

    @Override
    public boolean updateMediaEntry(MediaEntry mediaEntry) {
        return false;
    }

    @Override
    public boolean deleteMediaEntry(int entryId) {
        return false;
    }

    @Override
    public List<MediaEntry> fetchMediaEntriesByUsername(String username) {
        return null;
    }
}
