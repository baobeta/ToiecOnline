package com.example.core.dao;

import com.example.core.data.dao.GenericDao;
import com.example.core.persistence.entity.ExaminationQuestionEntity;

public interface ExaminationQuestionDao extends GenericDao<Integer, ExaminationQuestionEntity> {
   // Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, Integer examinationId);
}
