package org.humminghire.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Project {
    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("positionTitle")
    private String positionTitle;

    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private String link;
}
