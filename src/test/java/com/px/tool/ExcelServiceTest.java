package com.px.tool;

import com.px.tool.infrastructure.service.ExcelService;
import com.px.tool.infrastructure.service.impl.ExcelServiceImpl;
import com.px.tool.infrastructure.utils.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelServiceTest {

    private ExcelService excelService;

    private void printCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case BOOLEAN:
                System.out.print(cell.getBooleanCellValue());
                break;
            case STRING:
                System.out.print(cell.getRichStringCellValue().getString());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    System.out.print(cell.getDateCellValue());
                } else {
                    System.out.print(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                System.out.print(cell.getCellFormula());
                break;
            case BLANK:
            default:
                System.out.print("");
                break;
        }

        System.out.print("\t");
    }

    @Before
    public void init() {
        excelService = new ExcelServiceImpl();
    }

    @Test
    public void exportFile() throws IOException {
//        excelService.exportFile();
        FileUtils.copy(new File("/mnt/project/Sources/NGHIA/free/px-toool/nghia-file/temp.xlsx"), new File("/mnt/project/Sources/NGHIA/free/px-toool/nghia-file/temp2.xlsx"));
    }

    @Test
    public void appendFile() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File("/mnt/project/Sources/NGHIA/free/px-toool/src/main/resources/templates/1_Kiem_Hong.xlsx")));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int totalLine = 50;
        if (1 > -2) {
            sheet.copyRows(24, 27, 27 + (totalLine - 18), new CellCopyPolicy()); // copy and paste

            for (int i = 24; i < 27 + (totalLine - 18); i++) {
                sheet.createRow(i);
                sheet.copyRows(6, 6, i - 1, new CellCopyPolicy()); // copy and paste
            }
        }
        XSSFRow row0 = sheet.getRow(0);
        XSSFRow row1 = sheet.getRow(1);
        XSSFRow row2 = sheet.getRow(2);


        setCellVal(row0, 4, "val_Tên VKTBKT ");
        setCellVal(row0, 6, "val_Số Hiệu ");
        setCellVal(row0, 8, "val_Tờ số ");
        setCellVal(row1, 2, "val_ Phan xuong");
        setCellVal(row1, 4, "val_ Nguồn v ");
        setCellVal(row1, 6, "val_ số XX ");
        setCellVal(row1, 8, "val_ số tờ ");
        setCellVal(row2, 2, "val_ tổ sx ");
        setCellVal(row2, 4, "val_ công đoạn");

        for (int i = 0; i < totalLine; i++) {
            XSSFRow currRow = sheet.getRow(5 + i);
            setCellVal(currRow, 0, "val_ TT");
            setCellVal(currRow, 1, "val_ tên phụ kiện");
            setCellVal(currRow, 3, "val_ tên linh kiện");
            setCellVal(currRow, 4, "val_ ký hiệu");
            setCellVal(currRow, 5, "val_ SL");
            setCellVal(currRow, 6, "val_ Dạng hư hỏng");
            setCellVal(currRow, 7, "val_ pp khắc phục");
            setCellVal(currRow, 8, "val_ ng kiểm hỏng");
        }


        FileOutputStream out = new FileOutputStream("/mnt/project/Sources/NGHIA/free/px-toool/src/main/resources/templates/new_Kiem_Hong.xlsx");
        workbook.write(out);
        out.close();
    }

    private void setCellVal(Row row, int cell, String val) {
        row.getCell(cell).setCellValue(val);
    }


    @Test
    public void exportDatHang() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File("/mnt/project/Sources/NGHIA/free/px-toool/src/main/resources/templates/2_Dat_Hang.xlsx")));
        XSSFSheet sheet = workbook.getSheetAt(0);
        int totalLine = 40;
        if (totalLine > 5) {
            sheet.copyRows(13, 16, 16 + (totalLine - 5), new CellCopyPolicy()); // copy and paste

            for (int i = 13; i < 16 + (totalLine - 5); i++) {
                sheet.createRow(i);
                sheet.copyRows(7, 7, i - 1, new CellCopyPolicy()); // copy and paste
            }
        }

        XSSFRow row0 = sheet.getRow(1);
        XSSFRow row1 = sheet.getRow(2);
        XSSFRow row2 = sheet.getRow(3);

        setCellVal(row0, 6, "Số");
        setCellVal(row1, 6, "Đơn vị yêu cầu ");
        setCellVal(row1, 8, "Phân Xưởng");

        setCellVal(row2, 6, "Nội Dung");

        for (int i = 0; i < totalLine; i++) {
            XSSFRow crrRow = sheet.getRow(6 + i);
            setCellVal(crrRow, 0, "TT");
            setCellVal(crrRow, 1, "Tên pHụ kiện");
            setCellVal(crrRow, 2, "Tên Vật Tư");
            setCellVal(crrRow, 3, "Mã Kí Hiệu");
            setCellVal(crrRow, 4, "DVT");
            setCellVal(crrRow, 5, "SL");
            setCellVal(crrRow, 6, "Mục Đích ");
            setCellVal(crrRow, 7, "Phương pháp kahwsc phục");
            setCellVal(crrRow, 8, "Số pghieesu đặt hàng ");
            setCellVal(crrRow, 9, "người thự hiện");
        }
        FileOutputStream out = new FileOutputStream("/mnt/project/Sources/NGHIA/free/px-toool/src/main/resources/templates/new_Dat_hang.xlsx");
        workbook.write(out);
        out.close();
    }

    @After
    public void clean() {
        this.excelService = null;
    }
}
