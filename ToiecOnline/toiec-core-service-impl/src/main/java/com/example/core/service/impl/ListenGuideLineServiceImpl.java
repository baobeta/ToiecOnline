package com.example.core.service.impl;


import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.persistence.entity.ListenGuideLineEntity;
import com.example.core.service.ListenGuideLineService;
import com.example.core.service.utils.SingletonDaoUtil;
import com.example.core.utils.ListenGuideLineBeanUtils;
import org.hibernate.exception.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenGuideLineServiceImpl implements ListenGuideLineService {
    public Object[] findListenGuideLineByProperties(Map<String,Object> property, String sortExpression, String sortDirection,
                                                    Integer offset, Integer limit) {
        List<ListenGuideLineDTO> result = new ArrayList<ListenGuideLineDTO>();
        Object[] objects = SingletonDaoUtil.getListenGuidelineDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        for(ListenGuideLineEntity item : (List<ListenGuideLineEntity>)objects[1]) {
            ListenGuideLineDTO dto = ListenGuideLineBeanUtils.entity2Dto(item);
            result.add(dto);
        }
        objects[1] =result;
        return objects;

    }

    public ListenGuideLineDTO findByListenGuideLineId(String property,Integer listenGuidelineId) {
        ListenGuideLineEntity entity = SingletonDaoUtil.getListenGuidelineDaoInstance().findEqualUnique(property,listenGuidelineId);
        ListenGuideLineDTO dto = ListenGuideLineBeanUtils.entity2Dto(entity);
        return dto;
    }

    @Override
    public void saveListenGuideLine(ListenGuideLineDTO dto) throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(timestamp);
        ListenGuideLineEntity entity =ListenGuideLineBeanUtils.dto2Entity(dto);
        SingletonDaoUtil.getListenGuidelineDaoInstance().save(entity);
    }

    @Override
    public ListenGuideLineDTO updateListenGuideLine(ListenGuideLineDTO dto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(timestamp);
        ListenGuideLineEntity entity = ListenGuideLineBeanUtils.dto2Entity(dto);
        entity = SingletonDaoUtil.getListenGuidelineDaoInstance().update(entity);
        dto = ListenGuideLineBeanUtils.entity2Dto(entity);
        return dto;
    }

    @Override
    public Integer delete(List<Integer> ids) {
        Integer result = SingletonDaoUtil.getListenGuidelineDaoInstance().delete(ids);
        return result;
    }
}
