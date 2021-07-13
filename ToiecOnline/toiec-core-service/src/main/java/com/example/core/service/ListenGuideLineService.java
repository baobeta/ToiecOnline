package com.example.core.service;

import com.example.core.dto.ListenGuideLineDTO;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Map;

public interface ListenGuideLineService {
    Object[] findListenGuideLineByProperties(Map<String,Object> property, String sortExpression, String sortDirection,
                                             Integer offset, Integer limit);
    ListenGuideLineDTO findByListenGuideLineId(String property,Integer listenGuidelineId);
    void saveListenGuideLine(ListenGuideLineDTO dto) throws Exception;
    ListenGuideLineDTO updateListenGuideLine(ListenGuideLineDTO dto);
    Integer delete(List<Integer> ids);
}
