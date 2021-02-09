package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.util.List;

public interface IMediaEntryService {
    boolean createMediaEntry(MediaEntry mediaEntry);
    boolean updateMediaEntry(MediaEntry mediaEntry);
    boolean deleteMediaEntry(int entryId);
    List<MediaEntry> fetchMediaEntriesByUsername(String username);
}
