package com.px.tool.domain.excel;


import com.px.tool.domain.RequestType;
import com.px.tool.domain.kiemhong.KiemHongPayLoad;
import com.px.tool.domain.mucdich.sudung.repository.MucDichSuDungRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.utils.ExcelImageService;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class KiemHongExporter extends DocumentExporter<KiemHongPayLoad> {

    @Autowired
    public KiemHongExporter(ExcelImageService excelImageService, UserService userService) {
        super(excelImageService, userService);
    }

    @Override
    @PostConstruct
    public void register() {
        DocumentFactory.register(RequestType.KIEM_HONG, this);
    }

    @Override
    public void writeFixedData() {
        XSSFRow row0 = getSheet().getRow(0);
        XSSFRow row1 = getSheet().getRow(1);
        XSSFRow row2 = getSheet().getRow(2);

        setCellVal(row0, 4, getPayload().getTenVKTBKT());
        setCellVal(row0, 6, getPayload().getSoHieu());
        setCellVal(row0, 8, getPayload().getToSo());
        setCellVal(row1, 2, fillUserInfo(getPayload().getPhanXuong(), userById()));
        setCellVal(row1, 4, getPayload().getNguonVao());
        setCellVal(row1, 6, getPayload().getSoXX());
        setCellVal(row1, 8, getPayload().getSoTo());
        setCellVal(row2, 2, fillUserInfo(getPayload().getToSX(), userById()));
        setCellVal(row2, 4, getPayload().getCongDoan());
    }

    @Override
    public void copyRows() {
        if (this.totalLines() > 18) {
            getSheet().copyRows(24, 28, 28 + (this.totalLines() - 18), new CellCopyPolicy()); // copy and paste
            for (int i = 24; i < 28 + (this.totalLines() - 18); i++) {// 24 la row bat dau tu noi nhan
                getSheet().createRow(i);
                getSheet().copyRows(6, 6, i - 1, new CellCopyPolicy()); // copy and paste
            }
        }
    }

    @Override
    public void writeDynamicData() {
        for (int i = 0; i < this.totalLines(); i++) {
            XSSFRow currRow = getSheet().getRow(5 + i);
            setCellVal(currRow, 0, i + 1 + "");
            setCellVal(currRow, 1, getPayload().getKiemHongDetails().get(i).getTenPhuKien());
            setCellVal(currRow, 3, getPayload().getKiemHongDetails().get(i).getTenLinhKien());
            setCellVal(currRow, 4, getPayload().getKiemHongDetails().get(i).getKyHieu());
            setCellVal(currRow, 5, getPayload().getKiemHongDetails().get(i).getSl());
            setCellVal(currRow, 6, getPayload().getKiemHongDetails().get(i).getDangHuHong());
            setCellVal(currRow, 7, getPayload().getKiemHongDetails().get(i).getPhuongPhapKhacPhuc());
            setCellVal(currRow, 8, getPayload().getKiemHongDetails().get(i).getNguoiKiemHong());
        }
    }

    @Override
    public void writePhanChuKy() throws IOException {
        // NOTE: update print anh chu ky
        int cusNoiNhan = 24;
        int ngayThangNamRow = 25;
        int imgRow = 28; // row nay bat dau tu 1 nen khong giong index ben tren phai -1
        int fullNameRow = 28;
        if (this.totalLines() > 18) {
            // 18 la so line data fixed , da cho ban dau
            // 4 la so dong thua , sau khi clone o step ben tren
            int tmpLines = this.totalLines() - 18 + 4;
            cusNoiNhan += tmpLines;
            imgRow += tmpLines;
            ngayThangNamRow += tmpLines;
            fullNameRow += tmpLines;
        }
        setCellVal(getSheet().getRow(cusNoiNhan), 1, getNoiNhan(userById(), getPayload().getCusReceivers()));
        if (getPayload().getToTruongXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 8, getPayload().getNgayThangNamToTruong());
            setCellVal(getSheet().getRow(fullNameRow), 8, getPayload().getToTruongfullName());
            excelImageService.addImageToSheet("I" + imgRow, getSheet(), imageData(getPayload().getToTruongSignImg()));
        }
        if (getPayload().getTroLyKTXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 6, getPayload().getNgayThangNamTroLyKT());
            setCellVal(getSheet().getRow(fullNameRow), 6, getPayload().getTroLyfullName());
            excelImageService.addImageToSheet("G" + imgRow, getSheet(), imageData(getPayload().getTroLyKTSignImg()));
        }
        if (getPayload().getQuanDocXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 3, getPayload().getNgayThangNamQuanDoc());
            setCellVal(getSheet().getRow(fullNameRow), 3, getPayload().getQuanDocfullName());
            excelImageService.addImageToSheet("D" + imgRow, getSheet(), imageData(getPayload().getQuanDocSignImg()));
        }
    }

    @Override
    protected int totalLines() {
        return this.getPayload().getKiemHongDetails().size();
    }
}
