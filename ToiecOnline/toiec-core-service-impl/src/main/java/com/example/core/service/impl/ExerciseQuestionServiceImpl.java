package com.example.core.service.impl;

import com.example.core.dto.ExerciseQuestionDTO;
import com.example.core.persistence.entity.ExerciseQuestionEntity;
import com.example.core.service.ExerciseQuestionService;
import com.example.core.service.utils.SingletonDaoUtil;
import com.example.core.utils.ExerciseBeanUtils;
import com.example.core.utils.ExerciseQuestionBeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseQuestionServiceImpl implements ExerciseQuestionService {
    public Object[] findExerciseQuestionByProperties(Map<String, Object> property, String sortExpression,
                                                     String sortDirection, Integer offset, Integer limit) {
        List<ExerciseQuestionDTO> result = new ArrayList<ExerciseQuestionDTO>();
        String whereClause = null;
//        if (exerciseId != null) {
//            whereClause = " AND exerciseEntity.exerciseId = "+exerciseId+"";
//        }
        Object[] objects = SingletonDaoUtil.getExerciseQuestionDaoInstance().findByProperty( property, sortExpression, sortDirection, offset, limit);
        for (ExerciseQuestionEntity item: (List<ExerciseQuestionEntity>)objects[1]) {
            ExerciseQuestionDTO dto = ExerciseQuestionBeanUtils.entity2Dto(item);
            dto.setExercise(ExerciseBeanUtils.entity2Dto(item.getExerciseEntity()));
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }
}
