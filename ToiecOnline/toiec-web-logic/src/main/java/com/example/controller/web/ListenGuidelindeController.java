package com.example.controller.web;

import com.example.command.ListenGuilineCommand;
import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.RequestUtil;
import com.example.core.wed.utils.SingletonServiceUtil;
import org.apache.commons.lang.StringUtils;

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

@WebServlet(urlPatterns={"/danh-sach-huong-dan-nghe.html","/noi-dung-bai-huong-dan-nghe.html"})
public class ListenGuidelindeController extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        ListenGuilineCommand command = FormUtil.populate(ListenGuilineCommand.class,request);
        if(request.getParameter("listenguidelineid")!=null) {
            String listenGuideLineIdStr = request.getParameter("listenguidelineid");
            ListenGuideLineDTO existListGuideLine =SingletonServiceUtil
                    .getListenGuideLineServiceInstance()
                    .findByListenGuideLineId("listenGuidelineId",Integer.parseInt(listenGuideLineIdStr));
            command.setPojo(existListGuideLine);
            request.setAttribute(WebConstant.FROM_ITEM,command);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/web/listenguideline/detail.jsp");
            requestDispatcher.forward(request,response);
        } else {
            executeSearchListenGuide(request,command);
            request.setAttribute(WebConstant.LIST_ITEM,command);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/web/listenguideline/list.jsp");
            requestDispatcher.forward(request,response);
        }
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

    }
    private void executeSearchListenGuide(HttpServletRequest request, ListenGuilineCommand command) {
        Map<String, Object> properties = buildMapProperty(command);
        command.setMaxPageItems(3);
        RequestUtil.initSearchBeanManual(command);
        Object[] objects =SingletonServiceUtil.getListenGuideLineServiceInstance()
                         .findListenGuideLineByProperties(properties, command.getSortExpression()
                        , command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ListenGuideLineDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPages((int) Math.ceil((double) command.getTotalItems() / command.getMaxPageItems()) );

    }

    private Map<String, Object> buildMapProperty(ListenGuilineCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(command.getPojo().getTitle())) {
            properties.put("title",command.getPojo().getTitle());
        }
        return  properties;
    }

}
