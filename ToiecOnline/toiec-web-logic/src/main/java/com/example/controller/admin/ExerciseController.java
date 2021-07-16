package com.example.controller.admin;

import com.example.core.wed.common.WebConstant;
import com.example.toiec.core.common.utils.UploadUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/admin-exercise-upload.html")
public class ExerciseController extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/admin/exercise/upload.jsp");
        requestDispatcher.forward(request,response);


    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        UploadUtil uploadUtil = new UploadUtil();
        Set<String> valueTitle = new HashSet<String>();
        uploadUtil.writeOrUpdateFile(request,valueTitle, WebConstant.EXERCISE);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/admin/exercise/upload.jsp");
        requestDispatcher.forward(request,response);


    }

}
