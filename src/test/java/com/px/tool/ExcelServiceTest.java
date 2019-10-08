package com.px.tool;

import com.px.tool.service.ExcelService;
import com.px.tool.service.impl.ExcelServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ExcelServiceTest {


    private ExcelService excelService;

    @Before
    public void init() {
        excelService = new ExcelServiceImpl();
    }

    @Test
    public void exportFile() throws IOException {
        excelService.exportFile();
    }

    @After
    public void clean() {
        this.excelService = null;
    }
}
