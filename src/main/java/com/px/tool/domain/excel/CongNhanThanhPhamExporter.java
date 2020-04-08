package com.px.tool.domain.excel;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.utils.ExcelImageService;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;

@Component
public class CongNhanThanhPhamExporter extends DocumentExporter<CongNhanThanhPhamPayload> {

    @Autowired
    public CongNhanThanhPhamExporter(ExcelImageService excelImageService, UserService userService) {
        super(excelImageService, userService);
    }

    @Override
    @PostConstruct
    protected void register() {
        DocumentFactory.register(RequestType.CONG_NHAN_THANH_PHAM, this);
    }

    @Override
    protected int totalLines() {
        return this.getPayload().getNoiDungThucHiens().size();
    }

    @Override
    protected void writeFixedData() {
        XSSFRow row0 = getSheet().getRow(2);
        XSSFRow row1 = getSheet().getRow(3);
        XSSFRow row2 = getSheet().getRow(4);
        XSSFRow row3 = getSheet().getRow(5);
        XSSFRow row4 = getSheet().getRow(6);
        XSSFRow row5 = getSheet().getRow(7);

        XSSFRow row19 = getSheet().getRow(19);

        setCellVal(row0, 1, getPayload().getTenSanPham());
        setCellVal(row1, 1, getPayload().getNoiDung());
        setCellVal(row2, 1, getPayload().getSoPA());
        setCellVal(row3, 1, getPayload().getDonviThucHien());
        setCellVal(row3, 4, getPayload().getTo());
        setCellVal(row4, 1, getPayload().getDonviDatHang());
        setCellVal(row4, 3, getPayload().getSoLuong());
        setCellVal(row4, 5, getPayload().getDvt());
        setCellVal(row5, 1, getPayload().getSoNghiemThuDuoc());
        setCellVal(row19, 1, getPayload().getLaoDongTienLuong().toString());
        setCellVal(row19, 3, getPayload().getGioX().toString());
        setCellVal(row19, 5, getPayload().getDong().toString());
    }

    @Override
    protected void copyRows() {
        int totalLine = getPayload().getNoiDungThucHiens().size();
        if (totalLine > 5) {
            getSheet().copyRows(18, 33, 33 + (totalLine - 6), new CellCopyPolicy()); // copy and paste

            for (int i = 18; i < 33 + (totalLine - 6); i++) {
                getSheet().createRow(i);
                getSheet().copyRows(12, 12, i - 1, new CellCopyPolicy()); // copy and paste
            }
        }
    }

    @Override
    protected void writeDynamicData() throws IOException {
        for (int i = 0; i < totalLines(); i++) {
            XSSFRow crrRow = getSheet().getRow(11 + i);
            setCellVal(crrRow, 0, getPayload().getNoiDungThucHiens().get(i).getNoiDung());
            setCellVal(crrRow, 3, getPayload().getNoiDungThucHiens().get(i).getKetQua());
            setCellVal(crrRow, 4, getPayload().getNoiDungThucHiens().get(i).getNguoiLam());
            setCellVal(crrRow, 5, fillUserInfo(getPayload().getNoiDungThucHiens().get(i).getNghiemThu(), userById()));
            excelImageService.addImageToSheet("G" + (12 + i), getSheet(), imageData(getPayload().getNoiDungThucHiens().get(i).getSignImg()));
        }
    }

    @Override
    protected void writePhanChuKy() throws IOException {
// NOTE: update print anh chu ky
        int ngayThangNamTPKCS = 21;
        int signImgTPKCS = 24;
        int fullNameTPKCS = 24;


        int ngayThangNamRow = 27;
        int toTuongAlias = 28;
        int imgRow = 30; // row nay bat dau tu 1 nen khong giong index ben tren phai -1
        int fullNameRow = 30;
        if (totalLines() > 5) {
            int so_dong_lech_noi_dung_thuc_hien = totalLines() - 5 + 14;
            // 18 la so line data fixed , da cho ban dau
            // 4 la so dong thua , sau khi clone o step ben tren
            imgRow += so_dong_lech_noi_dung_thuc_hien;
            ngayThangNamRow += so_dong_lech_noi_dung_thuc_hien;
            fullNameRow += so_dong_lech_noi_dung_thuc_hien;
            toTuongAlias += so_dong_lech_noi_dung_thuc_hien;

            ngayThangNamTPKCS += so_dong_lech_noi_dung_thuc_hien;
            signImgTPKCS += so_dong_lech_noi_dung_thuc_hien;
            fullNameTPKCS += so_dong_lech_noi_dung_thuc_hien;
        }

        if (getPayload().getTpkcsXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamTPKCS), 4, getPayload().getNgayThangNamTPKCS());
            setCellVal(getSheet().getRow(fullNameTPKCS), 4, getPayload().getTpkcsFullName());
            excelImageService.addImageToSheet("E" + signImgTPKCS, getSheet(), imageData(getPayload().getTpkcsSignImg()));
        }
        if (getPayload().getToTruong1XacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 0, getPayload().getNgayThangNamToTruong1());
            setCellVal(getSheet().getRow(fullNameRow), 0, getPayload().getToTruong1fullName());
            setCellVal(getSheet().getRow(toTuongAlias), 0, getPayload().getToTruong1Alias());
            excelImageService.addImageToSheet("A" + imgRow, getSheet(), imageData(getPayload().getToTruong1SignImg()));
        }
        if (Objects.nonNull(getPayload().getToTruong2Id())) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 1, getPayload().getNgayThangNamToTruong2());
            setCellVal(getSheet().getRow(fullNameRow), 1, getPayload().getToTruong2fullName());
            setCellVal(getSheet().getRow(toTuongAlias), 1, getPayload().getToTruong2Alias());
            excelImageService.addImageToSheet("B" + imgRow, getSheet(), imageData(getPayload().getToTruong2SignImg()));
        }
        if (Objects.nonNull(getPayload().getToTruong3Id())) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 2, getPayload().getNgayThangNamToTruong3());
            setCellVal(getSheet().getRow(fullNameRow), 2, getPayload().getToTruong3fullName());
            setCellVal(getSheet().getRow(toTuongAlias), 2, getPayload().getToTruong3Alias());
            excelImageService.addImageToSheet("C" + imgRow, getSheet(), imageData(getPayload().getToTruong3SignImg()));
        }
        if (Objects.nonNull(getPayload().getToTruong4Id())) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 3, getPayload().getNgayThangNamToTruong4());
            setCellVal(getSheet().getRow(fullNameRow), 3, getPayload().getToTruong4fullName());
            setCellVal(getSheet().getRow(toTuongAlias), 3, getPayload().getToTruong4Alias());
            excelImageService.addImageToSheet("D" + imgRow, getSheet(), imageData(getPayload().getToTruong4SignImg()));
        }
        if (Objects.nonNull(getPayload().getToTruong5Id())) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 4, getPayload().getNgayThangNamToTruong5());
            setCellVal(getSheet().getRow(fullNameRow), 4, getPayload().getToTruong5fullName());
            setCellVal(getSheet().getRow(toTuongAlias), 4, getPayload().getToTruong5Alias());
            excelImageService.addImageToSheet("E" + imgRow, getSheet(), imageData(getPayload().getToTruong5SignImg()));
        }
    }
}
