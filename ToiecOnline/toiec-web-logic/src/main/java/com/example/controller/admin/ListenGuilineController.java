package com.example.controller.admin;

import com.example.command.ListenGuilineCommand;
import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.RequestUtil;
import com.example.core.wed.utils.SingletonServiceUtil;
import com.example.core.wed.utils.WebCommonUtil;
import com.example.toiec.core.common.utils.UploadUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
@WebServlet(urlPatterns = {"/admin-guideline-listen-list.html","/admin-guideline-listen-edit.html"})
public class ListenGuilineController extends HttpServlet {

    private final Logger log = Logger.getLogger(String.valueOf(this.getClass()));

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuilineCommand command =FormUtil.populate(ListenGuilineCommand.class,req);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
        // check type list
        if(command.getUrlType()!=null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            //check action if is action delete
            if(command.getCrudaction()!=null
                    &&command.getCrudaction().equals(WebConstant.REDIRECT_DELETE)) {
                List<Integer> ids =new ArrayList<Integer>();
                //get list ids
                for(String item: command.getCheckList()) {
                    ids.add(Integer.parseInt(item));
                }
                // delete
                Integer result =SingletonServiceUtil
                        .getListenGuideLineServiceInstance()
                        .delete(ids);
                //check count
                if(result != ids.size()) {
                    command.setCrudaction(WebConstant.REDIRECT_ERROR);
                }

            }
            // searching
            executeSearchListenGuide(req,command);
            // build Message
            if(command.getCrudaction()!=null) {
                Map<String, String> mapMessage = buildMapRedirectMessage(resourceBundle);
                WebCommonUtil.addRedirectMessage(req, command.getCrudaction(), mapMessage);
            }
            req.setAttribute(WebConstant.LIST_ITEM,command);
            // view page list
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/admin/listenguideline/list.jsp");
            requestDispatcher.forward(req,resp);
        }
        // type edit
        else if(command.getUrlType()!=null
                && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if(command.getPojo()!=null
                    && command.getPojo().getListenGuidelineId()!=null) {
                // find pojo by ID
                command.setPojo(SingletonServiceUtil
                        .getListenGuideLineServiceInstance()
                        .findByListenGuideLineId("listenGuidelineId",command.getPojo().getListenGuidelineId()));
            }
            req.setAttribute(WebConstant.FROM_ITEM,command);
            //view page edit
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/admin/listenguideline/edit.jsp");
            requestDispatcher.forward(req,resp);
        }
}

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuilineCommand command = new ListenGuilineCommand();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
        UploadUtil uploadUtil = new UploadUtil();
        //set value need get
        Set<String> valueTitle = buildSetValueListenGuideLine();
        // Save file
        Object[] objects = uploadUtil.writeOrUpdateFile(req,valueTitle,WebConstant.LISTENGUIDELINE);
        // Check file is write
        boolean checkStatusUploadImage = (boolean) objects[0];
        if(!checkStatusUploadImage) {
            resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
        } else {
            ListenGuideLineDTO dto = command.getPojo();
            //Set file name image in pojo
            if(StringUtils.isNotBlank(objects[2].toString())) {
                dto.setImage(objects[2].toString());
            }
            // Get value in form
            Map<String, String> mapValue = (Map<String, String>) objects[3];
            dto = returnValueOfDTO(dto,mapValue);
            if(dto!=null) {
                // update
                if(dto.getListenGuidelineId()!=null) {
                    ListenGuideLineDTO listenGuideLineDTO = SingletonServiceUtil
                                                            .getListenGuideLineServiceInstance()
                                                            .findByListenGuideLineId("listenGuidelineId", dto.getListenGuidelineId());
                    if(dto.getImage()==null) {
                        dto.setImage(listenGuideLineDTO.getImage());
                    }
                    dto.setCreatedDate(listenGuideLineDTO.getCreatedDate());
                    ListenGuideLineDTO result = SingletonServiceUtil
                                                .getListenGuideLineServiceInstance()
                                                .updateListenGuideLine(dto);
                    if(result!=null) {
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_update");
                    } else {
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
                    }
                    // insert
                } else {
                    try {
                        SingletonServiceUtil.getListenGuideLineServiceInstance()
                                            .saveListenGuideLine(dto);
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_insert");
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
                    }
                }
            }


        }
//        Map<String, String> mapValue = (Map<String, String>) objects[3];
//        command = returnValueListenGuidelineCommand(valueTitle,command,mapValue);
//        resp.sendRedirect("admin-guideline-listen-list.html?urlType=url_list");

    }

    private ListenGuideLineDTO returnValueOfDTO(ListenGuideLineDTO dto ,Map<String, String> mapValue) {
        for(Map.Entry<String,String> item : mapValue.entrySet() ) {
            if(item.getKey().equals("pojo.title")) {
                dto.setTitle(item.getValue());
            }
            else if(item.getKey().equals("pojo.context")) {
                dto.setContext(item.getValue());
            }
            else if(item.getKey().equals("pojo.listenGuidelineId")) {
                dto.setListenGuidelineId(Integer.parseInt(item.getValue()));
            }
        }
        return dto;
    }
//
    private Set<String> buildSetValueListenGuideLine() {
        Set<String> returnValue = new HashSet<String>();
        returnValue.add("pojo.title");
        returnValue.add("pojo.context");
        returnValue.add("pojo.listenGuidelineId");
        return returnValue;
    }
    private Map<String, String> buildMapRedirectMessage(ResourceBundle resourceBundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT,resourceBundle.getString("label.listenguideline.add.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE,resourceBundle.getString("label.listenguideline.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE,resourceBundle.getString("label.listenguideline.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR,resourceBundle.getString("label.messages.error"));
        return mapMessage;
    }

    private void executeSearchListenGuide(HttpServletRequest req, ListenGuilineCommand command) {
        Map<String, Object> properties = buildMapProperty(command);
        RequestUtil.initSearchBean(req,command);
        Object[] objects = SingletonServiceUtil.getListenGuideLineServiceInstance()
                .findListenGuideLineByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
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
}
