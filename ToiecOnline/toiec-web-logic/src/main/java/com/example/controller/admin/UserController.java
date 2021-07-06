package com.example.controller.admin;

import antlr.Tool;
import com.example.command.UserCommand;
import com.example.core.dto.RoleDTO;
import com.example.core.dto.UserDTO;
import com.example.core.service.RoleService;
import com.example.core.service.UserService;
import com.example.core.service.impl.RoleServiceImpl;
import com.example.core.service.impl.UserServiceImpl;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.WebCommonUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


@WebServlet(urlPatterns = {"/admin-user-list.html","/ajax-admin-user-edit.html"})
public class UserController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    UserService userService = new UserServiceImpl();
    RoleService roleService = new RoleServiceImpl();
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        UserCommand command = FormUtil.populate(UserCommand.class,request);
        UserDTO pojo = command.getPojo();
        if(command.getUrlType()!= null && command.getUrlType().equals(WebConstant.URL_LIST))
        {
            Map<String, Object> map = new HashMap<String, Object>();
            Object[] objects = userService.findByProperty(map,command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
            command.setListResult((List<UserDTO>) objects[1]);
            command.setTotalItems(Integer.parseInt(objects[0].toString()));
            request.setAttribute(WebConstant.LIST_ITEM, command);
            if(command.getCrudaction() != null ) {
               Map<String, String> mapMessage = buildMapMessage();
               WebCommonUtil.addRedirectMessage(request,command.getCrudaction(),mapMessage);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/list.jsp");
            requestDispatcher.forward(request,response);
        } else if(command.getUrlType()!= null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if(pojo != null && pojo.getUserId() != null) {
                command.setPojo(userService.findById(pojo.getUserId()));
            }
            command.setRoles(roleService.findAll());
            request.setAttribute(WebConstant.FROM_ITEM,command);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/edit.jsp");
            requestDispatcher.forward(request,response);

        }



    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        try {
            UserCommand command = FormUtil.populate(UserCommand.class,request);
            UserDTO pojo = command.getPojo();
            if(command.getUrlType()!= null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
                if(command.getCrudaction() != null &&command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setRoleId(command.getRoleId());
                    pojo.setRoleDTO(roleDTO);

                    if(pojo != null && pojo.getUserId()!= null) {
                        //updateUser
                        userService.updateUser(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_UPDATE);
                    } else {
                        //saveUser
                        userService.saveUser(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_INSERT);
                    }

                }
            }

        } catch (Exception e) {
            log(e.getMessage(),e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_ERROR);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/edit.jsp");
        requestDispatcher.forward(request,response);



    }


    private Map<String, String> buildMapMessage() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT,resourceBundle.getString("label.user.messages.add.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE,resourceBundle.getString("label.user.messages.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE,resourceBundle.getString("label.user.messages.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR,resourceBundle.getString("label.messages.error"));
        return mapMessage;
    }
}
