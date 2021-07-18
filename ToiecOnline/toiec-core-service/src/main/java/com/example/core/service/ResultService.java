package com.example.core.service;

import com.example.core.dto.ExaminationQuestionDTO;
import com.example.core.dto.ResultDTO;
import com.example.core.persistence.entity.ExaminationEntity;
import com.example.core.persistence.entity.ResultEntity;
import com.example.core.persistence.entity.UserEntity;

import java.util.List;

public interface ResultService {
    ResultDTO saveResult(String userName, Integer examinationId, List<ExaminationQuestionDTO> examinationQuestions);
}
