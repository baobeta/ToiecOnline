package com.example.controller.admin;

import com.example.command.ListenGuilineCommand;
import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.service.ListenGuideLineService;
import com.example.core.service.impl.ListenGuideLineServiceImpl;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.RequestUtil;
import com.example.core.wed.utils.SingletonServiceUtil;
import com.example.toiec.core.common.utils.UploadUtil;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

//@WebServlet("/admin-guiline-listen-list.html")
@WebServlet(urlPatterns = {"/admin-guideline-listen-list.html","/admin-guideline-listen-edit.html"})
public class ListenGuilineController extends HttpServlet {

    private final Logger log = Logger.getLogger(String.valueOf(this.getClass()));

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          ListenGuilineCommand command =FormUtil.populate(ListenGuilineCommand.class,req);
          ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
          HttpSession session = req.getSession();
          command.setMaxPageItems(2);
          command.setTotalItems(0);
        executeSearchListenGuide(req,command);

//        req.setAttribute(WebConstant.ALERT,WebConstant.TYPE_SUCCESS);
//        req.setAttribute(WebConstant.MESSAGE_RESPONSE,resourceBundle.getString("label.guideline.listen.add.success"));
//        if(session != null) {
//            req.setAttribute(WebConstant.ALERT,session.getAttribute(WebConstant.ALERT));
//            req.setAttribute(WebConstant.MESSAGE_RESPONSE,session.getAttribute(WebConstant.MESSAGE_RESPONSE));
//        }

        if(command.getUrlType()!=null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            req.setAttribute(WebConstant.LIST_ITEM,command);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/admin/listenguideline/list.jsp");
            requestDispatcher.forward(req,resp);
        }
        else if(command.getUrlType()!=null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            req.setAttribute(WebConstant.FROM_ITEM,command);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/admin/listenguideline/edit.jsp");
            requestDispatcher.forward(req,resp);
        }
//        session.removeAttribute(WebConstant.ALERT);
//        session.removeAttribute(WebConstant.MESSAGE_RESPONSE);


    }

    private void executeSearchListenGuide(HttpServletRequest req, ListenGuilineCommand command) {
        Map<String, Object> properties = buildMapProperty(command);
        RequestUtil.initSearchBean(req,command);
        Object[] objects = SingletonServiceUtil.getListenGuideLineServiceInstance().findListenGuideLineByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ListenGuideLineDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt( objects[0].toString()));

    }

    private Map<String, Object> buildMapProperty(ListenGuilineCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(command.getPojo().getTitle())) {
            properties.put("title",command.getPojo().getTitle());
        }
        return  properties;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuilineCommand command = new ListenGuilineCommand();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
        UploadUtil uploadUtil = new UploadUtil();
        HttpSession session = req.getSession();
      //  Set<String> valueTitle = buildSetValueListenGuideLine();
        try {
    //        Object[] objects = uploadUtil.writeOrUpdateFile(req,valueTitle,WebConstant.LISTENGUIDELINE);

    //        Map<String, String> mapValue = (Map<String, String>) objects[3];
          //  command = returnValueListenGuidelineCommand(valueTitle,command,mapValue);
            session.setAttribute(WebConstant.ALERT,WebConstant.TYPE_SUCCESS);
            session.setAttribute(WebConstant.MESSAGE_RESPONSE,resourceBundle.getString("label.guideline.listen.add.success"));
//        } catch (FileUploadException e) {
//
//            log.error(e.getMessage(),e);
//            session.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
//            session.setAttribute(WebConstant.MESSAGE_RESPONSE,resourceBundle.getString("label.error"));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            session.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
            session.setAttribute(WebConstant.MESSAGE_RESPONSE,resourceBundle.getString("label.error"));
        }
        resp.sendRedirect("/toiec_web_war_exploded/admin-guideline-listen-list.html?urlType=url_list");

    }

//    private ListenGuilineCommand returnValueListenGuidelineCommand(Set<String> valueTitle, ListenGuilineCommand command, Map<String, String> mapValue) {
//        for (String item :valueTitle) {
//            if(mapValue.containsKey(item)) {
//                if(item.equals("pojo.title")) {
//                    command.getPojo().setTitle(mapValue.get(item));
//                } else if(item.equals("pojo.context")) {
//                    command.getPojo().setContext(mapValue.get(item));
//
//                }
//            }
//        }
//        return command;
//    }
//
//    private Set<String> buildSetValueListenGuideLine() {
//        Set<String> returnValue = new HashSet<String>();
//        returnValue.add("pojo.title");
//        returnValue.add("pojo.context");
//        return returnValue;
//    }
}
