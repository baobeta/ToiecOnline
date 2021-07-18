package com.example.core.service.impl;

import com.example.core.dto.ExaminationQuestionDTO;
import com.example.core.persistence.entity.ExaminationQuestionEntity;
import com.example.core.service.ExaminationQuestionService;
import com.example.core.service.utils.SingletonDaoUtil;
import com.example.core.utils.ExaminationBeanUtil;
import com.example.core.utils.ExaminationQuestionBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationQuestionServiceImpl  implements ExaminationQuestionService {
    public Object[] findExaminationQuestionByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExaminationQuestionDTO> result = new ArrayList<ExaminationQuestionDTO>();
        Object[] objects = SingletonDaoUtil.getExaminationQuestionDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        int count = 1;
        for (ExaminationQuestionEntity item: (List<ExaminationQuestionEntity>)objects[1]) {
            ExaminationQuestionDTO dto = ExaminationQuestionBeanUtil.entity2Dto(item);
            if (item.getParagraph() == null) {
                dto.setNumber(count);
                count++;
            }
            dto.setExamination(ExaminationBeanUtil.entity2Dto(item.getExamination()));
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }
}
