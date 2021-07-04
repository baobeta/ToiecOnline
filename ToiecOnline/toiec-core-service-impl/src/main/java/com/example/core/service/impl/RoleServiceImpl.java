package com.example.core.service.impl;

import com.example.core.dao.RoleDao;
import com.example.core.daoimpl.RoleDaoImpl;
import com.example.core.dto.RoleDTO;
import com.example.core.persistence.entity.RoleEntity;
import com.example.core.service.RoleService;
import com.example.core.utils.RoleBeanUtil;
import com.example.core.utils.UserBeanUtils;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    RoleDao roleDao = new RoleDaoImpl();
    @Override
    public List<RoleDTO> findAll() {

        List<RoleEntity> entity = roleDao.findAll();
        List<RoleDTO> dtos = new ArrayList<RoleDTO>();
        for (RoleEntity item : entity) {
            RoleDTO dto = RoleBeanUtil.entity2Dto(item);
            dtos.add(dto);
        }
        return dtos;
    }
}
