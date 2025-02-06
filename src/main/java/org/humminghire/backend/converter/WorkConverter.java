package org.humminghire.backend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.humminghire.backend.model.Work;

@Converter(autoApply = true)
public class WorkConverter implements AttributeConverter<Work, String> {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .registerModule(new JavaTimeModule());

    @Override
    public String convertToDatabaseColumn(Work work) {
        try {
            return work != null ? objectMapper.writeValueAsString(work) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Work to JSON", e);
        }
    }

    @Override
    public Work convertToEntityAttribute(String json) {
        try {
            return json != null ? objectMapper.readValue(json, Work.class) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to Work", e);
        }
    }
}
