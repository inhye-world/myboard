package edu.example.myboard.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

import java.io.FileInputStream;
import java.io.*;
import java.text.SimpleDateFormat;

public class ExcelUtil {
    public static Workbook getWorkbook(String filePath){
        FileInputStream fis =null;
        try{
            fis = new FileInputStream(filePath);
        }catch (FileNotFoundException e){
            throw new RuntimeException(e.getMessage(), e);
        }

        Workbook wb = null;

        if(filePath.toUpperCase().endsWith(".XLS")){
            try{
                wb = WorkbookFactory.create(fis);
            }catch(IOException e){
                throw new RuntimeException(e.getMessage(),e);
            }
        }else if(filePath.toUpperCase().endsWith(".XLSX")){
            try {
                wb = XSSFWorkbookFactory.create(fis);
            }catch(IOException e){
                throw new RuntimeException(e.getMessage(),e);
            }
        }

        return wb;

    }

    @SuppressWarnings("deprecation")
    public static String cellValue(Cell cell){
        String value = null;
        if(cell == null) {
            value = "";
        }else{
            switch(cell.getCellType()){
                case FORMULA:
                    value = cell.getCellFormula();
                break;
                case NUMERIC:
                    if(DateUtil.isCellDateFormatted(cell)){
                        SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        value = "" + objSimpleDateFormat.format(cell.getDateCellValue());
                    }else{
                        value = "" + String.format("%.0f", new Double(cell.getNumericCellValue()));
                    }
                break;
                case STRING:
                    value = "" + cell.getStringCellValue();
                break;
                case BLANK:
                    value = "";
                break;
                case ERROR:
                    value = "" + cell.getErrorCellValue();
                break;
                default:
            }
        }
        return value.trim();
    }
}
