package org.humminghire.backend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.humminghire.backend.model.Certification;

import java.util.List;

@Converter(autoApply = true)
public class CertificationListConverter implements AttributeConverter<List<Certification>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .registerModule(new JavaTimeModule());

    private final TypeReference<List<Certification>> typeRef = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(List<Certification> certifications) {
        try {
            return certifications != null ? objectMapper.writeValueAsString(certifications) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Certification list to JSON", e);
        }
    }

    @Override
    public List<Certification> convertToEntityAttribute(String json) {
        try {
            return json != null ? objectMapper.readValue(json, typeRef) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to Certification list", e);
        }
    }
}
