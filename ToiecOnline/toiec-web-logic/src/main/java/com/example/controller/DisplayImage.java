package com.example.controller;

import com.example.toiec.core.common.constant.CoreConstant;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DisplayImage extends HttpServlet {
    private  final String imageBase="/"+ CoreConstant.FOLDER_UPLOAD;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException, IOException {
        String imageUrl = request.getRequestURI();
        String relativeImagePath = imageUrl.substring("/repository/".length());
        ServletOutputStream outputStream;
        outputStream = reponse.getOutputStream();
        FileInputStream fin = new FileInputStream(imageBase+ File.separator+relativeImagePath);
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(outputStream);
        int ch =0;
        while ((ch = bin.read())!=-1) {
            bout.write(ch);
        }
        bin.close();
        fin.close();
        bout.close();
        outputStream.close();



    }
}
