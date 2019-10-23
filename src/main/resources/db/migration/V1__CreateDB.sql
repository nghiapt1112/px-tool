SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cong_nhan_thanh_pham
-- ----------------------------
DROP TABLE IF EXISTS `cong_nhan_thanh_pham`;
CREATE TABLE `cong_nhan_thanh_pham`  (
  `tp_id` bigint(20) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `donvi_dat_hang` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `donvi_thuc_hien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `so_nghiem_thu_duoc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sopa` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_san_pham` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`tp_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dinh_muc_lao_dong
-- ----------------------------
DROP TABLE IF EXISTS `dinh_muc_lao_dong`;
CREATE TABLE `dinh_muc_lao_dong`  (
  `dm_id` bigint(20) NOT NULL,
  `baccv` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dm` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ghi_chu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `noi_dung_cong_viec` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pa_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`dm_id`) USING BTREE,
  INDEX `FKqr6ybi9bjgt807r6qe8glkvmi`(`pa_id`) USING BTREE,
  CONSTRAINT `FKqr6ybi9bjgt807r6qe8glkvmi` FOREIGN KEY (`pa_id`) REFERENCES `phuong_an` (`pa_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dinh_muc_vat_tu
-- ----------------------------
DROP TABLE IF EXISTS `dinh_muc_vat_tu`;
CREATE TABLE `dinh_muc_vat_tu`  (
  `vt_id` bigint(20) NOT NULL,
  `dm1sp` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dvt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ghi_chu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `kho_don_gia` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `kho_so_luong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `kho_thanh_tien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `kho_tong_tien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ky_ma_ky_hieu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `mn_don_gia` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `mn_so_luong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `mn_thanh_tien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `mn_tong_tien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `so_luong_san_pham` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_vat_tu_ky_thuat` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tien_luong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tong_nhu_cau` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pa_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`vt_id`) USING BTREE,
  INDEX `FK8hki41rsduhymuf5kjgdsp4cs`(`pa_id`) USING BTREE,
  CONSTRAINT `FK8hki41rsduhymuf5kjgdsp4cs` FOREIGN KEY (`pa_id`) REFERENCES `phuong_an` (`pa_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);
INSERT INTO `hibernate_sequence` VALUES (12);

