package com.tvtracker.enterprise.dto;

import lombok.Data;

@Data
public class MediaEntry {
    /**
     * MediaEntry's unique identifier
     */
    private int id;
    private String title;
    private String username;
    private String type;
    private String platform;
    private String description;
    private String imageUrl;
    private boolean watched;
}
