package com.example.toiec.core.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// utill read file excel
public class ExcelPoiUtil {

    public static Workbook getWorkBook(String fileName, String fileLocation) throws IOException {
        // Open file
        FileInputStream excelFile = new FileInputStream(new File(fileLocation));
        Workbook workbook = null;
        // Classify file excel xls(excel<2007) and xlsx (excel>2007)
        // Create workbook
        if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook(excelFile);
        } else if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(excelFile);
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        // read cell value from workbook
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // Classify data
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = Boolean.toString(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                        break;
                }
        }
        return cellValue;
    }
}