package com.alliacom.audit.utilities;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Rapport {

    public static String RAPPORT_DIRECTORY = "./rapports/";
    private String file_name;
    private String file_name_without_path;
    private String grapheBase64 = "";
    private byte[] graphe;

    public Rapport(String name) {
        this.file_name_without_path = name + ".xls";
        this.file_name = RAPPORT_DIRECTORY + name + ".xls";
    }

    /**
     * Generate the file in excel format
     * @return filepath
     */
    public String create(Object[][] datas) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Rapport");
        Object[] headers = {"Norme", "Reference", "Exigence", "Score"};

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
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                    scoreTotal += (Integer) field;
                }
            }
        }

        /* --  Footer -- */
        Row footer = sheet.createRow(rowNumber++);
        int footerColNumber = 2;

        Cell cell1 = footer.createCell(footerColNumber++);
        cell1.setCellValue("SCORE TOTAL");

        Cell cell2 = footer.createCell(footerColNumber++);
        cell2.setCellValue(scoreTotal);

        if(graphe.length > 0) {
            int pictureId = workbook.addPicture(graphe, Workbook.PICTURE_TYPE_PNG);
            Drawing drawing = sheet.createDrawingPatriarch();
            CreationHelper creationHelper = workbook.getCreationHelper();
            ClientAnchor clientAnchor = creationHelper.createClientAnchor();
            clientAnchor.setCol1(5);
            clientAnchor.setRow1(1);
            Picture picture = drawing.createPicture(clientAnchor, pictureId);
            picture.resize();
        }

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell1.setCellStyle(cellStyle);
        cell2.setCellStyle(cellStyle);


        /* -- Basic XLS Configuration -- */
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(2);

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

        return file_name_without_path;
    }


    public void generateGrapheImage() {
        String encodingPrefix = "base64,";
        int contentStartIndex = grapheBase64.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData = Base64.decodeBase64(grapheBase64.substring(contentStartIndex));
        graphe = imageData;
    }

    public static String getRapportDirectory() {
        return RAPPORT_DIRECTORY;
    }

    public static void setRapportDirectory(String rapportDirectory) {
        RAPPORT_DIRECTORY = rapportDirectory;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_name_without_path() {
        return file_name_without_path;
    }

    public void setFile_name_without_path(String file_name_without_path) {
        this.file_name_without_path = file_name_without_path;
    }

    public String getGrapheBase64() {
        return grapheBase64;
    }

    public void setGrapheBase64(String grapheBase64) {
        this.grapheBase64 = grapheBase64;
    }
}
