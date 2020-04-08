package com.px.tool.domain.excel;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.utils.ExcelImageService;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class PhuongAnExporter extends DocumentExporter<PhuongAnPayload> {

    @Autowired
    public PhuongAnExporter(ExcelImageService excelImageService, UserService userService) {
        super(excelImageService, userService);
    }

    @Override
    @PostConstruct
    protected void register() {
        DocumentFactory.register(RequestType.PHUONG_AN, this);
    }

    @Override
    protected int totalLines() {
        return getPayload().getDinhMucLaoDongs().size();
    }

    @Override
    protected void writeFixedData() {
        XSSFRow row1 = getSheet().getRow(1);
        XSSFRow row2 = getSheet().getRow(2);
        XSSFRow row3 = getSheet().getRow(3);
        XSSFRow row4 = getSheet().getRow(4);
        XSSFRow row5 = getSheet().getRow(5);
        XSSFRow row15 = getSheet().getRow(15);
        XSSFRow row29 = getSheet().getRow(29);
        XSSFRow row30 = getSheet().getRow(30);

        setCellVal(row1, 13, getPayload().getToSo());
        setCellVal(row2, 13, getPayload().getSoTo());
        setCellVal(row2, 6, getPayload().getMaSo());
        setCellVal(row3, 13, getPayload().getPDH());
        setCellVal(row3, 6, getPayload().getSanPham());
        setCellVal(row4, 6, getPayload().getNoiDung());
        setCellVal(row5, 6, getPayload().getNguonKinhPhi());

        setCellVal(row15, 11, getPayload().getTongCongDinhMucLaoDong().toString());
        setCellVal(row29, 9, getPayload().getTongDMVTKho().toString());
        setCellVal(row29, 12, getPayload().getTongDMVTMuaNgoai().toString());
        setCellVal(row30, 2, getPayload().getTienLuong().toString());

        int totalLine = getPayload().getDinhMucLaoDongs().size();
        int startFix1 = 15;
        int endFix1 = 41;
        if (totalLine > 5) {
            getSheet().copyRows(startFix1, endFix1, endFix1 + (totalLine - 6), new CellCopyPolicy()); // copy and paste

            for (int i = startFix1; i < endFix1 + (totalLine - 6); i++) {
                getSheet().createRow(i);
                getSheet().copyRows(9, 9, i - 1, new CellCopyPolicy()); // copy and paste
            }
        }

        for (int i = 0; i < getPayload().getDinhMucLaoDongs().size(); i++) {
            XSSFRow crrRow = getSheet().getRow(9 + i);
            setCellVal(crrRow, 0, i + 1 + "");
            setCellVal(crrRow, 1, getPayload().getDinhMucLaoDongs().get(i).getNoiDungCongViec());
            setCellVal(crrRow, 10, getPayload().getDinhMucLaoDongs().get(i).getBacCV());
            setCellVal(crrRow, 11, getPayload().getDinhMucLaoDongs().get(i).getDm());
            setCellVal(crrRow, 12, getPayload().getDinhMucLaoDongs().get(i).getGhiChu());
        }

    }

    private int soDongBiLech() {
        return (totalLines() > 5 ? totalLines() + 20 : 0);  // so dong bi lech phai la 28
    }

    private int totalLine2() {
        return getPayload().getDinhMucVatTus().size();
    }

    private int row_mau() {
        return 20 + soDongBiLech(); // row bat dau co data o dinh_muc_vat_tu
    }

    @Override
    protected void copyRows() {
        // NOTE: soDongBiLech la so dong da bi thay doi cua DINH_MUC_LAO_DONG
        int startFix2 = 29 + soDongBiLech();
        int endFix2 = 41 + soDongBiLech();
        int totalLine2 = getPayload().getDinhMucVatTus().size();
//        int row_mau = 20 + soDongBiLech(); // row bat dau co data o dinh_muc_vat_tu
        if (totalLine2 > 9) {
            getSheet().copyRows(startFix2, endFix2, endFix2 + (totalLine2 - 9), new CellCopyPolicy()); // copy and paste

            for (int i = startFix2; i < endFix2 + (totalLine2 - 9); i++) {
                getSheet().createRow(i);
                getSheet().copyRows(row_mau(), row_mau(), i - 1, new CellCopyPolicy()); // copy and paste
            }
        }
    }

    @Override
    protected void writeDynamicData() {
// dang in o dong 35 => 34
        // expect 49 => 48
        for (int i = 0; i < getPayload().getDinhMucVatTus().size(); i++) {
            XSSFRow crrRow2 = getSheet().getRow(row_mau() + i);
            setCellVal(crrRow2, 0, i + 1 + "");
            setCellVal(crrRow2, 1, getPayload().getDinhMucVatTus().get(i).getTenVatTuKyThuat());
            setCellVal(crrRow2, 2, getPayload().getDinhMucVatTus().get(i).getKyMaKyHieu());
            setCellVal(crrRow2, 3, getPayload().getDinhMucVatTus().get(i).getDvt());
            setCellVal(crrRow2, 4, getPayload().getDinhMucVatTus().get(i).getDm1SP());
            setCellVal(crrRow2, 5, getPayload().getDinhMucVatTus().get(i).getSoLuongSanPham());
            setCellVal(crrRow2, 6, getPayload().getDinhMucVatTus().get(i).getTongNhuCau());
            setCellVal(crrRow2, 7, getPayload().getDinhMucVatTus().get(i).getKhoDonGia());
            setCellVal(crrRow2, 8, getPayload().getDinhMucVatTus().get(i).getKhoSoLuong());
            setCellVal(crrRow2, 9, getPayload().getDinhMucVatTus().get(i).getKhoThanhTien());
            setCellVal(crrRow2, 10, getPayload().getDinhMucVatTus().get(i).getMnDonGia());
            setCellVal(crrRow2, 11, getPayload().getDinhMucVatTus().get(i).getMnSoLuong());
            setCellVal(crrRow2, 12, getPayload().getDinhMucVatTus().get(i).getMnThanhTien());
            setCellVal(crrRow2, 13, getPayload().getDinhMucVatTus().get(i).getGhiChu());
        }
    }

    @Override
    protected void writePhanChuKy() throws IOException {
//            // NOTE: update print anh chu ky
        int cusNoiNhan = 32;
        int ngayThangNamRow = 33;
        int imgRow = 36; // row nay bat dau tu 1 nen khong giong index ben tren phai -1
        int fullNameRow = 36;

        if (soDongBiLech() > 0) {
            // 18 la so line data fixed , da cho ban dau
            // 4 la so dong thua , sau khi clone o step ben tren
            int lech_cua_dinh_muc_lao_dong = (getPayload().getDinhMucLaoDongs().size() - 6) + 26;
            cusNoiNhan = cusNoiNhan + lech_cua_dinh_muc_lao_dong;
            imgRow = imgRow + lech_cua_dinh_muc_lao_dong;
            ngayThangNamRow = ngayThangNamRow + lech_cua_dinh_muc_lao_dong;
            fullNameRow = fullNameRow + lech_cua_dinh_muc_lao_dong;
        }
        if (totalLine2() > 9) {
            int lech_cua_dinh_muc_vat_tu = totalLine2() - 9 + 12;
            cusNoiNhan = cusNoiNhan + lech_cua_dinh_muc_vat_tu;
            imgRow = imgRow + lech_cua_dinh_muc_vat_tu;
            ngayThangNamRow = ngayThangNamRow + lech_cua_dinh_muc_vat_tu;
            fullNameRow = fullNameRow + lech_cua_dinh_muc_vat_tu;
        }
        setCellVal(getSheet().getRow(cusNoiNhan), 1, getNoiNhan(userById(), getPayload().getCusReceivers()));
        if (getPayload().getNguoiLapXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 12, getPayload().getNgayThangNamNguoiLap());
            setCellVal(getSheet().getRow(fullNameRow), 12, getPayload().getNguoiLapFullName());
            excelImageService.addImageToSheet("M" + imgRow, getSheet(), imageData(getPayload().getNguoiLapSignImg()));
        }
        if (getPayload().getTruongPhongVatTuXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 8, getPayload().getNgayThangNamtpVatTu());
            setCellVal(getSheet().getRow(fullNameRow), 8, getPayload().getTruongPhongVatTuFullName());
            excelImageService.addImageToSheet("I" + imgRow, getSheet(), imageData(getPayload().getTruongPhongVatTuSignImg()));
        }
        if (getPayload().getTruongPhongKeHoachXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 3, getPayload().getNgayThangNamTPKEHOACH());
            setCellVal(getSheet().getRow(fullNameRow), 3, getPayload().getTruongPhongKeHoachFullName());
            excelImageService.addImageToSheet("D" + imgRow, getSheet(), imageData(getPayload().getTruongPhongKeHoachSignImg()));
        }
        if (getPayload().getTruongPhongKTHKXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 1, getPayload().getNgayThangNamTPKTHK());
            setCellVal(getSheet().getRow(fullNameRow), 1, getPayload().getTruongPhongKTHKFullName());
            excelImageService.addImageToSheet("B" + imgRow, getSheet(), imageData(getPayload().getTruongPhongKTHKSignImg()));
        }
        if (getPayload().getGiamDocXacNhan()) {
            setCellVal(getSheet().getRow(3), 0, getPayload().getNgayThangNamGiamDoc());
            setCellVal(getSheet().getRow(6), 1, getPayload().getGiamDocFullName());
            excelImageService.addImageToSheet("B6", getSheet(), imageData(getPayload().getGiamDocSignImg()));
        }
    }
}
