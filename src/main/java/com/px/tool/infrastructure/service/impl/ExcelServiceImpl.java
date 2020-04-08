package com.px.tool.infrastructure.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import com.px.tool.domain.excel.DocumentExporter;
import com.px.tool.domain.excel.DocumentFactory;
import com.px.tool.domain.excel.KiemHongExporter;
import com.px.tool.domain.kiemhong.KiemHongPayLoad;
import com.px.tool.domain.kiemhong.service.KiemHongService;
import com.px.tool.domain.mucdich.sudung.MucDichSuDung;
import com.px.tool.domain.mucdich.sudung.repository.MucDichSuDungRepository;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.phuongan.repository.PhuongAnRepository;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.ExcelService;
import com.px.tool.infrastructure.utils.CollectionUtils;
import com.px.tool.infrastructure.utils.CommonUtils;
import com.px.tool.infrastructure.utils.ExcelImageService;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class ExcelServiceImpl extends BaseServiceImpl implements ExcelService {
    private static final String base_dir_kiem_hong = "/home/lending/Documents/sources/nghia/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/kiem_hong/";
    private static final String base_dir_dat_hang = "/home/lending/Documents/sources/nghia/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/dat_hang/";
    private static final String base_dir_pa = "/home/lending/Documents/sources/nghia/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/phuong_an/";
    private static final String base_dir_cntp = "/home/lending/Documents/sources/nghia/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/cntp/";

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

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PhuongAnRepository phuongAnRepository;

    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Autowired
    private MucDichSuDungRepository mucDichSuDungRepository;

    @Autowired
    private ExcelImageService excelImageService;

    @Value("${app.file.export.kiemhong}")
    private String kiemHongPrefix;

    @Value("${app.file.export.dathang}")
    private String datHangPrefix;

    @Value("${app.file.export.pa}")
    private String paPrefix;

    @Value("${app.file.export.cntp}")
    private String cntpPrefix;

    @Override
    public void exportFile(Long requestId, RequestType requestType, OutputStream outputStream) {
        FileInputStream fis = null;
        try {
            if (requestType == RequestType.KIEM_HONG) {
                fis = new FileInputStream(new File(this.getClass().getResource("/templates/1_Kiem_Hong.xlsx").getFile()));
                exportKiemHong(fis, outputStream, kiemHongService.findThongTinKiemHong(1L, requestId));
            } else if (requestType == RequestType.DAT_HANG) {
                fis = new FileInputStream(new File(this.getClass().getResource("/templates/2_Dat_Hang.xlsx").getFile()));
                exportPHieuDatHang(fis, outputStream, phieuDatHangService.findById(1L, requestId), mdsdMap());
            } else if (requestType == RequestType.PHUONG_AN) {
                fis = new FileInputStream(new File(this.getClass().getResource("/templates/3_phuong_an.xlsx").getFile()));
                exportPhuongAn(fis, outputStream, phuongAnService.findById(1L, requestId));
            } else if (requestType == RequestType.CONG_NHAN_THANH_PHAM) {
                fis = new FileInputStream(new File(this.getClass().getResource("/templates/4_cntp.xlsx").getFile()));
                exportCNTP(fis, outputStream, congNhanThanhPhamService.timCongNhanThanhPham(1L, requestId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PXException("File not found");
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void exportKiemHong(InputStream fis, OutputStream response, KiemHongPayLoad payload) {
        DocumentFactory.get(RequestType.KIEM_HONG).export(fis, response, payload);
    }

    private void exportPHieuDatHang(InputStream is, OutputStream outputStream, PhieuDatHangPayload payload, Map<Long, String> mdsdNameById) {
        DocumentFactory.get(RequestType.DAT_HANG).export(is, outputStream, payload);
    }

    public void exportCNTP(InputStream fis, OutputStream outputStream, CongNhanThanhPhamPayload payload) {
        DocumentFactory.get(RequestType.CONG_NHAN_THANH_PHAM).export(fis, outputStream, payload);
//        Map<Long, User> userById = userService.userById();
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(fis);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            XSSFRow row0 = sheet.getRow(2);
//            XSSFRow row1 = sheet.getRow(3);
//            XSSFRow row2 = sheet.getRow(4);
//            XSSFRow row3 = sheet.getRow(5);
//            XSSFRow row4 = sheet.getRow(6);
//            XSSFRow row5 = sheet.getRow(7);
//
//            XSSFRow row19 = sheet.getRow(19);
//
//            setCellVal(row0, 1, payload.getTenSanPham());
//            setCellVal(row1, 1, payload.getNoiDung());
//            setCellVal(row2, 1, payload.getSoPA());
//            setCellVal(row3, 1, payload.getDonviThucHien());
//            setCellVal(row3, 4, payload.getTo());
//            setCellVal(row4, 1, payload.getDonviDatHang());
//            setCellVal(row4, 3, payload.getSoLuong());
//            setCellVal(row4, 5, payload.getDvt());
//            setCellVal(row5, 1, payload.getSoNghiemThuDuoc());
//            setCellVal(row19, 1, payload.getLaoDongTienLuong().toString());
//            setCellVal(row19, 3, payload.getGioX().toString());
//            setCellVal(row19, 5, payload.getDong().toString());
//
//            int totalLine = payload.getNoiDungThucHiens().size();
//            if (totalLine > 5) {
//                sheet.copyRows(18, 33, 33 + (totalLine - 6), new CellCopyPolicy()); // copy and paste
//
//                for (int i = 18; i < 33 + (totalLine - 6); i++) {
//                    sheet.createRow(i);
//                    sheet.copyRows(12, 12, i - 1, new CellCopyPolicy()); // copy and paste
//                }
//            }
//            // NOTE: update print anh chu ky
//            int ngayThangNamTPKCS = 21;
//            int signImgTPKCS = 24;
//            int fullNameTPKCS = 24;
//
//
//            int ngayThangNamRow = 27;
//            int toTuongAlias = 28;
//            int imgRow = 30; // row nay bat dau tu 1 nen khong giong index ben tren phai -1
//            int fullNameRow = 30;
//            if (totalLine > 5) {
//                int so_dong_lech_noi_dung_thuc_hien = totalLine - 5 + 14;
//                // 18 la so line data fixed , da cho ban dau
//                // 4 la so dong thua , sau khi clone o step ben tren
//                imgRow += so_dong_lech_noi_dung_thuc_hien;
//                ngayThangNamRow += so_dong_lech_noi_dung_thuc_hien;
//                fullNameRow += so_dong_lech_noi_dung_thuc_hien;
//                toTuongAlias += so_dong_lech_noi_dung_thuc_hien;
//
//                ngayThangNamTPKCS += so_dong_lech_noi_dung_thuc_hien;
//                signImgTPKCS += so_dong_lech_noi_dung_thuc_hien;
//                fullNameTPKCS += so_dong_lech_noi_dung_thuc_hien;
//            }
//
//            if (payload.getTpkcsXacNhan()) {
//                setCellVal(sheet.getRow(ngayThangNamTPKCS), 4, payload.getNgayThangNamTPKCS());
//                setCellVal(sheet.getRow(fullNameTPKCS), 4, payload.getTpkcsFullName());
//                excelImageService.addImageToSheet("E" + signImgTPKCS, sheet, imageData(payload.getTpkcsSignImg()));
//            }
//            if (payload.getToTruong1XacNhan()) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 0, payload.getNgayThangNamToTruong1());
//                setCellVal(sheet.getRow(fullNameRow), 0, payload.getToTruong1fullName());
//                setCellVal(sheet.getRow(toTuongAlias), 0, payload.getToTruong1Alias());
//                excelImageService.addImageToSheet("A" + imgRow, sheet, imageData(payload.getToTruong1SignImg()));
//            }
//            if (Objects.nonNull(payload.getToTruong2Id())) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 1, payload.getNgayThangNamToTruong2());
//                setCellVal(sheet.getRow(fullNameRow), 1, payload.getToTruong2fullName());
//                setCellVal(sheet.getRow(toTuongAlias), 1, payload.getToTruong2Alias());
//                excelImageService.addImageToSheet("B" + imgRow, sheet, imageData(payload.getToTruong2SignImg()));
//            }
//            if (Objects.nonNull(payload.getToTruong3Id())) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 2, payload.getNgayThangNamToTruong3());
//                setCellVal(sheet.getRow(fullNameRow), 2, payload.getToTruong3fullName());
//                setCellVal(sheet.getRow(toTuongAlias), 2, payload.getToTruong3Alias());
//                excelImageService.addImageToSheet("C" + imgRow, sheet, imageData(payload.getToTruong3SignImg()));
//            }
//            if (Objects.nonNull(payload.getToTruong4Id())) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 3, payload.getNgayThangNamToTruong4());
//                setCellVal(sheet.getRow(fullNameRow), 3, payload.getToTruong4fullName());
//                setCellVal(sheet.getRow(toTuongAlias), 3, payload.getToTruong4Alias());
//                excelImageService.addImageToSheet("D" + imgRow, sheet, imageData(payload.getToTruong4SignImg()));
//            }
//            if (Objects.nonNull(payload.getToTruong5Id())) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 4, payload.getNgayThangNamToTruong5());
//                setCellVal(sheet.getRow(fullNameRow), 4, payload.getToTruong5fullName());
//                setCellVal(sheet.getRow(toTuongAlias), 4, payload.getToTruong5Alias());
//                excelImageService.addImageToSheet("E" + imgRow, sheet, imageData(payload.getToTruong5SignImg()));
//            }
//
//            for (int i = 0; i < totalLine; i++) {
//                XSSFRow crrRow = sheet.getRow(11 + i);
//                setCellVal(crrRow, 0, payload.getNoiDungThucHiens().get(i).getNoiDung());
//                setCellVal(crrRow, 3, payload.getNoiDungThucHiens().get(i).getKetQua());
//                setCellVal(crrRow, 4, payload.getNoiDungThucHiens().get(i).getNguoiLam());
//                setCellVal(crrRow, 5, fillUserInfo(payload.getNoiDungThucHiens().get(i).getNghiemThu(), userById));
//                excelImageService.addImageToSheet("G" + (12 + i), sheet, imageData(payload.getNoiDungThucHiens().get(i).getSignImg()));
//            }
//            workbook.write(outputStream);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                outputStream.flush();
//            } catch (IOException e) {
//            }
//        }
    }

    public void exportPhuongAn(InputStream fis, OutputStream outputStream, PhuongAnPayload payload) {
        DocumentFactory.get(RequestType.PHUONG_AN).export(fis, outputStream, payload);
//        Map<Long, User> userById = userService.userById();
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(fis);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//
//            XSSFRow row1 = sheet.getRow(1);
//            XSSFRow row2 = sheet.getRow(2);
//            XSSFRow row3 = sheet.getRow(3);
//            XSSFRow row4 = sheet.getRow(4);
//            XSSFRow row5 = sheet.getRow(5);
//            XSSFRow row15 = sheet.getRow(15);
//            XSSFRow row29 = sheet.getRow(29);
//            XSSFRow row30 = sheet.getRow(30);
//
//            setCellVal(row1, 13, payload.getToSo());
//            setCellVal(row2, 13, payload.getSoTo());
//            setCellVal(row2, 6, payload.getMaSo());
//            setCellVal(row3, 13, payload.getPDH());
//            setCellVal(row3, 6, payload.getSanPham());
//            setCellVal(row4, 6, payload.getNoiDung());
//            setCellVal(row5, 6, payload.getNguonKinhPhi());
//            setCellVal(row15, 11, payload.getTongCongDinhMucLaoDong().toString());
//
//            setCellVal(row29, 9, payload.getTongDMVTKho().toString());
//            setCellVal(row29, 12, payload.getTongDMVTMuaNgoai().toString());
//            setCellVal(row30, 2, payload.getTienLuong().toString());
//
////
//
//            int totalLine = payload.getDinhMucLaoDongs().size();
//            int startFix1 = 15;
//            int endFix1 = 41;
//            if (totalLine > 5) {
//                sheet.copyRows(startFix1, endFix1, endFix1 + (totalLine - 6), new CellCopyPolicy()); // copy and paste
//
//                for (int i = startFix1; i < endFix1 + (totalLine - 6); i++) {
//                    sheet.createRow(i);
//                    sheet.copyRows(9, 9, i - 1, new CellCopyPolicy()); // copy and paste
//                }
//            }
//
//            for (int i = 0; i < payload.getDinhMucLaoDongs().size(); i++) {
//                XSSFRow crrRow = sheet.getRow(9 + i);
//                setCellVal(crrRow, 0, i + 1 + "");
//                setCellVal(crrRow, 1, payload.getDinhMucLaoDongs().get(i).getNoiDungCongViec());
//                setCellVal(crrRow, 10, payload.getDinhMucLaoDongs().get(i).getBacCV());
//                setCellVal(crrRow, 11, payload.getDinhMucLaoDongs().get(i).getDm());
//                setCellVal(crrRow, 12, payload.getDinhMucLaoDongs().get(i).getGhiChu());
//            }
//            // NOTE: soDongBiLech la so dong da bi thay doi cua DINH_MUC_LAO_DONG
//            int soDongBiLech = (totalLine > 5 ? totalLine + 20 : 0);  // so dong bi lech phai la 28
//            int startFix2 = 29 + soDongBiLech;
//            int endFix2 = 41 + soDongBiLech;
//            int totalLine2 = payload.getDinhMucVatTus().size();
//            int row_mau = 20 + soDongBiLech; // row bat dau co data o dinh_muc_vat_tu
//            if (totalLine2 > 9) {
//                sheet.copyRows(startFix2, endFix2, endFix2 + (totalLine2 - 9), new CellCopyPolicy()); // copy and paste
//
//                for (int i = startFix2; i < endFix2 + (totalLine2 - 9); i++) {
//                    sheet.createRow(i);
//                    sheet.copyRows(row_mau, row_mau, i - 1, new CellCopyPolicy()); // copy and paste
//                }
//            }
////
////            // NOTE: update print anh chu ky
//            int cusNoiNhan = 32;
//            int ngayThangNamRow = 33;
//            int imgRow = 36; // row nay bat dau tu 1 nen khong giong index ben tren phai -1
//            int fullNameRow = 36;
//
//            if (soDongBiLech > 0) {
//                // 18 la so line data fixed , da cho ban dau
//                // 4 la so dong thua , sau khi clone o step ben tren
//                int lech_cua_dinh_muc_lao_dong = (totalLine - 6) + 26;
//                cusNoiNhan = cusNoiNhan + lech_cua_dinh_muc_lao_dong;
//                imgRow = imgRow + lech_cua_dinh_muc_lao_dong;
//                ngayThangNamRow = ngayThangNamRow + lech_cua_dinh_muc_lao_dong;
//                fullNameRow = fullNameRow + lech_cua_dinh_muc_lao_dong;
//            }
//            if (totalLine2 > 9) {
//                int lech_cua_dinh_muc_vat_tu = totalLine2 - 9 + 12;
//                cusNoiNhan = cusNoiNhan + lech_cua_dinh_muc_vat_tu;
//                imgRow = imgRow + lech_cua_dinh_muc_vat_tu;
//                ngayThangNamRow = ngayThangNamRow + lech_cua_dinh_muc_vat_tu;
//                fullNameRow = fullNameRow + lech_cua_dinh_muc_vat_tu;
//            }
//            setCellVal(sheet.getRow(cusNoiNhan), 1, getNoiNhan(userById, payload.getCusReceivers()));
//            if (payload.getNguoiLapXacNhan()) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 12, payload.getNgayThangNamNguoiLap());
//                setCellVal(sheet.getRow(fullNameRow), 12, payload.getNguoiLapFullName());
//                excelImageService.addImageToSheet("M" + imgRow, sheet, imageData(payload.getNguoiLapSignImg()));
//            }
//            if (payload.getTruongPhongVatTuXacNhan()) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 8, payload.getNgayThangNamtpVatTu());
//                setCellVal(sheet.getRow(fullNameRow), 8, payload.getTruongPhongVatTuFullName());
//                excelImageService.addImageToSheet("I" + imgRow, sheet, imageData(payload.getTruongPhongVatTuSignImg()));
//            }
//            if (payload.getTruongPhongKeHoachXacNhan()) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 3, payload.getNgayThangNamTPKEHOACH());
//                setCellVal(sheet.getRow(fullNameRow), 3, payload.getTruongPhongKeHoachFullName());
//                excelImageService.addImageToSheet("D" + imgRow, sheet, imageData(payload.getTruongPhongKeHoachSignImg()));
//            }
//            if (payload.getTruongPhongKTHKXacNhan()) {
//                setCellVal(sheet.getRow(ngayThangNamRow), 1, payload.getNgayThangNamTPKTHK());
//                setCellVal(sheet.getRow(fullNameRow), 1, payload.getTruongPhongKTHKFullName());
//                excelImageService.addImageToSheet("B" + imgRow, sheet, imageData(payload.getTruongPhongKTHKSignImg()));
//            }
//            if (payload.getGiamDocXacNhan()) {
//                setCellVal(sheet.getRow(3), 0, payload.getNgayThangNamGiamDoc());
//                setCellVal(sheet.getRow(6), 1, payload.getGiamDocFullName());
//                excelImageService.addImageToSheet("B6", sheet, imageData(payload.getGiamDocSignImg()));
//            }
//
//            // dang in o dong 35 => 34
//            // expect 49 => 48
//            for (int i = 0; i < payload.getDinhMucVatTus().size(); i++) {
//                XSSFRow crrRow2 = sheet.getRow(row_mau + i);
//                setCellVal(crrRow2, 0, i + 1 + "");
//                setCellVal(crrRow2, 1, payload.getDinhMucVatTus().get(i).getTenVatTuKyThuat());
//                setCellVal(crrRow2, 2, payload.getDinhMucVatTus().get(i).getKyMaKyHieu());
//                setCellVal(crrRow2, 3, payload.getDinhMucVatTus().get(i).getDvt());
//                setCellVal(crrRow2, 4, payload.getDinhMucVatTus().get(i).getDm1SP());
//                setCellVal(crrRow2, 5, payload.getDinhMucVatTus().get(i).getSoLuongSanPham());
//                setCellVal(crrRow2, 6, payload.getDinhMucVatTus().get(i).getTongNhuCau());
//                setCellVal(crrRow2, 7, payload.getDinhMucVatTus().get(i).getKhoDonGia());
//                setCellVal(crrRow2, 8, payload.getDinhMucVatTus().get(i).getKhoSoLuong());
//                setCellVal(crrRow2, 9, payload.getDinhMucVatTus().get(i).getKhoThanhTien());
//                setCellVal(crrRow2, 10, payload.getDinhMucVatTus().get(i).getMnDonGia());
//                setCellVal(crrRow2, 11, payload.getDinhMucVatTus().get(i).getMnSoLuong());
//                setCellVal(crrRow2, 12, payload.getDinhMucVatTus().get(i).getMnThanhTien());
//                setCellVal(crrRow2, 13, payload.getDinhMucVatTus().get(i).getGhiChu());
//            }
//            workbook.write(outputStream);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                outputStream.flush();
//            } catch (IOException e) {
//            }
//        }
    }

    @Override
    public void exports(Long startDate, Long endDate) {
        mkdirs(base_dir_kiem_hong);
        mkdirs(base_dir_dat_hang);
        mkdirs(base_dir_pa);
        mkdirs(base_dir_cntp);

        Page<Request> page = requestRepository.findPaging(of(0, Integer.MAX_VALUE), startDate, endDate);
        Map<Long, User> userById = userService.userById();
        try {
            if (!page.isEmpty()) {
                Map<Long, String> mdsdNameById = mdsdMap();
                for (Request request : page) {
                    if (request.getKiemHong().allApproved()) {
                        try (FileOutputStream os = new FileOutputStream(base_dir_kiem_hong + kiemHongPrefix + request.getKiemHong().getKhId() + ".xlsx");
                             FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/1_Kiem_Hong.xlsx"))) {
                            KiemHongPayLoad payload = KiemHongPayLoad
                                    .fromEntity(request.getKiemHong())
                                    .andRequestId(request.getRequestId());
                            payload.processSignImgAndFullName(userById);

                            exportKiemHong(fis, os, payload);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (request.getPhieuDatHang().allApproved()) {
                        try (FileOutputStream os = new FileOutputStream(base_dir_dat_hang + datHangPrefix + CommonUtils.removeAllSpecialCharacters(request.getPhieuDatHang().getSo()) + ".xlsx");
                             FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/2_Dat_Hang.xlsx"))) {
                            PhieuDatHangPayload payload = PhieuDatHangPayload
                                    .fromEntity(request.getPhieuDatHang());

                            payload.setRequestId(request.getRequestId());
                            payload.processSignImgAndFullName(userById);
                            exportPHieuDatHang(fis, os, payload, mdsdNameById);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            // TODO: find pa.
            List<PhuongAn> pas = phuongAnRepository.findAll();
            if (!CollectionUtils.isEmpty(pas)) {
                for (PhuongAn pa : pas) {
                    if (pa.allApproved()) {
                        try (FileOutputStream os = new FileOutputStream(base_dir_pa + paPrefix + CommonUtils.removeAllSpecialCharacters(pa.getMaSo()) + ".xlsx");
                             FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/3_phuong_an.xlsx"))) {
                            logger.info("Exporting PA with id: {}", pa.getPaId());
                            PhuongAnPayload payload = PhuongAnPayload.fromEntity(pa);
                            payload.setRequestId(pa.getPaId());
                            payload.processSignImgAndFullName(userById);
                            exportPhuongAn(fis, os, payload);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            // TODO: find CNTP
            List<CongNhanThanhPham> cntps = congNhanThanhPhamRepository.findAll();
            for (CongNhanThanhPham cntp : cntps) {
                if (cntp.allApproved()) {
                    try (FileOutputStream os = new FileOutputStream(base_dir_cntp + cntpPrefix + cntp.getTpId() + ".xlsx");
                         FileInputStream fis = new FileInputStream(new File("./src/main/resources/templates/4_cntp.xlsx"))) {
                        logger.info("Exporting CNTP with id: {}", cntp.getTpId());
                        CongNhanThanhPhamPayload payload = CongNhanThanhPhamPayload.fromEntity(cntp);
                        payload.setRequestId(cntp.getTpId());
                        payload.processSignImgAndFullName(userById);
                        exportCNTP(fis, os, payload);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void mkdirs(String dirPath) {
        try {
            Path path = Paths.get(dirPath);
            //if directory exists?
            if (!Files.exists(path)) Files.createDirectories(path);
        } catch (Exception e) {
            //fail to create directory
            e.printStackTrace();
        }

    }


}
