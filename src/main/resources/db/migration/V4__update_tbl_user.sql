ALTER TABLE `user` ADD
    `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

UPDATE `user` AS u SET u.alias = 'Giám Đốc' WHERE u.email='GIAM_DOC';
UPDATE `user` AS u SET u.alias = 'Chính Ủy' WHERE u.email='CHINH_UY';
UPDATE `user` AS u SET u.alias = 'PGD. Kỹ thuật hàng không' WHERE u.email='PGDKTHK';
UPDATE `user` AS u SET u.alias = 'PGD. Sản xuất' WHERE u.email='PGDSX';
UPDATE `user` AS u SET u.alias = 'PGD. Xe máy đặc chủng' WHERE u.email='PGDXMDC';
UPDATE `user` AS u SET u.alias = 'Ban chính trị' WHERE u.email='BAN_CHINH_TRI';
UPDATE `user` AS u SET u.alias = 'Phòng kỹ thuật hàng không' WHERE u.email='PHONG_KTHK';
UPDATE `user` AS u SET u.alias = 'Phòng xe máy đặc chủng' WHERE u.email='PHONG_XE_MAY_DAC_CHUNG';
UPDATE `user` AS u SET u.alias = 'Phòng KCS' WHERE u.email='PHONG_KCS';
UPDATE `user` AS u SET u.alias = 'Phòng Cơ điện' WHERE u.email='PHONG_CO_DIEN';
UPDATE `user` AS u SET u.alias = 'Phòng vật tư' WHERE u.email='PHONG_VAT_TU';
UPDATE `user` AS u SET u.alias = 'Phòng tài chính' WHERE u.email='PHONG_TAI_CHINH';
UPDATE `user` AS u SET u.alias = 'Phòng kế hoạch' WHERE u.email='PHONG_KE_HOACH';
UPDATE `user` AS u SET u.alias = 'Tổ dự án' WHERE u.email='TO_DU_AN';
UPDATE `user` AS u SET u.alias = 'Phòng hành chính hậu cần' WHERE u.email='PHONG_HANH_CHINH_HAU_CAN';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 1' WHERE u.email='PX1';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 2' WHERE u.email='PX2';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 3' WHERE u.email='PX3';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 4' WHERE u.email='PX4';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 5' WHERE u.email='PX5';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 6' WHERE u.email='PX6';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 7' WHERE u.email='PX7';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 8' WHERE u.email='PX8';
UPDATE `user` AS u SET u.alias = 'Phân xưởng 9' WHERE u.email='PX9';
UPDATE `user` AS u SET u.alias = 'Trạm kiểm thử bay thử' WHERE u.email='TRAM_KIEM_THU_BAY_THU';
UPDATE `user` AS u SET u.alias = 'Văn thư bảo mật' WHERE u.email='VAN_THU_BAO_MAT';
UPDATE `user` AS u SET u.alias = 'Điều động' WHERE u.email='DIEU_DO';
UPDATE `user` AS u SET u.alias = 'Trợ lý động lực nhiên liệu' WHERE u.email='TRO_LY_DONG_LUC_NHIEN_LIEU';
UPDATE `user` AS u SET u.alias = 'Trợ lý thủy lực càng' WHERE u.email='TL_THUY_LUC_CANG';
UPDATE `user` AS u SET u.alias = 'Trợ lý thân cánh điều khiển' WHERE u.email='TL_THAN_CANH_DIEU_KHIEN';
UPDATE `user` AS u SET u.alias = 'Trợ lý vũ khí hàng không' WHERE u.email='TL_VU_KHI_HANG_KHONG';
UPDATE `user` AS u SET u.alias = 'Trợ lý vô tuyến' WHERE u.email='TL_VO_TUYEN';
UPDATE `user` AS u SET u.alias = 'Trợ lý điện điện tử' WHERE u.email='TL_DIEN_DIEN_TU';
UPDATE `user` AS u SET u.alias = 'Trợ lý đồng hồ' WHERE u.email='TL_DONG_HO';
UPDATE `user` AS u SET u.alias = 'Trợ lý hóa' WHERE u.email='TL_HOA';
UPDATE `user` AS u SET u.alias = 'Trợ lý huấn luyện' WHERE u.email='TL_HUAN_LUYEN';
UPDATE `user` AS u SET u.alias = 'Trợ lý cơ khí' WHERE u.email='TL_CO_KHI';
UPDATE `user` AS u SET u.alias = 'Trợ lý xe máy' WHERE u.email='TL_XE_MAY';
UPDATE `user` AS u SET u.alias = 'Trợ lý điện khí' WHERE u.email='TL_DIEN_KHI';
UPDATE `user` AS u SET u.alias = 'Nhân viên động lực nhiên liệu' WHERE u.email='NV_DONG_LUC_NHIEN_LIEU';
UPDATE `user` AS u SET u.alias = 'Nhân viên thủy lực càng' WHERE u.email='NV_THUY_LUC_CANG';
UPDATE `user` AS u SET u.alias = 'Nhân viên thân cánh điều khiển' WHERE u.email='NV_THAN_CANH_DIEU_KHIEN';
UPDATE `user` AS u SET u.alias = 'Nhân viên vũ khí hàng không' WHERE u.email='NV_VU_KHI_HANG_KHONG';
UPDATE `user` AS u SET u.alias = 'Nhân viên vô tuyến' WHERE u.email='NV_VO_TUYEN';
UPDATE `user` AS u SET u.alias = 'Nhân viên điện tử' WHERE u.email='NV_DIEN_DIEN_TU';
UPDATE `user` AS u SET u.alias = 'Nhân viên đồng hồ' WHERE u.email='NV_DONG_HO';
UPDATE `user` AS u SET u.alias = 'Nhân viên hóa' WHERE u.email='NV_HOA';
UPDATE `user` AS u SET u.alias = 'Nhân viên xe máy' WHERE u.email='NV_XE_MAY';
UPDATE `user` AS u SET u.alias = 'Nhân viên điện khí' WHERE u.email='NV_DIEN_KHI';
UPDATE `user` AS u SET u.alias = 'Nhân viên vật tư 1' WHERE u.email='NV_VAT_TU_1';
UPDATE `user` AS u SET u.alias = 'Nhân viên vật tư 2' WHERE u.email='NV_VAT_TU_2';
UPDATE `user` AS u SET u.alias = 'Nhân viên vật tư 3' WHERE u.email='NV_VAT_TU_3';
UPDATE `user` AS u SET u.alias = 'Nhân viên vật tư 4' WHERE u.email='NV_VAT_TU_4';
UPDATE `user` AS u SET u.alias = 'Nhân viên định mức' WHERE u.email='NHAN_VIEN_DINH_MUC';
UPDATE `user` AS u SET u.alias = 'Tổ sơn' WHERE u.email='TO_SON';
UPDATE `user` AS u SET u.alias = 'Tổ động lực nhiên liệu' WHERE u.email='TO_DONG_LUC_NHIEN_LIEU';
UPDATE `user` AS u SET u.alias = 'Tổ thủy lực càng' WHERE u.email='TO_THUY_LUC_CANG';
UPDATE `user` AS u SET u.alias = 'Tổ thân cánh điều khiển' WHERE u.email='TO_THAN_CANH_DIEU_KHIEN';
UPDATE `user` AS u SET u.alias = 'Tổ vũ khí hàng không' WHERE u.email='TO_VU_KHI_HANG_KHONG';
UPDATE `user` AS u SET u.alias = 'Tổ gò tán' WHERE u.email='TO_GO_TAN';
UPDATE `user` AS u SET u.alias = 'Tổ điện PX.3' WHERE u.email='TO_DIEN_PX3';
UPDATE `user` AS u SET u.alias = 'Tổ đồng hồ' WHERE u.email='TO_DONG_HO';
UPDATE `user` AS u SET u.alias = 'Tổ vô tuyến PX.3' WHERE u.email='TO_VO_TUYEN_PX3';
UPDATE `user` AS u SET u.alias = 'Tổ phụ tùng' WHERE u.email='TO_PHU_TUNG';
UPDATE `user` AS u SET u.alias = 'Tổ động cơ cánh quạt' WHERE u.email='TO_DONG_CO_CANH_QUAT';
UPDATE `user` AS u SET u.alias = 'Tổ kiểm hỏng' WHERE u.email='TO_KIEM_HOMG';
UPDATE `user` AS u SET u.alias = 'Tổ ép đệm' WHERE u.email='TO_EP_DEM';
UPDATE `user` AS u SET u.alias = 'Tổ thiết bị hàng không' WHERE u.email='TO_THIET_BI_HANG_KHONG';
UPDATE `user` AS u SET u.alias = 'Tổ vô tuyến PX.5' WHERE u.email='TO_VO_TUYEN_PX5';
UPDATE `user` AS u SET u.alias = 'Tổ điện PX.5' WHERE u.email='TO_DIEN_PX5';
UPDATE `user` AS u SET u.alias = 'Tổ đặc chủng' WHERE u.email='TO_DAC_CHUNG';
UPDATE `user` AS u SET u.alias = 'Tổ máy gầm' WHERE u.email='TO_MAY_GAM';
UPDATE `user` AS u SET u.alias = 'Tổ cơ khí PX.6' WHERE u.email='TO_CO_KHI_PX6';
UPDATE `user` AS u SET u.alias = 'Tổ tổng hợp' WHERE u.email='TO_TONG_HOP';
UPDATE `user` AS u SET u.alias = 'Tổ vận hành' WHERE u.email='TO_VAN_HANH_1';
UPDATE `user` AS u SET u.alias = 'Tổ vận hành 2' WHERE u.email='TO_VAN_HANH_2';
UPDATE `user` AS u SET u.alias = 'Tổ vận hành 3' WHERE u.email='TO_VAN_HANH_3';
UPDATE `user` AS u SET u.alias = 'Tổ khám nghiệm sửa chữa bình' WHERE u.email='TO_KHAM_NGHIEM_SUA_CHUA_BINH';
UPDATE `user` AS u SET u.alias = 'Tổ cơ điện' WHERE u.email='TO_CO_DIEN';
UPDATE `user` AS u SET u.alias = 'Tổ cơ khí PX.8' WHERE u.email='TO_CO_KHI_PX8';
UPDATE `user` AS u SET u.alias = 'Tổ mạ' WHERE u.email='TO_MA';
UPDATE `user` AS u SET u.alias = 'Tổ vật liệu composite' WHERE u.email='TO_VAT_LIEU_COMPOSITE';
UPDATE `user` AS u SET u.alias = 'Tổ ULD' WHERE u.email='TO_ULD';
UPDATE `user` AS u SET u.alias = 'Tổ DOLLY' WHERE u.email='TO_DOLLY';