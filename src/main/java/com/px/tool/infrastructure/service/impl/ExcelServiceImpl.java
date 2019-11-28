package com.px.tool.infrastructure.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import com.px.tool.domain.kiemhong.service.KiemHongService;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.ExcelService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private KiemHongService kiemHongService;

    @Autowired
    private PhieuDatHangService phieuDatHangService;

    @Autowired
    private PhuongAnService phuongAnService;

    @Autowired
    private CongNhanThanhPhamService congNhanThanhPhamService;

    @Override
    public void exportFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);


        Row row2 = sheet.createRow(2);
        Row row3 = sheet.createRow(3);
        Row row4 = sheet.createRow(4);
        Row row5 = sheet.createRow(5);
        Row row6 = sheet.createRow(6);
        Row row7 = sheet.createRow(7);
        Row row8 = sheet.createRow(8);
        Row row9 = sheet.createRow(9);
        Row row10 = sheet.createRow(10);
        merge(sheet, 2, 2, 1, 3);
        Cell cell22 = row2.createCell(1);
        cell22.setCellValue("Nha may A41");


        row3.createCell(0).setCellValue("a");
        row4.createCell(0).setCellValue("b");
        row5.createCell(0).setCellValue("c");
        row6.createCell(0).setCellValue("d");
        row7.createCell(0).setCellValue("d1");
        row8.createCell(0).setCellValue("d2");
        row9.createCell(0).setCellValue("d3");
        row10.createCell(0).setCellValue("d4");

        sheet.shiftRows(4, 5, 1);
        File currDir = new File("nghia-file/");
        String path = currDir.getAbsolutePath();

        String fileLocation = path + "/" + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    private void merge(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    @Override
    public File exportFile(Long requestId, RequestType requestType) {
        try {
            if (requestType == RequestType.KIEM_HONG) {
                return new File("./src/main/resources/templates/1_Kiem_Hong.xls");
            } else if (requestType == RequestType.DAT_HANG) {
                return new File("./src/main/resources/templates/2_Dat_Hang.xlsx");
            } else if (requestType == RequestType.PHUONG_AN) {
                return new File("./src/main/resources/templates/3_phuong_an.xls");
            } else if (requestType == RequestType.CONG_NHAN_THANH_PHAM) {
                return new File("./src/main/resources/templates/4_cntp.xls");
            }
            throw new PXException("Không có file để download");
        } catch (Exception e) {
            e.printStackTrace();
            throw new PXException("File not found");
        }
    }
}
