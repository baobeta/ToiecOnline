package com.example.core.service.impl;

import com.example.core.dao.UserDao;
import com.example.core.daoimpl.UserDaoImpl;
import com.example.core.dto.CheckLogin;
import com.example.core.dto.UserDTO;
import com.example.core.dto.UserImportDTO;
import com.example.core.persistence.entity.RoleEntity;
import com.example.core.persistence.entity.UserEntity;
import com.example.core.service.UserService;
import com.example.core.service.utils.SingletonDaoUtil;
import com.example.core.utils.UserBeanUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.*;

public class UserServiceImpl implements UserService {
//    public UserDTO isUserExist(UserDTO dto) {
//        UserEntity entity = SingletonDaoUtil.getUserDaoInstance().findUserByUsernameAndPassword(dto.getName(), dto.getPassword());
//        return UserBeanUtils.entity2Dto(entity);
//
//    }
//    public UserDTO findRoleByUser(UserDTO dto) {
//        UserEntity entity = SingletonDaoUtil.getUserDaoInstance().findUserByUsernameAndPassword(dto.getName(), dto.getPassword());
//        return UserBeanUtils.entity2Dto(entity);
//    }



    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        Object[] objects = SingletonDaoUtil.getUserDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for(UserEntity item : (List<UserEntity>)objects[1]) {
            UserDTO userDTO = UserBeanUtils.entity2Dto(item);
            userDTOs.add(userDTO);
        }
        objects[1]= userDTOs;
        return objects;
    }


    public UserDTO findById(Integer userId) {
        UserEntity entity = SingletonDaoUtil.getUserDaoInstance().findById(userId);
        UserDTO dto = UserBeanUtils.entity2Dto(entity);
        return dto;
    }


    public void saveUser(UserDTO userDTO) {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        userDTO.setCreatedDate(createdDate);
        UserEntity entity = UserBeanUtils.dto2Entity(userDTO);
        SingletonDaoUtil.getUserDaoInstance().save(entity);

    }


    public UserDTO updateUser(UserDTO userDTO) {
        UserEntity entity = UserBeanUtils.dto2Entity(userDTO);
        entity = SingletonDaoUtil.getUserDaoInstance().update(entity);
        userDTO = UserBeanUtils.entity2Dto(entity);
        return userDTO;
    }


    public CheckLogin checkLogin(String username, String password) {
        CheckLogin checkLogin = new CheckLogin();
        if(username !=null && password != null) {
            Object[] objects =SingletonDaoUtil.getUserDaoInstance().checkLogin(username, password);
            checkLogin.setUserExist( (Boolean) objects[0]);
            if (checkLogin.isUserExist()) {
                checkLogin.setRoleName(objects[1].toString());
            }
        }

        return checkLogin ;
    }

    public void validateImportUser(List<UserImportDTO> userImportDTOS) {
        List<String> names = new ArrayList<String>();
        List<String> roles = new ArrayList<String>();

        for(UserImportDTO item: userImportDTOS) {
            if (item.isValid()) {
                names.add(item.getUserName());
                if (!roles.contains(item.getRoleName())) {
                    roles.add(item.getRoleName());
                }
            }
        }

        Map<String, UserEntity> userEntityMap = new HashMap<String, UserEntity>();
        Map<String, RoleEntity> roleEntityMap = new HashMap<String, RoleEntity>();
        if (names.size() > 0) {
            List<UserEntity> userEntities = SingletonDaoUtil.getUserDaoInstance().findByUsers(names);
            for (UserEntity item: userEntities) {
                userEntityMap.put(item.getName().toUpperCase(), item);
            }
        }
        if (roles.size() > 0) {
            List<RoleEntity> roleEntities = SingletonDaoUtil.getRoleDaoInstance().findByRoles(roles);
            for (RoleEntity item: roleEntities) {
                roleEntityMap.put(item.getName().toUpperCase(), item);
            }
        }

        for (UserImportDTO item: userImportDTOS) {
            String message = item.getError();
            if (item.isValid()) {
                UserEntity userEntity = userEntityMap.get(item.getUserName().toUpperCase());
                if (userEntity != null) {
                    message += "<br/>";
                    message += "tên đăng nhập tồn tại";
                }

                RoleEntity roleEntity = roleEntityMap.get(item.getRoleName().toUpperCase());
                if (roleEntity == null) {
                    message += "<br/>";
                    message += "Vai trò không tồn tại";
                }
                if (StringUtils.isNotBlank(message)) {
                    item.setValid(false);
                    item.setError(message.substring(5));

                }
            }
        }
    }

    @Override
    public void saveUserImport(List<UserImportDTO> userImportDTOS) {
        for (UserImportDTO item: userImportDTOS) {
            if(item.isValid()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(item.getUserName());
                userEntity.setPassword(item.getPassword());
                userEntity.setFullName(item.getFullName());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                userEntity.setCreatedDate(timestamp);
                RoleEntity roleEntity =SingletonDaoUtil.getRoleDaoInstance().findEqualUnique("name",item.getRoleName().toUpperCase());
                userEntity.setRoleEntity(roleEntity);
                SingletonDaoUtil.getUserDaoInstance().save(userEntity);

            }
        }
    }


}

