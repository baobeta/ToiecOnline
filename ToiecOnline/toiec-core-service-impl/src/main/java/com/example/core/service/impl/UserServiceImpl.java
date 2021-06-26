package com.example.core.service.impl;

import com.example.core.dao.UserDao;
import com.example.core.daoimpl.UserDaoImpl;
import com.example.core.dto.UserDTO;
import com.example.core.persistence.entity.UserEntity;
import com.example.core.service.UserService;
import com.example.core.utils.UserBeanUtils;

public class UserServiceImpl implements UserService {
    public UserDTO isUserExist(UserDTO dto) {
        UserDao userDao = new UserDaoImpl() ;
        UserEntity entity = userDao.findUserByUsernameAndPassword(dto.getName(), dto.getPassword());
        return UserBeanUtils.entity2Dto(entity);

    }
    public UserDTO findRoleByUser(UserDTO dto) {
        UserDao userDao = new UserDaoImpl() ;
        UserEntity entity = userDao.findUserByUsernameAndPassword(dto.getName(), dto.getPassword());
        return UserBeanUtils.entity2Dto(entity);
    }
}
