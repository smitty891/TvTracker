package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.data.repository.CrudRepository;

public interface MediaEntryRepository extends CrudRepository<MediaEntry, Integer> {
}
