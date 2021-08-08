package com.example.toiec.core.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static SessionUtil sessionUtil = null;
    // Singleton
    public static SessionUtil getInstance() {
         if (sessionUtil == null) {
             sessionUtil = new SessionUtil();
         }
         return sessionUtil;
    }
    //import key and value in session
    public void putValue(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key,value);
    }
    //get key and value in session
    public Object getValue(HttpServletRequest request,String key) {
        return request.getSession().getAttribute(key);
    }
    //remove key and value in session
    public void remove(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }


}
