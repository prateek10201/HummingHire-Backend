package org.humminghire.backend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.humminghire.backend.model.Education;

import java.util.List;

@Converter(autoApply = true)
public class EducationListConverter implements AttributeConverter<List<Education>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .registerModule(new JavaTimeModule());

    private final TypeReference<List<Education>> typeRef = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(List<Education> education) {
        try {
            return education != null ? objectMapper.writeValueAsString(education) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Education list to JSON", e);
        }
    }

    @Override
    public List<Education> convertToEntityAttribute(String json) {
        try {
            return json != null ? objectMapper.readValue(json, typeRef) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to Education list", e);
        }
    }
}
