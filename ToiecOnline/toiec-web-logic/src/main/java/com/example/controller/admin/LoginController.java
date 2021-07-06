package com.example.controller.admin;


import com.example.command.UserCommand;
import com.example.core.dto.UserDTO;
import com.example.core.service.UserService;
import com.example.core.service.impl.UserServiceImpl;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.SingletonServiceUtil;

import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.util.logging.Logger;

@WebServlet("/login.html")
public class LoginController extends HttpServlet {
    //private final  Logger log = Logger.getLogger(String.valueOf(this.getClass()));

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/web/login.jsp");
        requestDispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
       UserCommand command = FormUtil.populate(UserCommand.class,request);
       UserDTO pojo = command.getPojo();
        try {
            if(SingletonServiceUtil.getUserServiceInstance().isUserExist(pojo)!=null) {
                if(SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO()!=null) {
                    if(SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_ADMIN)) {
                        response.sendRedirect("/toiec_web_war_exploded/admin-home.html");
                    } else if((SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_USER))) {
                        response.sendRedirect("/toiec_web_war_exploded/home.html");
                    }
                }
            }

        } catch (NoResultException e)
        {
            log(e.getMessage(),e);
            request.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE,"Tên hoặc mật khẩu sai");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/web/login.jsp");
            requestDispatcher.forward(request,response);
        }


    }
}
