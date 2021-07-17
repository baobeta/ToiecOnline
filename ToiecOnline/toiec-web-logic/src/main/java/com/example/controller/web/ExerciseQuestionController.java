package com.example.controller.web;

import com.example.command.ExerciseCommand;
import com.example.command.ExerciseQuestionCommand;
import com.example.core.dto.ExerciseQuestionDTO;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.RequestUtil;
import com.example.core.wed.utils.SingletonServiceUtil;

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

@WebServlet(urlPatterns={"/bai-tap-thuc-hanh.html","/ajax-bai-tap-dap-an.html"})
public class ExerciseQuestionController extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        ExerciseQuestionCommand command = FormUtil.populate(ExerciseQuestionCommand.class, request);
        getListExerciseQuestion(command);
        request.setAttribute(WebConstant.LIST_ITEM, command);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/exercise/detail.jsp");
        rd.forward(request, response);

    }

    private void getListExerciseQuestion( ExerciseQuestionCommand  command) {
        Map<String, Object> properties = buildMap(command);
        command.setMaxPageItems(1);
        RequestUtil.initSearchBeanManual(command);
        Object[] objects = SingletonServiceUtil.getExerciseQuestionServiceInstance().findExerciseQuestionByProperties(properties, command.getSortExpression(),
                command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ExerciseQuestionDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPages((int) Math.ceil((double) command.getTotalItems() / command.getMaxPageItems()));

    }

    private Map<String, Object> buildMap(ExerciseQuestionCommand command) {
        ExerciseQuestionDTO pojo = command.getPojo();
        Map<String,Object> result = new HashMap<String, Object>();
        if(command.getExerciseId()!=null) {
            result.put("exerciseId",command.getExerciseId());
        }
        return result;
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ExerciseQuestionCommand command = FormUtil.populate(ExerciseQuestionCommand.class, request);
        getListExerciseQuestion(command);
        for (ExerciseQuestionDTO item: command.getListResult()) {
            if (!command.getAnswerUser().equals(item.getCorrectAnswer())) {
                command.setCheckAnswer(true);
            }
        }
        request.setAttribute(WebConstant.LIST_ITEM , command);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/exercise/result.jsp");
        rd.forward(request, response);

    }
}
