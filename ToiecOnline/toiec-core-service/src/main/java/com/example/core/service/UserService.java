package com.example.core.service;

import com.example.core.dto.CheckLogin;
import com.example.core.dto.UserDTO;
import com.example.core.dto.UserImportDTO;
import com.example.core.persistence.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
//    UserDTO isUserExist(UserDTO dto);
//    UserDTO findRoleByUser(UserDTO dto);

    Object[] findByProperty(Map<String,Object> property, String sortExpression, String sortDirection,Integer offset, Integer limit);
    UserDTO findById(Integer userId);
    void saveUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    CheckLogin checkLogin(String username, String password);
    void validateImportUser(List<UserImportDTO> userImportDTOS);
    void saveUserImport(List<UserImportDTO> userImportDTOS);
}
