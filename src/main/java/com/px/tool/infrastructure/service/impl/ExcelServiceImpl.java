package com.px.tool.infrastructure.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
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
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class ExcelServiceImpl extends BaseServiceImpl implements ExcelService {
    private static final String base_dir_kiem_hong = "/home/nghiapt/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/kiem_hong/";
    private static final String base_dir_dat_hang = "/home/nghiapt/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/dat_hang/";
    private static final String base_dir_pa = "/home/nghiapt/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/phuong_an/";
    private static final String base_dir_cntp = "/home/nghiapt/source/thu_muc_cap_nhat_phan_mem/PhuongAnSo_thong_ke/cntp/";

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
                fis = new FileInputStream(new File("./src/main/resources/templates/1_Kiem_Hong.xlsx"));
                exportKiemHong(fis, outputStream, kiemHongService.findThongTinKiemHong(1L, requestId));
            } else if (requestType == RequestType.DAT_HANG) {
                fis = new FileInputStream(new File("./src/main/resources/templates/2_Dat_Hang.xlsx"));
                exportPHieuDatHang(fis, outputStream, phieuDatHangService.findById(1L, requestId), mdsdMap());
            } else if (requestType == RequestType.PHUONG_AN) {
                fis = new FileInputStream(new File("./src/main/resources/templates/3_phuong_an.xlsx"));
                exportPhuongAn(fis, outputStream, phuongAnService.findById(1L, requestId));
            } else if (requestType == RequestType.CONG_NHAN_THANH_PHAM) {
                fis = new FileInputStream(new File("./src/main/resources/templates/4_cntp.xlsx"));
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
        Map<Long, User> userById = userService.userById();
        try {
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
            workbook.write(response);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            response.flush();
        } catch (IOException e) {
        }
    }

    private void exportPHieuDatHang(InputStream is, OutputStream outputStream, PhieuDatHangPayload payload, Map<Long, String> mdsdNameById) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
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
                setCellVal(crrRow, 6, getVal(mdsdNameById, payload.getPhieuDatHangDetails().get(i).getMucDichSuDung()));
                setCellVal(crrRow, 7, payload.getPhieuDatHangDetails().get(i).getPhuongPhapKhacPhuc());
                setCellVal(crrRow, 8, payload.getPhieuDatHangDetails().get(i).getSoPhieuDatHang());
                setCellVal(crrRow, 9, payload.getPhieuDatHangDetails().get(i).getNguoiThucHien());
            }
            workbook.write(outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                outputStream.flush();
            } catch (IOException e) {
            }
        }
    }

    public void exportCNTP(InputStream fis, OutputStream outputStream, CongNhanThanhPhamPayload payload) {
        Map<Long, User> userById = userService.userById();
        try {
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
                setCellVal(row25, 1, payload.getNgayThangNamToTruong2());
                setCellVal(row26, 1, payload.getToTruong2fullName());
            }
            if (Objects.nonNull(payload.getToTruong3Id())) {
                setCellVal(row25, 2, payload.getNgayThangNamToTruong3());
                setCellVal(row26, 2, payload.getToTruong3fullName());
            }
            if (Objects.nonNull(payload.getToTruong4Id())) {
                setCellVal(row25, 3, payload.getNgayThangNamToTruong4());
                setCellVal(row26, 3, payload.getToTruong4fullName());
            }
            if (Objects.nonNull(payload.getToTruong5Id())) {
                setCellVal(row25, 4, payload.getNgayThangNamToTruong5());
                setCellVal(row26, 4, payload.getToTruong5fullName());
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
            workbook.write(outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                outputStream.flush();
            } catch (IOException e) {
            }
        }
    }

    public void exportPhuongAn(InputStream fis, OutputStream outputStream, PhuongAnPayload payload) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow row1 = sheet.getRow(1);
            XSSFRow row2 = sheet.getRow(2);
            XSSFRow row3 = sheet.getRow(3);
            XSSFRow row4 = sheet.getRow(4);
            XSSFRow row5 = sheet.getRow(5);
            XSSFRow row15 = sheet.getRow(15);
            XSSFRow row29 = sheet.getRow(29);
            XSSFRow row30 = sheet.getRow(30);
            XSSFRow row32 = sheet.getRow(32);

            setCellVal(row1, 13, payload.getToSo());
            setCellVal(row2, 13, payload.getSoTo());
            setCellVal(row2, 6, payload.getMaSo());
            setCellVal(row3, 13, payload.getPDH());
            setCellVal(row3, 6, payload.getSanPham());
            setCellVal(row4, 6, payload.getNoiDung());
            setCellVal(row5, 6, payload.getNguonKinhPhi());
            setCellVal(row15, 2, payload.getTongCongDinhMucLaoDong().toString());

            setCellVal(row3, 0, payload.getNgayThangNamGiamDoc());
            setCellVal(row32, 1, payload.getNgayThangNamTPKTHK());
            setCellVal(row32, 3, payload.getNgayThangNamTPKEHOACH());
            setCellVal(row32, 8, payload.getNgayThangNamtpVatTu());
            setCellVal(row32, 12, payload.getNgayThangNamNguoiLap());

            setCellVal(row29, 9, payload.getTongDMVTKho().toString());
            setCellVal(row29, 13, payload.getTongDMVTMuaNgoai().toString());
            setCellVal(row30, 2, payload.getTienLuong().toString());

//

            int totalLine = payload.getDinhMucLaoDongs().size();
            int startFix1 = 15;
            int endFix1 = 35;
            if (totalLine > 5) {
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
            workbook.write(outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                outputStream.flush();
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


    private Map<Long, String> mdsdMap() {
        return mucDichSuDungRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(MucDichSuDung::getMdId, e -> e.getTen()));
    }

    private String getVal(Map<Long, String> map, Long key) {
        if (key == null) {
            return "";
        }
        if (map.containsKey(key)) {
            return map.get(key);
        } else return "";
    }
}
