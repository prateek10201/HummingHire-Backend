package org.humminghire.backend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.humminghire.backend.model.Project;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

@Converter(autoApply = true)
public class ProjectListConverter implements AttributeConverter<List<Project>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .registerModule(new JavaTimeModule());

    private final TypeReference<List<Project>> typeRef = new TypeReference<>() {};


    public String convertToDatabaseColumn(List<Project> projects) {
        try {
            return projects != null ? objectMapper.writeValueAsString(projects) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Project list to JSON", e);
        }
    }


    public List<Project> convertToEntityAttribute(String json) {
        try {
            return json != null ? objectMapper.readValue(json, typeRef) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to Project list", e);
        }
    }
}
