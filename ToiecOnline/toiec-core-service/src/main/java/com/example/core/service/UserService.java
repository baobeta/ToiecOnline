package com.example.core.service;

import com.example.core.dto.UserDTO;
import com.example.core.persistence.entity.UserEntity;

import java.util.Map;

public interface UserService {
    UserDTO isUserExist(UserDTO dto);
    UserDTO findRoleByUser(UserDTO dto);
    Object[] findByProperty(Map<String,Object> property, String sortExpression, String sortDirection,Integer offset, Integer limit);
    UserDTO findById(Integer userId);
}
