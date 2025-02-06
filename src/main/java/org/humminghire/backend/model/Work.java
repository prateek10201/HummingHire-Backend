package org.humminghire.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Work {

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("location")
    private String location;

    @JsonProperty("position")
    private String position;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @JsonProperty("startDate")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @JsonProperty("endDate")
    private Date endDate;

    @JsonProperty("description")
    private String description;

}
