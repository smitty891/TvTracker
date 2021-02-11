package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.util.ArrayList;
import java.util.List;

public class MediaEntryServiceStub implements IMediaEntryService {

    private List<MediaEntry> mediaEntries = new ArrayList<MediaEntry>();

    public MediaEntryServiceStub(){
        String[] titles = {"Fargo", "Idle hands", "Evil Dead", "Apocalypse Now" ,"Death to Smoochy"};
        for(int i = 0; i < titles.length; i++) {
            MediaEntry mediaEntry = new MediaEntry();
            mediaEntry.setEntryId(i);
            mediaEntry.setDescription("I rate " + titles[i] + " " + (i + 1) + " out of " + titles.length);
            mediaEntry.setPlatform("Netflix");
            mediaEntry.setType("Movie");
            mediaEntry.setUsername("testUser");
            mediaEntry.setTitle(titles[i]);
            mediaEntry.setWatched(false);
            this.mediaEntries.add(mediaEntry);
        }
    }

    @Override
    public boolean createMediaEntry(MediaEntry mediaEntry) {
        if(mediaEntry == null){
            return false;
        }

        this.mediaEntries.add(mediaEntry);
        return true;
    }

    @Override
    public boolean updateMediaEntry(MediaEntry mediaEntry) {
        for(MediaEntry entry: this.mediaEntries){
            if(entry.getEntryId() == mediaEntry.getEntryId()){
                this.mediaEntries.remove(entry);
                this.mediaEntries.add(mediaEntry);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteMediaEntry(int entryId) {
        for(MediaEntry entry: this.mediaEntries){
            if(entry.getEntryId() == entryId){
                this.mediaEntries.remove(entry);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<MediaEntry> fetchMediaEntriesByUsername(String username) {
        return this.mediaEntries;
    }
}
