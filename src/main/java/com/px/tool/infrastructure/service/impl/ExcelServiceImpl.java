package com.px.tool.infrastructure.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import com.px.tool.domain.kiemhong.KiemHongPayLoad;
import com.px.tool.domain.kiemhong.service.KiemHongService;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.ExcelService;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

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
    private UserService userService;

    @Override
    public void exportFile(Long requestId, RequestType requestType, HttpServletResponse response) {
        try {
            if (requestType == RequestType.KIEM_HONG) {
                exportKiemHong(response, kiemHongService.findThongTinKiemHong(1L, requestId));
            } else if (requestType == RequestType.DAT_HANG) {
                exportPHieuDatHang(response, phieuDatHangService.findById(1L, requestId));
            } else if (requestType == RequestType.PHUONG_AN) {
                exportPhuongAn(response, phuongAnService.findById(1L, requestId));
            } else if (requestType == RequestType.CONG_NHAN_THANH_PHAM) {
                exportCNTP(response, congNhanThanhPhamService.timCongNhanThanhPham(1L, requestId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PXException("File not found");
        }
    }

    private void exportKiemHong(HttpServletResponse response, KiemHongPayLoad payload) {
        Map<Long, User> userById = userService.userById();
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
            XSSFRow row24 = sheet.getRow(24);

            setCellVal(row0, 4, payload.getTenVKTBKT());
            setCellVal(row0, 6, payload.getSoHieu());
            setCellVal(row0, 8, payload.getToSo());
            setCellVal(row1, 2, fillUserInfo(payload.getPhanXuong(), userById));
            setCellVal(row1, 4, payload.getNguonVao());
            setCellVal(row1, 6, payload.getSoXX());
            setCellVal(row1, 8, payload.getSoTo());
            setCellVal(row2, 2, fillUserInfo(payload.getToSX(), userById));
            setCellVal(row2, 4, payload.getCongDoan());

            setCellVal(row24, 3, payload.getNgayThangNamQuanDoc());
            setCellVal(row24, 6, payload.getNgayThangNamTroLyKT());
            setCellVal(row24, 8, payload.getNgayThangNamToTruong());

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
            XSSFRow row13 = sheet.getRow(13);
            setCellVal(row13, 2, payload.getNgayThangNamTPKTHK());
            setCellVal(row13, 6, payload.getNgayThangNamTPVatTu());
            setCellVal(row13, 8, payload.getNgayThangNamNguoiDatHang());

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
        Map<Long, User> userById = userService.userById();
        try (FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/4_cntp.xlsx"))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row0 = sheet.getRow(2);
            XSSFRow row1 = sheet.getRow(3);
            XSSFRow row2 = sheet.getRow(4);
            XSSFRow row3 = sheet.getRow(5);
            XSSFRow row4 = sheet.getRow(6);
            XSSFRow row5 = sheet.getRow(7);

            XSSFRow row19 = sheet.getRow(19);
            XSSFRow row21 = sheet.getRow(21);
            XSSFRow row25 = sheet.getRow(25);
            XSSFRow row26 = sheet.getRow(26);

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

            setCellVal(row21, 1, payload.getNgayThangNamQuanDoc());
            setCellVal(row21, 4, payload.getNgayThangNamTPKCS());

            if (Objects.nonNull(payload.getToTruong1Id())) {
                setCellVal(row25, 0, payload.getNgayThangNamToTruong1());
                setCellVal(row26, 0, payload.getToTruong1fullName()); // TODO: id /name/chuc vu
            }
            if (Objects.nonNull(payload.getToTruong2Id())) {
                setCellVal(row25, 0, payload.getNgayThangNamToTruong2());
                setCellVal(row26, 0, payload.getToTruong2fullName());
            }
            if (Objects.nonNull(payload.getToTruong3Id())) {
                setCellVal(row25, 0, payload.getNgayThangNamToTruong3());
                setCellVal(row26, 0, payload.getToTruong3fullName());
            }
            if (Objects.nonNull(payload.getToTruong4Id())) {
                setCellVal(row25, 0, payload.getNgayThangNamToTruong4());
                setCellVal(row26, 0, payload.getToTruong4fullName());
            }
            if (Objects.nonNull(payload.getToTruong5Id())) {
                setCellVal(row25, 0, payload.getNgayThangNamToTruong5());
                setCellVal(row26, 0, payload.getToTruong5fullName());
            }

//

            int totalLine = payload.getNoiDungThucHiens().size();
            if (totalLine > 5) {
                sheet.copyRows(18, 29, 29 + (totalLine - 6), new CellCopyPolicy()); // copy and paste

                for (int i = 18; i < 29 + (totalLine - 6); i++) {
                    sheet.createRow(i);
                    sheet.copyRows(12, 12, i - 1, new CellCopyPolicy()); // copy and paste
                }
            }

            for (int i = 0; i < totalLine; i++) {
                XSSFRow crrRow = sheet.getRow(11 + i);
                setCellVal(crrRow, 0, payload.getNoiDungThucHiens().get(i).getNoiDung());
                setCellVal(crrRow, 3, payload.getNoiDungThucHiens().get(i).getKetQua());
                setCellVal(crrRow, 4, fillUserInfo(payload.getNoiDungThucHiens().get(i).getNghiemThu(), userById));
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

    public void exportPhuongAn(HttpServletResponse response, PhuongAnPayload payload) {
        try (FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/3_phuong_an.xlsx"))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow row1 = sheet.getRow(1);
            XSSFRow row2 = sheet.getRow(2);
            XSSFRow row3 = sheet.getRow(3);
            XSSFRow row4 = sheet.getRow(4);
            XSSFRow row5 = sheet.getRow(5);
            XSSFRow row32 = sheet.getRow(32);

            setCellVal(row1, 13, payload.getToSo());
            setCellVal(row2, 13, payload.getSoTo());
            setCellVal(row3, 13, payload.getPDH());
            setCellVal(row3, 6, payload.getSanPham());
            setCellVal(row4, 6, payload.getNoiDung());
            setCellVal(row5, 6, payload.getNguonKinhPhi());

            setCellVal(row3, 0, payload.getNgayThangNamGiamDoc());
            setCellVal(row32, 1, payload.getNgayThangNamTPKTHK());
            setCellVal(row32, 3, payload.getNgayThangNamTPKEHOACH());
            setCellVal(row32, 8, payload.getNgayThangNamtpVatTu());
            setCellVal(row32, 12, payload.getNgayThangNamNguoiLap());

//

            int totalLine = payload.getDinhMucLaoDongs().size();
            int startFix1 = 15;
            int endFix1 = 35;
            if (totalLine > 6) {
                sheet.copyRows(startFix1, endFix1, endFix1 + (totalLine - 6), new CellCopyPolicy()); // copy and paste

                for (int i = startFix1; i < endFix1 + (totalLine - 6); i++) {
                    sheet.createRow(i);
                    sheet.copyRows(9, 9, i - 1, new CellCopyPolicy()); // copy and paste
                }
            }

            for (int i = 0; i < payload.getDinhMucLaoDongs().size(); i++) {
                XSSFRow crrRow = sheet.getRow(9 + i);
                setCellVal(crrRow, 0, i + 1 + "");
                setCellVal(crrRow, 1, payload.getDinhMucLaoDongs().get(i).getNoiDungCongViec());
                setCellVal(crrRow, 10, payload.getDinhMucLaoDongs().get(i).getBacCV());
                setCellVal(crrRow, 11, payload.getDinhMucLaoDongs().get(i).getDm());
                setCellVal(crrRow, 12, payload.getDinhMucLaoDongs().get(i).getGhiChu());
            }
//
            int soDongBiLech = (totalLine > 5 ? totalLine + 14 : 0);
            int startFix2 = 29 + soDongBiLech;
            int endFix2 = 35 + soDongBiLech;
            int totalLine2 = payload.getDinhMucVatTus().size();
            int row_mau = 20 + soDongBiLech;
            if (totalLine2 > 9) {
                sheet.copyRows(startFix2, endFix2, endFix2 + (totalLine2 - 14), new CellCopyPolicy()); // copy and paste

                for (int i = startFix2; i < endFix2 + (totalLine2 - 14); i++) {
                    sheet.createRow(i);
                    sheet.copyRows(row_mau, row_mau, i - 1, new CellCopyPolicy()); // copy and paste
                }
            }

            // dang in o dong 35 => 34
            // expect 49 => 48
            for (int i = 0; i < payload.getDinhMucVatTus().size(); i++) {
                XSSFRow crrRow2 = sheet.getRow(row_mau + i);
                setCellVal(crrRow2, 0, i + 1 + "");
                setCellVal(crrRow2, 1, payload.getDinhMucVatTus().get(i).getTenVatTuKyThuat());
                setCellVal(crrRow2, 2, payload.getDinhMucVatTus().get(i).getKyMaKyHieu());
                setCellVal(crrRow2, 3, payload.getDinhMucVatTus().get(i).getDvt());
                setCellVal(crrRow2, 4, payload.getDinhMucVatTus().get(i).getDm1SP());
                setCellVal(crrRow2, 5, payload.getDinhMucVatTus().get(i).getSoLuongSanPham());
                setCellVal(crrRow2, 6, payload.getDinhMucVatTus().get(i).getTongNhuCau());
                setCellVal(crrRow2, 7, payload.getDinhMucVatTus().get(i).getKhoDonGia());
                setCellVal(crrRow2, 8, payload.getDinhMucVatTus().get(i).getKhoSoLuong());
                setCellVal(crrRow2, 9, payload.getDinhMucVatTus().get(i).getKhoThanhTien());
                setCellVal(crrRow2, 10, payload.getDinhMucVatTus().get(i).getMnDonGia());
                setCellVal(crrRow2, 11, payload.getDinhMucVatTus().get(i).getMnSoLuong());
                setCellVal(crrRow2, 12, payload.getDinhMucVatTus().get(i).getMnThanhTien());
                setCellVal(crrRow2, 13, payload.getDinhMucVatTus().get(i).getGhiChu());
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

    private String fillUserInfo(Long userId, Map<Long, User> userById) {
        if (Objects.isNull(userId) || !userById.containsKey(userId)) {
            return "";
        }
        return userById.get(userId).getAlias();
    }
}
