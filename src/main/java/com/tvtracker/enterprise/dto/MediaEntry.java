package com.tvtracker.enterprise.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public @Data
class MediaEntry {
    /**
     * MediaEntry's unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String username;
    private String type;
    private String platform;
    private String description;
    private String imageUrl;
    private boolean watched;
}
