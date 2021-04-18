package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Repository("mediaEntryDAO")
public class MediaEntrySQLDAO implements IMediaEntryDAO {

    @Autowired
    MediaEntryRepository mediaEntryRepository;

    @Override
    public boolean save(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        mediaEntryRepository.save(mediaEntry);
        return true;
    }

    @Override
    public List<MediaEntry> fetchByUsername(String username) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public MediaEntry fetch(int id) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException, IOException, ClassNotFoundException {
        mediaEntryRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        return false;
    }
}
