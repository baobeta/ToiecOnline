package com.example.core.utils;

import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.persistence.entity.ListenGuideLineEntity;

public class ListenGuideLineBeanUtils {
    public static ListenGuideLineDTO entity2Dto(ListenGuideLineEntity entity) {
        ListenGuideLineDTO dto = new ListenGuideLineDTO();
        dto.setListenGuidelineId(entity.getListenGuidelineId());
        dto.setTitle(entity.getTitle());
        dto.setContext(entity.getContext());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setImage(entity.getImage());

        return dto;

    }
    public static ListenGuideLineEntity dto2Entity(ListenGuideLineDTO dto) {
        ListenGuideLineEntity entity = new ListenGuideLineEntity();
        entity.setListenGuidelineId(dto.getListenGuidelineId());
        entity.setTitle(dto.getTitle());
        entity.setContext(dto.getContext());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setImage(dto.getImage());
        return entity;

    }
}
