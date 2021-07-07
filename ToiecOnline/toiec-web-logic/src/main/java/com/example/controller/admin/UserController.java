package com.example.controller.admin;

import antlr.Tool;
import com.example.command.UserCommand;
import com.example.core.dto.RoleDTO;
import com.example.core.dto.UserDTO;
import com.example.core.dto.UserImportDTO;
import com.example.core.service.RoleService;
import com.example.core.service.UserService;
import com.example.core.service.impl.RoleServiceImpl;
import com.example.core.service.impl.UserServiceImpl;
import com.example.core.wed.common.WebConstant;
import com.example.core.wed.utils.FormUtil;
import com.example.core.wed.utils.SingletonServiceUtil;
import com.example.core.wed.utils.WebCommonUtil;
import com.example.toiec.core.common.utils.ExcelPoiUtil;
import com.example.toiec.core.common.utils.UploadUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


@WebServlet(urlPatterns = {"/admin-user-list.html","/ajax-admin-user-edit.html",
        "/admin-user-import.html","/admin-user-import-validate.html"})
public class UserController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final String SHOW_IMPORT_USER ="show_import_user";
    private final String READ_EXCEL ="read_excel";
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        UserCommand command = FormUtil.populate(UserCommand.class,request);
        UserDTO pojo = command.getPojo();
        if(command.getUrlType()!= null && command.getUrlType().equals(WebConstant.URL_LIST))
        {
            Map<String, Object> map = new HashMap<String, Object>();
            Object[] objects = SingletonServiceUtil.getUserServiceInstance().findByProperty(map,command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
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
                command.setPojo(SingletonServiceUtil.getUserServiceInstance().findById(pojo.getUserId()));
            }
            command.setRoles(SingletonServiceUtil.getRoleServiceInstance().findAll());
            request.setAttribute(WebConstant.FROM_ITEM,command);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/edit.jsp");
            requestDispatcher.forward(request,response);

        } else if (command.getUrlType()!= null && command.getUrlType().equals(SHOW_IMPORT_USER)) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/importuser.jsp");
            requestDispatcher.forward(request,response);
        }



    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        UploadUtil uploadUtil = new UploadUtil();
        Set<String> value = new HashSet<String>();
        value.add("urlType");
        Object[] objects = uploadUtil.writeOrUpdateFile(request,value,"excel");

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
                        SingletonServiceUtil.getUserServiceInstance().updateUser(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_UPDATE);
                    } else {
                        //saveUser
                        SingletonServiceUtil.getUserServiceInstance().saveUser(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_INSERT);
                    }

                }
            }
            if(objects != null) {
                String urlType =null;
                Map<String,String> mapValue = (Map<String, String>) objects[3];
                for(Map.Entry<String,String> item : mapValue.entrySet() ) {
                    if(item.getKey().equals("urlType")) {
                        urlType = item.getValue();
                    }
                }
                if(urlType!= null && urlType.equals(READ_EXCEL)) {
                    String fileLocation = objects[1].toString();
                    String fileName = objects[2].toString();
                    List<UserImportDTO> excelValues = returnValueFromExcel(fileName,fileLocation);
                }

            }

        } catch (Exception e) {
            log(e.getMessage(),e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_ERROR);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/edit.jsp");
        requestDispatcher.forward(request,response);



    }

    private List<UserImportDTO> returnValueFromExcel(String fileName, String fileLocation) {
        Workbook workbook = null;
        List<UserImportDTO> excelValues =  new ArrayList<UserImportDTO>();
        try {
            workbook = ExcelPoiUtil.getWorkbook(fileName,fileLocation);
            Sheet sheet = workbook.getSheetAt(0);
            for( int i =1; i<=sheet.getLastRowNum();i++) {
                Row row = sheet.getRow(i);
                //System.out.println(row.getCell(0)+"__" +row.getCell(1));
                UserImportDTO userImportDTO = readDataFromExcel(row);
                excelValues.add(userImportDTO);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelValues;

    }

    private UserImportDTO readDataFromExcel(Row row) {
            UserImportDTO userImportDTO = new UserImportDTO();
            userImportDTO.setUsername(ExcelPoiUtil.getCellValue(row.getCell(0)));
            userImportDTO.setPassword(ExcelPoiUtil.getCellValue(row.getCell(1)));
            userImportDTO.setFullName(ExcelPoiUtil.getCellValue(row.getCell(2)));
            userImportDTO.setRoleName(ExcelPoiUtil.getCellValue(row.getCell(3)));
            return userImportDTO;
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
