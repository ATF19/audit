package com.alliacom.audit.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Rapport {

    private static String RAPPORT_DIRECTORY = "./rapports/";
    private String file_name;

    public Rapport(String name) {
        this.file_name = RAPPORT_DIRECTORY + name + ".xls";
    }

    /**
     * Generate the file in excel format
     * @return filepath
     */
    public String create() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Rapport");
        Object[] headers = {"Questionnaire", "Score"};
        Object[][] datas = {
            {"Question 1", 2},
            {"Question 3", 2}
        };
        int scoreTotal = 0;
        int rowNumber = 0;

        /* --  Header -- */
        Row header = sheet.createRow(rowNumber++);
        int headerColNumber = 0;
        for(Object headerTitle : headers) {
            Cell cell = header.createCell(headerColNumber++);
            cell.setCellValue((String) headerTitle);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(cellStyle);
        }

        /* --  Body -- */
        for(Object[] data : datas) {
            int colNumber = 0;
            Row row = sheet.createRow(rowNumber++);
            for(Object field : data) {
                Cell cell = row.createCell(colNumber++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                }
                else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                    scoreTotal += (Integer) field;
                }
            }
        }

        /* --  Footer -- */


        /* -- Basic XLS Configuration -- */
        sheet.autoSizeColumn(0); // Expand the first cell size dynamically

        /* --  Save the file -- */
        try {
            FileOutputStream outputStream = new FileOutputStream(file_name);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }


}
