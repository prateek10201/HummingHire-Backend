package org.humminghire.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Education {
    @JsonProperty("schoolName")
    private String schoolName;

    @JsonProperty("major")
    private String major;

    @JsonProperty("degreeType")
    private String degreeType;

    @JsonProperty("gpa")
    private String gpa;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @JsonProperty("startDate")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @JsonProperty("endDate")
    private Date endDate;
}
