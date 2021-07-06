package com.example.core.wed.utils;

import com.example.core.service.impl.ListenGuideLineServiceImpl;
import com.example.core.service.impl.RoleServiceImpl;
import com.example.core.service.impl.UserServiceImpl;

public class SingletonServiceUtil {
    private static UserServiceImpl userServiceImpl = null;
    private static RoleServiceImpl roleServiceImpl = null;
    private  static ListenGuideLineServiceImpl listenGuideLineServiceImpl = null;

    public static UserServiceImpl getUserServiceInstance() {
        if(userServiceImpl==null) {
            userServiceImpl = new UserServiceImpl();
        }
        return userServiceImpl;
    }
    public static RoleServiceImpl getRoleServiceInstance() {
        if(roleServiceImpl==null) {
            roleServiceImpl = new RoleServiceImpl();
        }
        return roleServiceImpl;
    }
    public static ListenGuideLineServiceImpl getListenGuideLineServiceInstance() {
        if(listenGuideLineServiceImpl == null) {
            listenGuideLineServiceImpl = new ListenGuideLineServiceImpl();
        }
        return listenGuideLineServiceImpl;
    }

}
