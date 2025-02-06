package org.humminghire.backend.model;

import lombok.Data;

import java.util.Map;


@Data
public class ScreeningQuestionAndAnswer {
    private Map<String, String> questionsAndAnswers;
}
