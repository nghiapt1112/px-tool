ALTER TABLE `cong_nhan_thanh_pham` ADD quan_doc_ids  VARCHAR(255) DEFAULT NULL;
ALTER TABLE `cong_nhan_thanh_pham` CHANGE `ngay_thang_namtpkcs` `ngay_thang_namtpkcs` bigint(20) DEFAULT NULL;
ALTER TABLE `kiem_hong` CHANGE `ngay_thang_nam_quan_doc` `ngay_thang_nam_quan_doc` bigint(20) DEFAULT NULL;
ALTER TABLE `kiem_hong` CHANGE `ngay_thang_nam_to_truong` `ngay_thang_nam_to_truong` bigint(20) DEFAULT NULL;
ALTER TABLE `kiem_hong` CHANGE `ngay_thang_nam_tro_lykt` `ngay_thang_nam_tro_lykt` bigint(20) DEFAULT NULL;

ALTER TABLE `phieu_dat_hang` CHANGE `ngay_thang_nam_nguoi_dat_hang` `ngay_thang_nam_nguoi_dat_hang` bigint(20) DEFAULT NULL;
ALTER TABLE `phieu_dat_hang` CHANGE `ngay_thang_namtpkthk` `ngay_thang_namtpkthk` bigint(20) DEFAULT NULL;
ALTER TABLE `phieu_dat_hang` CHANGE `ngay_thang_namtpvat_tu` `ngay_thang_namtpvat_tu` bigint(20) DEFAULT NULL;

ALTER TABLE `phuong_an` CHANGE `ngay_thang_nam_giam_doc` `ngay_thang_nam_giam_doc` bigint(20) DEFAULT NULL;
ALTER TABLE `phuong_an` CHANGE `ngay_thang_nam_nguoi_lap` `ngay_thang_nam_nguoi_lap` bigint(20) DEFAULT NULL;
ALTER TABLE `phuong_an` CHANGE `ngay_thang_nam_phe_duyet` `ngay_thang_nam_phe_duyet` bigint(20) DEFAULT NULL;
ALTER TABLE `phuong_an` CHANGE `ngay_thang_namtpkehoach` `ngay_thang_namtpkehoach` bigint(20) DEFAULT NULL;
ALTER TABLE `phuong_an` CHANGE `ngay_thang_namtpkthk` `ngay_thang_namtpkthk` bigint(20) DEFAULT NULL;
ALTER TABLE `phuong_an` CHANGE `ngay_thang_namtp_vat_tu` `ngay_thang_namtp_vat_tu` bigint(20) DEFAULT NULL;
