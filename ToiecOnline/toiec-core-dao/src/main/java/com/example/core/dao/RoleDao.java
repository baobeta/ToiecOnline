package com.example.core.dao;

import com.example.core.data.dao.GenericDao;
import com.example.core.persistence.entity.RoleEntity;

import java.util.List;

public interface RoleDao extends GenericDao<Integer, RoleEntity> {
    List<RoleEntity> findByRoles(List<String> roles);

}
