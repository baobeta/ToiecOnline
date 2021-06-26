package com.example.core.service;

import com.example.core.dto.UserDTO;
import com.example.core.persistence.entity.UserEntity;

public interface UserService {
    UserDTO isUserExist(UserDTO dto);
    UserDTO findRoleByUser(UserDTO dto);
}
