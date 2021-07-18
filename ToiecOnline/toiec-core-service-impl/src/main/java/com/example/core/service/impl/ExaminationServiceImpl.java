package com.example.core.service.impl;

import com.example.core.dto.ExaminationDTO;
import com.example.core.dto.ExaminationQuestionDTO;
import com.example.core.persistence.entity.ExaminationEntity;
import com.example.core.persistence.entity.ExaminationQuestionEntity;
import com.example.core.service.ExaminationQuestionService;
import com.example.core.service.ExaminationService;
import com.example.core.service.utils.SingletonDaoUtil;
import com.example.core.utils.ExaminationBeanUtil;
import com.example.core.utils.ExaminationQuestionBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationServiceImpl implements ExaminationService {
    public Object[] findExaminationByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExaminationDTO> result = new ArrayList<ExaminationDTO>();
        Object[] objects = SingletonDaoUtil.getExaminationDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        for (ExaminationEntity item: (List<ExaminationEntity>)objects[1]) {
            ExaminationDTO dto = ExaminationBeanUtil.entity2Dto(item);
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }

}