-- ----------------------------
-- Table structure for kiem_hong
-- ----------------------------
DROP TABLE IF EXISTS `kiem_hong`;
CREATE TABLE `kiem_hong`  (
  `kh_id` bigint(20) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `cong_doan` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_nam_quan_doc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_nam_to_truong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_nam_tro_lykt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nguon_vao` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `noi_nhan` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phan_xuong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `quan_doc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `so_hieu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `so_to` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `soxx` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_nha_may` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenvktbkt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tosx` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `to_so` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `to_truong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tro_lykt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `y_kien_giam_doc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`kh_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kiem_hong
-- ----------------------------
INSERT INTO `kiem_hong` VALUES (4, '2019-10-23 16:33:15', 3, b'1', '2019-10-23 16:29:59', 0, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string');
INSERT INTO `kiem_hong` VALUES (6, '2019-10-23 16:37:24', 3, b'0', NULL, NULL, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string');

-- ----------------------------
-- Table structure for kiem_hong_detail
-- ----------------------------
DROP TABLE IF EXISTS `kiem_hong_detail`;
CREATE TABLE `kiem_hong_detail`  (
  `kh_detail_id` bigint(20) NOT NULL,
  `dang_hu_hong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ky_hieu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nguoi_kiem_hong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phuong_phap_khac_phuc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sl` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_linh_kien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_phu_kien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `kh_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`kh_detail_id`) USING BTREE,
  INDEX `FK5oy0dm0r5rhhav4j2cfoenpab`(`kh_id`) USING BTREE,
  CONSTRAINT `FK5oy0dm0r5rhhav4j2cfoenpab` FOREIGN KEY (`kh_id`) REFERENCES `kiem_hong` (`kh_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kiem_hong_detail
-- ----------------------------
INSERT INTO `kiem_hong_detail` VALUES (5, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 4);
INSERT INTO `kiem_hong_detail` VALUES (7, '2', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 6);
INSERT INTO `kiem_hong_detail` VALUES (8, '4', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 6);
INSERT INTO `kiem_hong_detail` VALUES (9, '3', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 6);
INSERT INTO `kiem_hong_detail` VALUES (10, '1', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 6);
INSERT INTO `kiem_hong_detail` VALUES (11, '5', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 6);

-- ----------------------------
-- Table structure for noi_dung_thuc_hien
-- ----------------------------
DROP TABLE IF EXISTS `noi_dung_thuc_hien`;
CREATE TABLE `noi_dung_thuc_hien`  (
  `noi_dung_id` bigint(20) NOT NULL,
  `ket_qua` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nghiem_thu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nguoi_lam` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tp_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`noi_dung_id`) USING BTREE,
  INDEX `FKco98veiy520ogpu98uyy0ssww`(`tp_id`) USING BTREE,
  CONSTRAINT `FKco98veiy520ogpu98uyy0ssww` FOREIGN KEY (`tp_id`) REFERENCES `cong_nhan_thanh_pham` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phieu_dat_hang
-- ----------------------------
DROP TABLE IF EXISTS `phieu_dat_hang`;
CREATE TABLE `phieu_dat_hang`  (
  `pdh_id` bigint(20) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `nguoi_dat_hang` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tpkthk` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tpvat_tu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `don_vi_yeu_cau` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_nam_nguoi_dat_hang` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_namtpkthk` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_namtpvat_tu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `noi_nhan` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phan_xuong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `so` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_nha_may` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_phong` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `y_kien_giam_doc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`pdh_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phieu_dat_hang_detail
-- ----------------------------
DROP TABLE IF EXISTS `phieu_dat_hang_detail`;
CREATE TABLE `phieu_dat_hang_detail`  (
  `pdh_detail_id` bigint(20) NOT NULL,
  `dvt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ki_ma_hieu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `muc_dich_su_dung` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nguoi_thuc_hien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phuong_phap_khac_phuc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sl` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `so_phieu_dat_hang` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `stt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_phu_kien` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_vat_tu_ky_thuat` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pdh_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`pdh_detail_id`) USING BTREE,
  INDEX `FKmcui5cljwcj7ws3w774m7e77x`(`pdh_id`) USING BTREE,
  CONSTRAINT `FKmcui5cljwcj7ws3w774m7e77x` FOREIGN KEY (`pdh_id`) REFERENCES `phieu_dat_hang` (`pdh_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phong_ban
-- ----------------------------
DROP TABLE IF EXISTS `phong_ban`;
CREATE TABLE `phong_ban`  (
  `phong_ban_id` bigint(20) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`phong_ban_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phong_ban
-- ----------------------------
INSERT INTO `phong_ban` VALUES (43, '2019-10-21 16:10:28', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (44, '2019-10-21 16:10:28', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (45, '2019-10-21 16:10:28', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (46, '2019-10-21 16:10:28', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (47, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (48, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (49, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (50, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (51, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (52, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (53, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (54, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (55, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (56, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (57, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (58, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (59, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (60, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (61, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (62, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (63, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (64, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (65, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (66, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (67, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (68, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (69, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (70, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (71, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (72, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (73, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (74, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (75, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (76, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (77, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (78, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (79, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (80, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (81, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');
INSERT INTO `phong_ban` VALUES (82, '2019-10-21 16:10:29', NULL, b'0', NULL, NULL, 2, 'NAME');

-- ----------------------------
-- Table structure for phuong_an
-- ----------------------------
DROP TABLE IF EXISTS `phuong_an`;
CREATE TABLE `phuong_an`  (
  `pa_id` bigint(20) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `nguoi_lap` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pdh` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ma_so` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_nam_nguoi_lap` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_nam_phe_duyet` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_namtpkehoach` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_namtpkthk` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ngay_thang_namtp_vat_tu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nguon_kinh_phi` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `san_pham` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `so_to` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ten_nha_may` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tien_luong` decimal(19, 2) NULL DEFAULT NULL,
  `to_so` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tong_cong_dinh_muc_lao_dong` decimal(19, 2) NULL DEFAULT NULL,
  `tongdmvtkho` decimal(19, 2) NULL DEFAULT NULL,
  `tongdmvtmua_ngoai` decimal(19, 2) NULL DEFAULT NULL,
  `truong_phongkthk` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `truong_phong_ke_hoach` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `truong_phong_vat_tu` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`pa_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint(20) NOT NULL,
  `authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'USER');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phong_ban_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `FKhdrwlest5ngwfj0x436uw59iv`(`phong_ban_id`) USING BTREE,
  CONSTRAINT `FKhdrwlest5ngwfj0x436uw59iv` FOREIGN KEY (`phong_ban_id`) REFERENCES `phong_ban` (`phong_ban_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, '2019-10-23 16:12:48', NULL, b'0', NULL, NULL, 'admin', '$2a$10$YYEQIuOmZWeTjQnaLOBOZueZbNG5Z5jKPXIW/DQrHE33vK9JJCdQC', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKka3w3atry4amefp94rblb52n7`(`role_id`) USING BTREE,
  CONSTRAINT `FKhjx9nk20h4mo745tdqj8t8n9d` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKka3w3atry4amefp94rblb52n7` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (3, 1);

SET FOREIGN_KEY_CHECKS = 1;