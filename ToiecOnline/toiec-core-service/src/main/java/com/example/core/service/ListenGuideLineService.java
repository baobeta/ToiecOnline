package com.example.core.service;

import com.example.core.dto.ListenGuideLineDTO;

import java.util.List;
import java.util.Map;

public interface ListenGuideLineService {
    Object[] findListenGuideLineByProperties(Map<String,Object> property, String sortExpression, String sortDirection,
                                             Integer offset, Integer limit);
}
