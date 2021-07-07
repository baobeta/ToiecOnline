package com.example.toiec.core.common.utils;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UploadUtil {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final int maxMemorySize = 1024 * 1024 * 3; //3MBl
    private final int maxRequestSize = 1024 * 1024 * 50; //50 MB
    public Object[] writeOrUpdateFile(HttpServletRequest request, Set<String> titleValues, String path)  {
        Object[] objects = {};
        ServletContext context = request.getServletContext();
        String address = context.getRealPath("fileupload");
        boolean check = true;
        String fileLocation = null;
        String name = null;
        Map<String,String> mapReturnValue = new HashMap<String,String>();

        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(!isMultipart) {
            System.out.println("have not enctype multipart/form-data ");
            check = false;
        }
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Set factory constraints
        factory.setSizeThreshold(maxMemorySize);
        //create repo term
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(maxRequestSize);

        //Parse the request
        try {
            List<FileItem> items = upload.parseRequest(request);
            for(FileItem item : items) {
                if(!item.isFormField()) {
                    String fileName = item.getName();
                    if(StringUtils.isNotBlank(fileName)) {
                        File uploadedFile = new File(address+File.separator+path+File.separator+fileName);
                        fileLocation = address+File.separator+path+File.separator+fileName;
                        name= fileName;
                        boolean isExisting = uploadedFile.exists();
                        try {
                            if(isExisting) {
                                uploadedFile.delete();
                                item.write(uploadedFile);

                            } else {
                                item.write(uploadedFile);
                            }
                        } catch (Exception e) {
                            check =false;
                            log.error(e.getMessage(),e);
                        }

                    }
                } else {
                    if(titleValues!=null) {
                        String nameFiled = item.getFieldName();
                        String valueFiled = item.getString();
                        if(titleValues.contains(nameFiled)) {
                            mapReturnValue.put(nameFiled,valueFiled);
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            check = false;
            log.error(e.getMessage(),e);

        }

        return new Object[] {check, fileLocation, name,mapReturnValue};
    }


}
