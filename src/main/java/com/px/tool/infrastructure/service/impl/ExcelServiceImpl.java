package com.px.tool.infrastructure.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import com.px.tool.domain.kiemhong.KiemHongPayLoad;
import com.px.tool.domain.kiemhong.service.KiemHongService;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.ExcelService;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public void exportFile() throws IOException {
    }

    private void merge(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    @Override
    public void exportFile(Long requestId, RequestType requestType, HttpServletResponse response) {
        try {
            if (requestType == RequestType.KIEM_HONG) {
                exportKiemHong(response, kiemHongService.findThongTinKiemHong(1L, requestId));
            } else if (requestType == RequestType.DAT_HANG) {
                exportPHieuDatHang(response, phieuDatHangService.findById(1L, requestId));
            } else if (requestType == RequestType.PHUONG_AN) {
                new File("./src/main/resources/templates/3_phuong_an.xls");
            } else if (requestType == RequestType.CONG_NHAN_THANH_PHAM) {
                exportCNTP(response, congNhanThanhPhamService.timCongNhanThanhPham(1L, requestId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PXException("File not found");
        }
    }

    private void exportKiemHong(HttpServletResponse response, KiemHongPayLoad payload) {
        try (FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/1_Kiem_Hong.xlsx"))) {

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int totalLine = payload.getKiemHongDetails().size();
            if (totalLine > 18) {
                sheet.copyRows(24, 27, 27 + (totalLine - 18), new CellCopyPolicy()); // copy and paste

                for (int i = 24; i < 27 + (totalLine - 18); i++) {
                    sheet.createRow(i);
                    sheet.copyRows(6, 6, i - 1, new CellCopyPolicy()); // copy and paste
                }
            }
            XSSFRow row0 = sheet.getRow(0);
            XSSFRow row1 = sheet.getRow(1);
            XSSFRow row2 = sheet.getRow(2);


            setCellVal(row0, 4, payload.getTenVKTBKT());
            setCellVal(row0, 6, payload.getSoHieu());
            setCellVal(row0, 8, payload.getToSo());
            setCellVal(row1, 2, payload.getPhanXuong().toString()); // TODO map phuong xuong ra name
            setCellVal(row1, 4, payload.getNguonVao());
            setCellVal(row1, 6, payload.getSoXX());
            setCellVal(row1, 8, payload.getSoTo());
            setCellVal(row2, 2, payload.getToSX().toString()); // TODO map to san xuat
            setCellVal(row2, 4, payload.getCongDoan());

            for (int i = 0; i < totalLine; i++) {
                XSSFRow currRow = sheet.getRow(5 + i);
                setCellVal(currRow, 0, i + 1 + "");
                setCellVal(currRow, 1, payload.getKiemHongDetails().get(i).getTenPhuKien());
                setCellVal(currRow, 3, payload.getKiemHongDetails().get(i).getTenLinhKien());
                setCellVal(currRow, 4, payload.getKiemHongDetails().get(i).getKyHieu());
                setCellVal(currRow, 5, payload.getKiemHongDetails().get(i).getSl());
                setCellVal(currRow, 6, payload.getKiemHongDetails().get(i).getDangHuHong());
                setCellVal(currRow, 7, payload.getKiemHongDetails().get(i).getPhuongPhapKhacPhuc());
                setCellVal(currRow, 8, payload.getKiemHongDetails().get(i).getNguoiKiemHong());
            }
            workbook.write(response.getOutputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            response.flushBuffer();
        } catch (IOException e) {
        }
    }

    private void exportPHieuDatHang(HttpServletResponse httpServletResponse, PhieuDatHangPayload payload) {
        try (FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/2_Dat_Hang.xlsx"))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int totalLine = payload.getPhieuDatHangDetails().size();
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

            setCellVal(row0, 6, payload.getSo());
            setCellVal(row1, 6, payload.getDonViYeuCau());
            setCellVal(row1, 8, payload.getPhanXuong());

            setCellVal(row2, 6, payload.getNoiDung());

            for (int i = 0; i < totalLine; i++) {
                XSSFRow crrRow = sheet.getRow(6 + i);
                setCellVal(crrRow, 0, i + 1 + "");
                setCellVal(crrRow, 1, payload.getPhieuDatHangDetails().get(i).getTenPhuKien());
                setCellVal(crrRow, 2, payload.getPhieuDatHangDetails().get(i).getTenVatTuKyThuat());
                setCellVal(crrRow, 3, payload.getPhieuDatHangDetails().get(i).getKiMaHieu());
                setCellVal(crrRow, 4, payload.getPhieuDatHangDetails().get(i).getDvt());
                setCellVal(crrRow, 5, payload.getPhieuDatHangDetails().get(i).getSl());
                setCellVal(crrRow, 6, payload.getPhieuDatHangDetails().get(i).getMucDicSuDungAsString());
                setCellVal(crrRow, 7, payload.getPhieuDatHangDetails().get(i).getPhuongPhapKhacPhuc());
                setCellVal(crrRow, 8, payload.getPhieuDatHangDetails().get(i).getSoPhieuDatHang());
                setCellVal(crrRow, 9, payload.getPhieuDatHangDetails().get(i).getNguoiThucHien());
            }
            workbook.write(httpServletResponse.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpServletResponse.flushBuffer();
            } catch (IOException e) {
            }
        }
    }

    public void exportCNTP(HttpServletResponse response, CongNhanThanhPhamPayload payload) {
        try (FileInputStream fis = new FileInputStream(new File("/mnt/project/Sources/NGHIA/free/px-toool/src/main/resources/templates/4_cntp.xlsx"))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row0 = sheet.getRow(2);
            XSSFRow row1 = sheet.getRow(3);
            XSSFRow row2 = sheet.getRow(4);
            XSSFRow row3 = sheet.getRow(5);
            XSSFRow row4 = sheet.getRow(6);
            XSSFRow row5 = sheet.getRow(7);

            XSSFRow row19 = sheet.getRow(19);

            setCellVal(row0, 1, payload.getTenSanPham());
            setCellVal(row1, 1, payload.getNoiDung());
            setCellVal(row2, 1, payload.getSoPA());
            setCellVal(row3, 1, payload.getDonviThucHien());
            setCellVal(row3, 4, payload.getTo());
            setCellVal(row4, 1, payload.getDonviDatHang());
            setCellVal(row4, 3, payload.getSoLuong());
            setCellVal(row4, 5, payload.getDvt());
            setCellVal(row5, 1, payload.getSoNghiemThuDuoc());
            setCellVal(row19, 1, payload.getLaoDongTienLuong().toString());
            setCellVal(row19, 3, payload.getGioX().toString());
            setCellVal(row19, 5, payload.getDong().toString());
//

            int totalLine = 4;
            if (totalLine > 5) {
                sheet.copyRows(18, 24, 24 + (totalLine - 6), new CellCopyPolicy()); // copy and paste

                for (int i = 18; i < 24 + (totalLine - 6); i++) {
                    sheet.createRow(i);
                    sheet.copyRows(12, 12, i - 1, new CellCopyPolicy()); // copy and paste
                }
            }

            for (int i = 0; i < totalLine; i++) {
                XSSFRow crrRow = sheet.getRow(11 + i);
                setCellVal(crrRow, 0, payload.getNoiDungThucHiens().get(i).getNoiDung());
                setCellVal(crrRow, 3, payload.getNoiDungThucHiens().get(i).getKetQua());
                setCellVal(crrRow, 4, payload.getNoiDungThucHiens().get(i).getNghiemThu());
            }
            workbook.write(response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                response.flushBuffer();
            } catch (IOException e) {
            }
        }
    }

    private void setCellVal(Row row, int cell, String val) {
        row.getCell(cell).setCellValue(val);
    }


//    private String mappingTosx() {
//        userRepository.findByGroup(group_17_25)
//                .stream()
//                .filter(el -> el.getLevel() == 3)
//                .map(PhanXuongPayload::fromUserEntity)
//                .collect(Collectors.toList());
//    }
}
