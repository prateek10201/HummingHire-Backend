package org.humminghire.backend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.humminghire.backend.model.Work;

import java.util.List;

@Converter(autoApply = true)
public class WorkListConverter implements AttributeConverter<List<Work>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .registerModule(new JavaTimeModule());

    private final TypeReference<List<Work>> typeRef = new TypeReference<>() {};


    public String convertToDatabaseColumn(List<Work> works) {
        try {
            return works != null ? objectMapper.writeValueAsString(works) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Work list to JSON", e);
        }
    }


    public List<Work> convertToEntityAttribute(String json) {
        try {
            return json != null ? objectMapper.readValue(json, typeRef) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to Work list", e);
        }
    }
}
