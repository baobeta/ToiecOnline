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
import com.example.core.wed.utils.RequestUtil;
import com.example.core.wed.utils.SingletonServiceUtil;
import com.example.core.wed.utils.WebCommonUtil;
import com.example.toiec.core.common.utils.ExcelPoiUtil;
import com.example.toiec.core.common.utils.SessionUtil;
import com.example.toiec.core.common.utils.UploadUtil;
import org.apache.commons.lang.StringUtils;
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
import javax.servlet.http.HttpSession;
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
    private final String VALIDATE_IMPORTER ="validate_import";
    private final String LIST_USER_IMPORT="list_user_import";
    private final String IMPORT_DATA ="import_data";
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        UserCommand command = FormUtil.populate(UserCommand.class,request);
        UserDTO pojo = command.getPojo();
        if(command.getUrlType()!= null && command.getUrlType().equals(WebConstant.URL_LIST))
        {
            Map<String, Object> map = new HashMap<String, Object>();
            command.setMaxPageItems(3);
            RequestUtil.initSearchBean(request,command);
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
        } else if(command.getUrlType()!= null && command.getUrlType().equals(VALIDATE_IMPORTER)) {
            List<UserImportDTO> userImportDTO = (List<UserImportDTO>) SessionUtil.getInstance().getValue(request,LIST_USER_IMPORT);
            command.setUserImportDTOs(returnListUserImport(command, userImportDTO, request));
            request.setAttribute(WebConstant.LIST_ITEM,command);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/importuser.jsp");
            requestDispatcher.forward(request,response);

        }

    }

    private List<UserImportDTO> returnListUserImport(UserCommand command, List<UserImportDTO> userImportDTO, HttpServletRequest request) {
        command.setMaxPageItems(3);
        RequestUtil.initSearchBean(request,command);
        command.setTotalItems(userImportDTO.size());
        int fromIndex = command.getFirstItem();
        if(fromIndex>command.getTotalItems()) {
            fromIndex = 0;
            command.setFirstItem(0);
        }
        int toIndex = command.getFirstItem() + command.getMaxPageItems();
        if(userImportDTO.size()>0) {
            if(toIndex> userImportDTO.size()) {
                toIndex = userImportDTO.size();
            }
            command.setUserImportDTOs(userImportDTO.subList(fromIndex,toIndex));
        }
        return userImportDTO.subList(fromIndex,toIndex);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
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
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/admin/user/edit.jsp");
                requestDispatcher.forward(request,response);
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
                    validateData(excelValues);
                    SessionUtil.getInstance().putValue(request,LIST_USER_IMPORT,excelValues);
                    response.sendRedirect("admin-user-import-validate.html?urlType=validate_import");
                }

            }
            if(command.getUrlType()!= null && command.getUrlType().equals(IMPORT_DATA)) {
                List<UserImportDTO> userImportDTO = (List<UserImportDTO>) SessionUtil.getInstance().getValue(request,LIST_USER_IMPORT);
                SingletonServiceUtil.getUserServiceInstance().saveUserImport(userImportDTO);
                SessionUtil.getInstance().remove(request,LIST_USER_IMPORT);
                response.sendRedirect("admin-user-list.html?urlType=url_list");

            }

        } catch (Exception e) {
            log(e.getMessage(),e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE,WebConstant.REDIRECT_ERROR);
        }

    }

    private void validateData(List<UserImportDTO> excelValues) {
        Set<String> stringSet = new HashSet<>();
        for(UserImportDTO item :excelValues) {
            validateRequireFiled(item);
            validateDuplicate(item,stringSet);
        }
        SingletonServiceUtil.getUserServiceInstance().validateImportUser(excelValues);
    }

    private void validateDuplicate(UserImportDTO item, Set<String> stringSet) {
        String messages = item.getError();
        if(!stringSet.contains(item.getUserName())) {
            stringSet.add(item.getUserName());
        } else {
            if(item.isValid())
            {
                messages+="<br/>";
                messages+=resourceBundle.getString("label.username.duplicate");
            }
        }
        if(StringUtils.isNotBlank(messages)) {
            item.setValid(false);
            item.setError(messages);
        }

    }

    private void validateRequireFiled(UserImportDTO item) {
        String messages = "";
        if(StringUtils.isBlank(item.getUserName()))
        {
            messages+="<br/>";
            messages+=resourceBundle.getString("label.username.notempty");
        }
        if(StringUtils.isBlank(item.getPassword())) {
            messages+="<br/>";
            messages+=resourceBundle.getString("label.password.notempty");
        }
        if(StringUtils.isBlank(item.getRoleName())) {
            messages+="<br/>";
            messages+=resourceBundle.getString("label.rolename.notempty");
        }
        if(StringUtils.isNotBlank(messages)) {
            item.setValid(false);
        }
        item.setError(messages);
    }

    private List<UserImportDTO> returnValueFromExcel(String fileName, String fileLocation) throws IOException{
        Workbook workbook = ExcelPoiUtil.getWorkBook(fileName, fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        List<UserImportDTO> excelValues = new ArrayList<UserImportDTO>();
        for (int i=1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            UserImportDTO userImportDTO = readDataFromExcel(row);
            excelValues.add(userImportDTO);
        }
        return excelValues;
    }

    private UserImportDTO readDataFromExcel(Row row) {
            UserImportDTO userImportDTO = new UserImportDTO();
            userImportDTO.setUserName(ExcelPoiUtil.getCellValue(row.getCell(0)));
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
