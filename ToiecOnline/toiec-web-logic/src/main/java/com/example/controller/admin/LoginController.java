package com.example.controller.admin;


import com.example.command.UserCommand;
import com.example.core.dto.CheckLogin;
import com.example.core.dto.UserDTO;
import com.example.core.service.UserService;
import com.example.core.service.impl.UserServiceImpl;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.SingletonServiceUtil;
import com.example.toiec.core.common.utils.SessionUtil;
import org.apache.log4j.Logger;

import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
//import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/logout.html","/login.html"})
public class LoginController extends HttpServlet {
    private final Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    Locale locale;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        try {
            //Check action login or logout
            String action = request.getParameter("action");
            if(action.equals(WebConstant.LOGIN)) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/web/login.jsp");
                requestDispatcher.forward(request,response);
            } else if (action.equals(WebConstant.LOGOUT)) {
                SessionUtil.getInstance().remove(request,WebConstant.LOGIN_NAME);
                response.sendRedirect("/home.html");
            }

        } catch( NullPointerException e) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/web/login.jsp");
            requestDispatcher.forward(request,response);

        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
       UserCommand command = FormUtil.populate(UserCommand.class,request);
       UserDTO pojo = command.getPojo();
       if(pojo!= null) {
           // check login
           CheckLogin checkLogin = SingletonServiceUtil
                                    .getUserServiceInstance()
                                    .checkLogin(pojo.getName(), pojo.getPassword());
           // if login exists
           if(checkLogin.isUserExist()) {
               // Put name
               SessionUtil.getInstance().putValue(request,WebConstant.LOGIN_NAME,pojo.getName());
               // Decentralization
               if(checkLogin.getRoleName().equals(WebConstant.ROLE_ADMIN)) {
                   response.sendRedirect("/admin-home.html");
               }
               else if(checkLogin.getRoleName().equals(WebConstant.ROLE_USER)) {
                   response.sendRedirect("/home.html");
               }
           }
           // Login wrong
           else {
               request.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
               request.setAttribute(WebConstant.MESSAGE_RESPONSE,resourceBundle.getString("label.name.password.wong "));
               RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/web/login.jsp");
               requestDispatcher.forward(request,response);
           }
       }

    }
}




















//        try {
//            if(SingletonServiceUtil.getUserServiceInstance().isUserExist(pojo)!=null) {
//                if(SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO()!=null) {
//                    if(SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_ADMIN)) {
//                        response.sendRedirect("admin-home.html");
//                    } else if((SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_USER))) {
//                        response.sendRedirect("home.html");
//                    }
//                }
//            }
//
//        } catch (NoResultException e)
//        {
//            log(e.getMessage(),e);
//            request.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
//            request.setAttribute(WebConstant.MESSAGE_RESPONSE,"Tên hoặc mật khẩu sai");
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/web/login.jsp");
//            requestDispatcher.forward(request,response);
//        }


