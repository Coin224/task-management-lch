package com.lch.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;


public class ExcelTool {


    public static HSSFWorkbook createHSSFWorkbook(String sheetName, String[] title, String[][] data) {
        //1.创建workbook对象  excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.使用workbook对象，创建sheet页
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //3.使用sheet对象 创建表头 第0行 excel从0开始
        HSSFRow row = sheet.createRow(0);
            //3.1 设置单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
            //设置居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.DOUBLE);
            //todo 左右
            //前景色、背景色
        cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            //字体
        HSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        //把字体添加到style中
        cellStyle.setFont(font);
        //4.写标题
        for (int i = 0; i < title.length; i++) {
            //5.使用row 创建cell 单元格对象
            HSSFCell cell = row.createCell(i);
            //添加样式
            cell.setCellStyle(cellStyle);
            //6.设置单元格内容
            cell.setCellValue(title[i]);
        }
        //7.填充数据内容
        for (int i = 0; i < data.length ; i++) {
            row = sheet.createRow(i+1);
            for (int j = 0; j < data[i].length; j++) {
                //写内容到单元格
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(data[i][j]);
            }
        }
        return workbook;
    }
}
