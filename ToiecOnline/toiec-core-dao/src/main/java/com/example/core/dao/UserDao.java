package com.example.core.dao;

import com.example.core.data.dao.GenericDao;
import com.example.core.persistence.entity.RoleEntity;
import com.example.core.persistence.entity.UserEntity;

import java.util.List;

public interface UserDao  extends GenericDao<Integer, UserEntity> {
//    UserEntity findUserByUsernameAndPassword(String name , String password);
    Object[] checkLogin(String username, String password) ;
    List<UserEntity> findByUsers(List<String> names);
}
