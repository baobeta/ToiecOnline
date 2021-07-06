package com.example.core.service.impl;

import com.example.core.dao.UserDao;
import com.example.core.daoimpl.UserDaoImpl;
import com.example.core.dto.UserDTO;
import com.example.core.persistence.entity.UserEntity;
import com.example.core.service.UserService;
import com.example.core.utils.UserBeanUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        UserDao userDao = new UserDaoImpl() ;
        Object[] objects = userDao.findByProperty(property, sortExpression, sortDirection, offset, limit);
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for(UserEntity item : (List<UserEntity>)objects[1]) {
            UserDTO userDTO = UserBeanUtils.entity2Dto(item);
            userDTOs.add(userDTO);
        }
        objects[1]= userDTOs;
        return objects;
    }

    @Override
    public UserDTO findById(Integer userId) {
        UserDao userDao = new UserDaoImpl() ;
        UserEntity entity = userDao.findById(userId);
        UserDTO dto = UserBeanUtils.entity2Dto(entity);
        return dto;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        UserDao userDao = new UserDaoImpl() ;
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        userDTO.setCreatedDate(createdDate);
        UserEntity entity = UserBeanUtils.dto2Entity(userDTO);
        userDao.save(entity);

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        UserDao userDao = new UserDaoImpl() ;
        UserEntity entity = UserBeanUtils.dto2Entity(userDTO);
        entity = userDao.update(entity);
        userDTO = UserBeanUtils.entity2Dto(entity);
        return userDTO;
    }
}
