package com.example.core.service.utils;

import com.example.core.daoimpl.ListenGuideLineImpl;
import com.example.core.daoimpl.RoleDaoImpl;
import com.example.core.daoimpl.UserDaoImpl;

public class SingletonDaoUtil {
    private static UserDaoImpl userDaoImpl = null;
    private static RoleDaoImpl roleDaoImpl = null;
    private static ListenGuideLineImpl listenGuideLineDaoImpl = null;
    public static  UserDaoImpl getUserDaoInstance() {
        if(userDaoImpl == null) {
            userDaoImpl = new UserDaoImpl();

        }
        return userDaoImpl;
    }

    public static  RoleDaoImpl getRoleDaoInstance() {
        if(roleDaoImpl == null) {
            roleDaoImpl = new RoleDaoImpl();

        }
        return roleDaoImpl;
    }

    public static  ListenGuideLineImpl getListenGuidelineDaoInstance() {
        if(listenGuideLineDaoImpl == null) {
            listenGuideLineDaoImpl = new ListenGuideLineImpl();

        }
        return listenGuideLineDaoImpl;
    }


}
