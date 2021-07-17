package com.example.core.service;

import java.util.Map;

public interface ExerciseQuestionService {
    public Object[] findExerciseQuestionByProperties(Map<String, Object> property, String sortExpression,
                                                     String sortDirection, Integer offset, Integer limit);
}
