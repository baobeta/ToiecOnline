package com.example.core.service.impl;

import com.example.core.dao.ListenGuideLineDao;
import com.example.core.daoimpl.ListenGuideLineImpl;
import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.persistence.entity.ListenGuideLineEntity;
import com.example.core.service.ListenGuideLineService;
import com.example.core.service.utils.SingletonDaoUtil;
import com.example.core.utils.ListenGuideLineBeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenGuideLineServiceImpl implements ListenGuideLineService {
    public Object[] findListenGuideLineByProperties(Map<String,Object> property, String sortExpression, String sortDirection,
                                                    Integer offset, Integer limit) {
        List<ListenGuideLineDTO> result = new ArrayList<ListenGuideLineDTO>();
        Object[] objects = SingletonDaoUtil.getListenGuidelineDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        for(ListenGuideLineEntity item :(List<ListenGuideLineEntity>)objects[1]) {
            ListenGuideLineDTO dto = ListenGuideLineBeanUtils.entity2Dto(item);
            result.add(dto);
        }
        objects[1] =result;
        return objects;


    }
}
