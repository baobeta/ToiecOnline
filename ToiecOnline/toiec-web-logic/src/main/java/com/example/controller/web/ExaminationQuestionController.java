package com.example.controller.web;

import com.example.command.ExaminationQuestionCommand;
import com.example.core.dto.ExaminationQuestionDTO;
import com.example.core.dto.ExerciseQuestionDTO;
import com.example.core.dto.ResultDTO;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.SingletonServiceUtil;
import com.example.toiec.core.common.utils.SessionUtil;

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

@WebServlet(urlPatterns={"/bai-thi-thuc-hanh.html","/bai-thi-dap-an.html"})
public class ExaminationQuestionController extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        ExaminationQuestionCommand command = FormUtil.populate(ExaminationQuestionCommand.class,request);
        getExaminationQuestions(command);
        request.setAttribute(WebConstant.LIST_ITEM,command);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/web/examination/detail.jsp");
        requestDispatcher.forward(request,response);


    }

    private void getExaminationQuestions(ExaminationQuestionCommand command) {
        Map<String, Object> properties =buildMapProperty(command);
        Object[] objects = SingletonServiceUtil.getExaminationQuestionServiceInstance().findExaminationQuestionByProperties(new HashMap<String,Object>(), command.getSortExpression(), command.getSortDirection(), null,null);
        command.setListResult((List<ExaminationQuestionDTO>) objects[1]);
    }

    private Map<String, Object> buildMapProperty(ExaminationQuestionCommand command) {
        ExaminationQuestionDTO pojo = command.getPojo();
        Map<String,Object> result = new HashMap<String, Object>();
        if(command.getExaminationId() != null) {
            result.put("examinationId",command.getExaminationId());
        }
        return result;
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ExaminationQuestionCommand command = new ExaminationQuestionCommand();
        Integer examinationId = Integer.parseInt(request.getParameter("examinationId"));
        command.setExaminationId(examinationId);
        getExaminationQuestions(command);
        for (ExaminationQuestionDTO item: command.getListResult()) {
            if (request.getParameter("answerUser["+item.getExaminationQuestionId()+"]") != null) {
                item.setAnswerUser(request.getParameter("answerUser["+item.getExaminationQuestionId()+"]"));
            }
        }
        String userName = (String) SessionUtil.getInstance().getValue(request, WebConstant.LOGIN_NAME);
        ResultDTO resultDTO = SingletonServiceUtil.getResultServiceInstance().saveResult(userName, examinationId, command.getListResult());
        command.setReadingScore(resultDTO.getReadingScore());
        command.setListenScore(resultDTO.getListenScore());
        request.setAttribute(WebConstant.LIST_ITEM, command);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/examination/result.jsp");
        rd.forward(request, response);

    }
}
