package com.example.controller.web;


import com.example.command.ExaminationCommand;
import com.example.command.ExaminationCommand;
import com.example.core.dto.ExaminationDTO;
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

@WebServlet(urlPatterns={"/danh-sach-bai-thi.html"})
public class ExaminationController extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        ExaminationCommand command = FormUtil.populate(ExaminationCommand.class,request);
        executeSearchExamination(request,command);
        request.setAttribute(WebConstant.LIST_ITEM,command);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/web/examination/list.jsp");
        requestDispatcher.forward(request,response);
    }

    private void executeSearchExamination(HttpServletRequest request, ExaminationCommand command) {
        Map<String, Object> properties = buildMapProperty(command);
        command.setMaxPageItems(3);
        RequestUtil.initSearchBeanManual(command);
        Object[] objects = SingletonServiceUtil.getExaminationServiceInstance().findExaminationByProperties(properties, command.getSortExpression()
                , command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ExaminationDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPages((int) Math.ceil((double) command.getTotalItems() / command.getMaxPageItems()) );
    }

    private Map<String, Object> buildMapProperty(ExaminationCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(command.getPojo().getName())) {
            properties.put("name",command.getPojo().getName());
        }
        return  properties;

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

    }
}
