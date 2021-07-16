package com.example.core.utils;

import com.example.core.dto.ExerciseDTO;
import com.example.core.persistence.entity.ExerciseEntity;

public class ExerciseBeanUtils {
    public static ExerciseDTO entity2Dto(ExerciseEntity entity) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setExerciseId(entity.getExerciseId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ExerciseEntity dto2Entity(ExerciseDTO dto) {
        ExerciseEntity entity = new ExerciseEntity();
        entity.setExerciseId(dto.getExerciseId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
