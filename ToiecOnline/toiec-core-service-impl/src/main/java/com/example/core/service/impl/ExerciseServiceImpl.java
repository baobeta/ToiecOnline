package com.example.core.service.impl;

import com.example.core.dto.ExerciseDTO;
import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.persistence.entity.ExerciseEntity;
import com.example.core.persistence.entity.ListenGuideLineEntity;
import com.example.core.service.ExerciseService;
import com.example.core.service.utils.SingletonDaoUtil;
import com.example.core.utils.ExerciseBeanUtils;
import com.example.core.utils.ListenGuideLineBeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseServiceImpl implements ExerciseService {
    @Override
    public Object[] findExerciseByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExerciseDTO> result = new ArrayList<ExerciseDTO>();
        Object[] objects = SingletonDaoUtil.getExerciseDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        for(ExerciseEntity item : (List<ExerciseEntity>)objects[1]) {
            ExerciseDTO dto = ExerciseBeanUtils.entity2Dto(item);
            result.add(dto);
        }
        objects[1] =result;
        return objects;
    }
}
