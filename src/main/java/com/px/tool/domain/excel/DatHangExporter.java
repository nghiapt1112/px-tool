package com.px.tool.domain.excel;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.mucdich.sudung.MucDichSuDung;
import com.px.tool.domain.mucdich.sudung.repository.MucDichSuDungRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.utils.ExcelImageService;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DatHangExporter extends DocumentExporter<PhieuDatHangPayload> {

    private MucDichSuDungRepository mucDichSuDungRepository;

    @Autowired
    public DatHangExporter(ExcelImageService excelImageService, UserService userService, MucDichSuDungRepository mucDichSuDungRepository) {
        super(excelImageService, userService);
    }

    @Override
    @PostConstruct
    protected void register() {
        DocumentFactory.register(RequestType.DAT_HANG, this);
    }

    @Override
    protected int totalLines() {
        return getPayload().getPhieuDatHangDetails().size();
    }

    @Override
    protected void writeFixedData() {
        XSSFRow row0 = getSheet().getRow(1);
        XSSFRow row1 = getSheet().getRow(2);
        XSSFRow row2 = getSheet().getRow(3);

        setCellVal(row0, 6, getPayload().getSo());
        setCellVal(row1, 6, getPayload().getDonViYeuCau());
        setCellVal(row1, 8, getPayload().getPhanXuong());
        setCellVal(row2, 6, getPayload().getNoiDung());
    }

    @Override
    protected void copyRows() {
        if (totalLines() > 5) {
            getSheet().copyRows(13, 18, 18 + (totalLines() - 5), new CellCopyPolicy()); // copy and paste
            for (int i = 13; i < 18 + (totalLines() - 5); i++) {
                getSheet().createRow(i);
                getSheet().copyRows(7, 7, i - 1, new CellCopyPolicy()); // copy and paste
            }
        }
    }

    @Override
    protected void writeDynamicData() {
        for (int i = 0; i < totalLines(); i++) {
            XSSFRow crrRow = getSheet().getRow(6 + i);
            setCellVal(crrRow, 0, i + 1 + "");
            setCellVal(crrRow, 1, getPayload().getPhieuDatHangDetails().get(i).getTenPhuKien());
            setCellVal(crrRow, 2, getPayload().getPhieuDatHangDetails().get(i).getTenVatTuKyThuat());
            setCellVal(crrRow, 3, getPayload().getPhieuDatHangDetails().get(i).getKiMaHieu());
            setCellVal(crrRow, 4, getPayload().getPhieuDatHangDetails().get(i).getDvt());
            setCellVal(crrRow, 5, getPayload().getPhieuDatHangDetails().get(i).getSl());
            setCellVal(crrRow, 6, getVal(mdsdMap(), getPayload().getPhieuDatHangDetails().get(i).getMucDichSuDung()));
            setCellVal(crrRow, 7, getPayload().getPhieuDatHangDetails().get(i).getPhuongPhapKhacPhuc());
            setCellVal(crrRow, 8, getPayload().getPhieuDatHangDetails().get(i).getSoPhieuDatHang());
            setCellVal(crrRow, 9, getPayload().getPhieuDatHangDetails().get(i).getNguoiThucHien());
        }
    }

    @Override
    protected void writePhanChuKy() throws IOException {
        // NOTE: update print anh chu ky
        int cusNoiNhan = 13;
        int ngayThangNamRow = 14;
        int imgRow = 17; // row nay bat dau tu 1 nen khong giong index ben tren phai -1
        int fullNameRow = 17;
        if (totalLines() > 5) {
            // 18 la so line data fixed , da cho ban dau
            // 4 la so dong thua , sau khi clone o step ben tren
            int tmpLines = totalLines() - 5 + 5;
            cusNoiNhan = cusNoiNhan + tmpLines;
            imgRow = imgRow + tmpLines;
            ngayThangNamRow = ngayThangNamRow + tmpLines;
            fullNameRow = fullNameRow + tmpLines;
        }
        setCellVal(getSheet().getRow(cusNoiNhan), 1, getNoiNhan(userById(), getPayload().getCusReceivers()));
        if (getPayload().getNguoiDatHangXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 8, getPayload().getNgayThangNamNguoiDatHang());
            setCellVal(getSheet().getRow(fullNameRow), 8, getPayload().getNguoiDatHangFullName());
            excelImageService.addImageToSheet("I" + imgRow, getSheet(), imageData(getPayload().getNguoiDatHangSignImg()));
        }
        if (getPayload().getTpvatTuXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 6, getPayload().getNgayThangNamTPVatTu());
            setCellVal(getSheet().getRow(fullNameRow), 6, getPayload().getTpvatTuFullName());
            excelImageService.addImageToSheet("G" + imgRow, getSheet(), imageData(getPayload().getTpvatTuSignImg()));
        }
        if (getPayload().getTpkthkXacNhan()) {
            setCellVal(getSheet().getRow(ngayThangNamRow), 2, getPayload().getNgayThangNamTPKTHK());
            setCellVal(getSheet().getRow(fullNameRow), 2, getPayload().getTpkthkFullName());
            excelImageService.addImageToSheet("C" + imgRow, getSheet(), imageData(getPayload().getTpkthkSignImg()));
        }
    }

    private Map<Long, String> mdsdMap() {
        return this.mucDichSuDungRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(MucDichSuDung::getMdId, e -> e.getTen()));
    }
}
