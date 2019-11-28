package com.px.tool;

import com.px.tool.infrastructure.service.ExcelService;
import com.px.tool.infrastructure.service.impl.ExcelServiceImpl;
import com.px.tool.infrastructure.utils.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.DateUtil;
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
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File("/mnt/project/Sources/NGHIA/free/px-toool/nghia-file/temp.xlsx")));
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.copyRows(7, 11, 16, new CellCopyPolicy()); // copy and paste
        for (int i = 7; i <=11; i++) {
            sheet.createRow(i);
        }
        FileOutputStream out = new FileOutputStream("/mnt/project/Sources/NGHIA/free/px-toool/nghia-file/temp3.xlsx");
        workbook.write(out);
        out.close();
    }

    @After
    public void clean() {
        this.excelService = null;
    }
}
