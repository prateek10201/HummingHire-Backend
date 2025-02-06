package org.humminghire.backend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.humminghire.backend.model.ScreeningQuestionAndAnswer;

import java.util.List;

@Converter(autoApply = true)
public class ScreeningQAListConverter implements AttributeConverter<List<ScreeningQuestionAndAnswer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<List<ScreeningQuestionAndAnswer>> typeRef = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(List<ScreeningQuestionAndAnswer> screeningQAs) {
        try {
            return screeningQAs != null ? objectMapper.writeValueAsString(screeningQAs) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting ScreeningQA list to JSON", e);
        }
    }

    @Override
    public List<ScreeningQuestionAndAnswer> convertToEntityAttribute(String json) {
        try {
            return json != null ? objectMapper.readValue(json, typeRef) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to ScreeningQA list", e);
        }
    }
}
