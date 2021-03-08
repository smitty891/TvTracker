/**
 * @author add author name here
 * @since   Add Date Here
 */
package com.tvtracker.enterprise.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public @Data
class  MediaEntry {
    /**
     * MediaEntry's unique identifier
     */
    private int entryId;
    private String title;
    private String username;
    private String type;
    private String platform;
    private String description;
    private String imageUrl;
    private boolean watched;
}
