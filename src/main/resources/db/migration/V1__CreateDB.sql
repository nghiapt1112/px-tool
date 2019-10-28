/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : px_tool2

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 28/10/2019 23:48:05
*/

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
  `donvi_dat_hang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `donvi_thuc_hien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `so_nghiem_thu_duoc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sopa` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_san_pham` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tp_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cong_nhan_thanh_pham
-- ----------------------------
INSERT INTO `cong_nhan_thanh_pham` VALUES (299, '2019-10-28 15:26:42', NULL, b'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for dinh_muc_lao_dong
-- ----------------------------
DROP TABLE IF EXISTS `dinh_muc_lao_dong`;
CREATE TABLE `dinh_muc_lao_dong`  (
  `dm_id` bigint(20) NOT NULL,
  `baccv` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ghi_chu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noi_dung_cong_viec` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pa_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`dm_id`) USING BTREE,
  INDEX `FKqr6ybi9bjgt807r6qe8glkvmi`(`pa_id`) USING BTREE,
  CONSTRAINT `FKqr6ybi9bjgt807r6qe8glkvmi` FOREIGN KEY (`pa_id`) REFERENCES `phuong_an` (`pa_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dinh_muc_vat_tu
-- ----------------------------
DROP TABLE IF EXISTS `dinh_muc_vat_tu`;
CREATE TABLE `dinh_muc_vat_tu`  (
  `vt_id` bigint(20) NOT NULL,
  `dm1sp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dvt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ghi_chu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kho_don_gia` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kho_so_luong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kho_thanh_tien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kho_tong_tien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ky_ma_ky_hieu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mn_don_gia` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mn_so_luong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mn_thanh_tien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mn_tong_tien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `so_luong_san_pham` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_vat_tu_ky_thuat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tien_luong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tong_nhu_cau` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pa_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`vt_id`) USING BTREE,
  INDEX `FK8hki41rsduhymuf5kjgdsp4cs`(`pa_id`) USING BTREE,
  CONSTRAINT `FK8hki41rsduhymuf5kjgdsp4cs` FOREIGN KEY (`pa_id`) REFERENCES `phuong_an` (`pa_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history`  (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `script` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `checksum` int(11) NULL DEFAULT NULL,
  `installed_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `installed_on` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`) USING BTREE,
  INDEX `flyway_schema_history_s_idx`(`success`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flyway_schema_history
-- ----------------------------
INSERT INTO `flyway_schema_history` VALUES (1, '1', 'CreateDB', 'SQL', 'V1__CreateDB.sql', -1513203368, 'root', '2019-10-28 15:12:18', 1271, 1);

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);
INSERT INTO `hibernate_sequence` VALUES (304);

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
  `cong_doan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_nam_quan_doc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_nam_to_truong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_nam_tro_lykt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nguon_vao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noi_nhan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phan_xuong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `quan_doc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `so_hieu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `so_to` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `soxx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_nha_may` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tenvktbkt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tosx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_so` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_truong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tro_lykt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `y_kien_giam_doc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`kh_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kiem_hong
-- ----------------------------
INSERT INTO `kiem_hong` VALUES (300, '2019-10-28 15:26:42', 217, b'0', NULL, NULL, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string');

-- ----------------------------
-- Table structure for kiem_hong_detail
-- ----------------------------
DROP TABLE IF EXISTS `kiem_hong_detail`;
CREATE TABLE `kiem_hong_detail`  (
  `kh_detail_id` bigint(20) NOT NULL,
  `dang_hu_hong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ky_hieu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nguoi_kiem_hong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phuong_phap_khac_phuc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_linh_kien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_phu_kien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kh_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`kh_detail_id`) USING BTREE,
  INDEX `FK5oy0dm0r5rhhav4j2cfoenpab`(`kh_id`) USING BTREE,
  CONSTRAINT `FK5oy0dm0r5rhhav4j2cfoenpab` FOREIGN KEY (`kh_id`) REFERENCES `kiem_hong` (`kh_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kiem_hong_detail
-- ----------------------------
INSERT INTO `kiem_hong_detail` VALUES (301, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', NULL);

-- ----------------------------
-- Table structure for noi_dung_thuc_hien
-- ----------------------------
DROP TABLE IF EXISTS `noi_dung_thuc_hien`;
CREATE TABLE `noi_dung_thuc_hien`  (
  `noi_dung_id` bigint(20) NOT NULL,
  `ket_qua` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nghiem_thu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nguoi_lam` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tp_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`noi_dung_id`) USING BTREE,
  INDEX `FKco98veiy520ogpu98uyy0ssww`(`tp_id`) USING BTREE,
  CONSTRAINT `FKco98veiy520ogpu98uyy0ssww` FOREIGN KEY (`tp_id`) REFERENCES `cong_nhan_thanh_pham` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `nguoi_dat_hang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tpkthk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tpvat_tu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `don_vi_yeu_cau` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_nam_nguoi_dat_hang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_namtpkthk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_namtpvat_tu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noi_nhan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phan_xuong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `so` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_nha_may` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_phong` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `y_kien_giam_doc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pdh_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phieu_dat_hang
-- ----------------------------
INSERT INTO `phieu_dat_hang` VALUES (302, '2019-10-28 15:26:42', NULL, b'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for phieu_dat_hang_detail
-- ----------------------------
DROP TABLE IF EXISTS `phieu_dat_hang_detail`;
CREATE TABLE `phieu_dat_hang_detail`  (
  `pdh_detail_id` bigint(20) NOT NULL,
  `dvt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ki_ma_hieu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `muc_dich_su_dung` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nguoi_thuc_hien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phuong_phap_khac_phuc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `so_phieu_dat_hang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `stt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_phu_kien` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_vat_tu_ky_thuat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pdh_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`pdh_detail_id`) USING BTREE,
  INDEX `FKmcui5cljwcj7ws3w774m7e77x`(`pdh_id`) USING BTREE,
  CONSTRAINT `FKmcui5cljwcj7ws3w774m7e77x` FOREIGN KEY (`pdh_id`) REFERENCES `phieu_dat_hang` (`pdh_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_truong` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`phong_ban_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phong_ban
-- ----------------------------
INSERT INTO `phong_ban` VALUES (0, NULL, NULL, NULL, NULL, NULL, NULL, 'TO_VAT_LIEU_COMPOSITE', NULL);
INSERT INTO `phong_ban` VALUES (4, '2019-10-25 16:41:04', NULL, b'0', NULL, NULL, 1, 'ADMIN', NULL);
INSERT INTO `phong_ban` VALUES (5, '2019-10-25 16:41:04', NULL, b'0', NULL, NULL, 2, 'GIAM_DOC', NULL);
INSERT INTO `phong_ban` VALUES (6, '2019-10-25 16:41:04', NULL, b'0', NULL, NULL, 3, 'CHINH_UY', NULL);
INSERT INTO `phong_ban` VALUES (7, '2019-10-25 16:41:04', NULL, b'0', NULL, NULL, 4, 'PGDKTHK', NULL);
INSERT INTO `phong_ban` VALUES (8, '2019-10-25 16:41:04', NULL, b'0', NULL, NULL, 5, 'PGDSX', NULL);
INSERT INTO `phong_ban` VALUES (9, '2019-10-25 16:41:04', NULL, b'0', NULL, NULL, 6, 'PGDXMDC', NULL);
INSERT INTO `phong_ban` VALUES (10, '2019-10-25 16:41:04', NULL, b'0', NULL, NULL, 7, 'BAN_CHINH_TRI', NULL);
INSERT INTO `phong_ban` VALUES (11, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 8, 'PHONG_KTHK', NULL);
INSERT INTO `phong_ban` VALUES (12, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 9, 'PHONG_XE_MAY_DAC_CHUNG', NULL);
INSERT INTO `phong_ban` VALUES (13, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 10, 'PHONG_KCS', NULL);
INSERT INTO `phong_ban` VALUES (14, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 11, 'PHONG_CO_DIEN', NULL);
INSERT INTO `phong_ban` VALUES (15, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 12, 'PHONG_VAT_TU', NULL);
INSERT INTO `phong_ban` VALUES (16, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 13, 'PHONG_TAI_CHINH', NULL);
INSERT INTO `phong_ban` VALUES (17, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 14, 'PHONG_KE_HOACH', NULL);
INSERT INTO `phong_ban` VALUES (18, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 15, 'TO_DU_AN', NULL);
INSERT INTO `phong_ban` VALUES (19, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 16, 'PHONG_HANH_CHINH_HAU_CAN', NULL);
INSERT INTO `phong_ban` VALUES (20, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 17, 'PX1', NULL);
INSERT INTO `phong_ban` VALUES (21, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 18, 'PX2', NULL);
INSERT INTO `phong_ban` VALUES (22, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 19, 'PX3', NULL);
INSERT INTO `phong_ban` VALUES (23, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 20, 'PX4', NULL);
INSERT INTO `phong_ban` VALUES (24, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 21, 'PX5', NULL);
INSERT INTO `phong_ban` VALUES (25, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 22, 'PX6', NULL);
INSERT INTO `phong_ban` VALUES (26, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 23, 'PX7', NULL);
INSERT INTO `phong_ban` VALUES (27, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 24, 'PX8', NULL);
INSERT INTO `phong_ban` VALUES (28, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 25, 'PX9', NULL);
INSERT INTO `phong_ban` VALUES (29, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 26, 'TRAM_KIEM_THU_BAY_THU', NULL);
INSERT INTO `phong_ban` VALUES (30, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 27, 'VAN_THU_BAO_MAT', NULL);
INSERT INTO `phong_ban` VALUES (31, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 28, 'DIEU_DO', NULL);
INSERT INTO `phong_ban` VALUES (32, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 8, 'TRO_LY_DONG_LUC_NHIEN_LIEU', NULL);
INSERT INTO `phong_ban` VALUES (33, '2019-10-25 16:41:05', NULL, b'0', NULL, NULL, 8, 'TL_THUY_LUC_CANG', NULL);
INSERT INTO `phong_ban` VALUES (34, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_THAN_CANH_DIEU_KHIEN', NULL);
INSERT INTO `phong_ban` VALUES (35, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_VU_KHI_HANG_KHONG', NULL);
INSERT INTO `phong_ban` VALUES (36, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_VO_TUYEN', NULL);
INSERT INTO `phong_ban` VALUES (37, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_DIEN_DIEN_TU', NULL);
INSERT INTO `phong_ban` VALUES (38, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_DONG_HO', NULL);
INSERT INTO `phong_ban` VALUES (39, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_HOA', NULL);
INSERT INTO `phong_ban` VALUES (40, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_HUAN_LUYEN', NULL);
INSERT INTO `phong_ban` VALUES (41, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 8, 'TL_CO_KHI', NULL);
INSERT INTO `phong_ban` VALUES (42, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 9, 'TL_XE_MAY', NULL);
INSERT INTO `phong_ban` VALUES (43, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 9, 'TL_DIEN_KHI', NULL);
INSERT INTO `phong_ban` VALUES (44, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_DONG_LUC_NHIEN_LIEU', NULL);
INSERT INTO `phong_ban` VALUES (45, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_THUY_LUC_CANG', NULL);
INSERT INTO `phong_ban` VALUES (46, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_THAN_CANH_DIEU_KHIEN', NULL);
INSERT INTO `phong_ban` VALUES (47, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_VU_KHI_HANG_KHONG', NULL);
INSERT INTO `phong_ban` VALUES (48, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_VO_TUYEN', NULL);
INSERT INTO `phong_ban` VALUES (49, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_DIEN_DIEN_TU', NULL);
INSERT INTO `phong_ban` VALUES (50, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_DONG_HO', NULL);
INSERT INTO `phong_ban` VALUES (51, '2019-10-25 16:41:06', NULL, b'0', NULL, NULL, 10, 'NV_HOA', NULL);
INSERT INTO `phong_ban` VALUES (52, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 10, 'NV_XE_MAY', NULL);
INSERT INTO `phong_ban` VALUES (53, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 10, 'NV_DIEN_KHI', NULL);
INSERT INTO `phong_ban` VALUES (54, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 12, 'NV_VAT_TU_1', NULL);
INSERT INTO `phong_ban` VALUES (55, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 12, 'NV_VAT_TU_2', NULL);
INSERT INTO `phong_ban` VALUES (56, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 12, 'NV_VAT_TU_3', NULL);
INSERT INTO `phong_ban` VALUES (57, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 17, 'TO_SON', NULL);
INSERT INTO `phong_ban` VALUES (58, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 18, 'TO_DONG_LUC_NHIEN_LIEU', NULL);
INSERT INTO `phong_ban` VALUES (59, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 18, 'TO_THUY_LUC_CANG', NULL);
INSERT INTO `phong_ban` VALUES (60, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 18, 'TO_THAN_CANH_DIEU_KHIEN', NULL);
INSERT INTO `phong_ban` VALUES (61, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 18, 'TO_VU_KHI_HANG_KHONG', NULL);
INSERT INTO `phong_ban` VALUES (62, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 18, 'TO_GO_TAN', NULL);
INSERT INTO `phong_ban` VALUES (63, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 19, 'TO_DIEN', NULL);
INSERT INTO `phong_ban` VALUES (64, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 19, 'TO_DONG_HO', NULL);
INSERT INTO `phong_ban` VALUES (65, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 19, 'TO_VO_TUYEN', NULL);
INSERT INTO `phong_ban` VALUES (66, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 20, 'TO_PHU_TUNG', NULL);
INSERT INTO `phong_ban` VALUES (67, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 20, 'TO_DONG_CO_CANH_QUAT', NULL);
INSERT INTO `phong_ban` VALUES (68, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 20, 'TO_KIEM_HOMG', NULL);
INSERT INTO `phong_ban` VALUES (69, '2019-10-25 16:41:07', NULL, b'0', NULL, NULL, 20, 'TO_EP_DEM', NULL);
INSERT INTO `phong_ban` VALUES (70, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 21, 'TO_THIET_BI_HANG_KHONG', NULL);
INSERT INTO `phong_ban` VALUES (71, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 21, 'TO_VO_TUYEN', NULL);
INSERT INTO `phong_ban` VALUES (72, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 21, 'TO_DIEN', NULL);
INSERT INTO `phong_ban` VALUES (73, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 22, 'TO_DAC_CHUNG', NULL);
INSERT INTO `phong_ban` VALUES (74, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 22, 'TO_MAY_GAM', NULL);
INSERT INTO `phong_ban` VALUES (75, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 22, 'TO_CO_KHI', NULL);
INSERT INTO `phong_ban` VALUES (76, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 22, 'TO_TONG_HOP', NULL);
INSERT INTO `phong_ban` VALUES (77, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 23, 'TO_VAN_HANH_1', NULL);
INSERT INTO `phong_ban` VALUES (78, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 23, 'TO_VAN_HANH_2', NULL);
INSERT INTO `phong_ban` VALUES (79, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 23, 'TO_VAN_HANH_3', NULL);
INSERT INTO `phong_ban` VALUES (80, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 23, 'TO_KHAM_NGHIEM_SUA_CHUA_BINH', NULL);
INSERT INTO `phong_ban` VALUES (81, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 2, 'TO_CO_DIEN', NULL);
INSERT INTO `phong_ban` VALUES (82, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 1, 'TO_CO_KHI', NULL);
INSERT INTO `phong_ban` VALUES (83, '2019-10-25 16:41:08', NULL, b'0', NULL, NULL, 1, 'TO_MA', NULL);
INSERT INTO `phong_ban` VALUES (85, NULL, NULL, b'0', NULL, NULL, 1, 'TO_ULD', NULL);
INSERT INTO `phong_ban` VALUES (86, NULL, NULL, b'0', NULL, NULL, 1, 'TO_DOLLY', NULL);
INSERT INTO `phong_ban` VALUES (218, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (219, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (220, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (221, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (222, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (223, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (224, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (225, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (226, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (227, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (228, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (229, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (230, '2019-10-28 15:16:08', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (231, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (232, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (233, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (234, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (235, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (236, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (237, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (238, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (239, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (240, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (241, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (242, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (243, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (244, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (245, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (246, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (247, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (248, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (249, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (250, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (251, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (252, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (253, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (254, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (255, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (256, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (257, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (258, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (259, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (260, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (261, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (262, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (263, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (264, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (265, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (266, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (267, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (268, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (269, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (270, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (271, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (272, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (273, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (274, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (275, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (276, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (277, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (278, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (279, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (280, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (281, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (282, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (283, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (284, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (285, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (286, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (287, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (288, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (289, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (290, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (291, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (292, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (293, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (294, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (295, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (296, '2019-10-28 15:16:09', NULL, b'0', NULL, NULL, 2, '', NULL);
INSERT INTO `phong_ban` VALUES (297, '2019-10-28 15:16:10', NULL, b'0', NULL, NULL, 2, '', NULL);

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
  `nguoi_lap` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pdh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ma_so` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_nam_nguoi_lap` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_nam_phe_duyet` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_namtpkehoach` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_namtpkthk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ngay_thang_namtp_vat_tu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nguon_kinh_phi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `noi_dung` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `san_pham` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `so_to` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ten_nha_may` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tien_luong` decimal(19, 2) NULL DEFAULT NULL,
  `to_so` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tong_cong_dinh_muc_lao_dong` decimal(19, 2) NULL DEFAULT NULL,
  `tongdmvtkho` decimal(19, 2) NULL DEFAULT NULL,
  `tongdmvtmua_ngoai` decimal(19, 2) NULL DEFAULT NULL,
  `truong_phongkthk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `truong_phong_ke_hoach` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `truong_phong_vat_tu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pa_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phuong_an
-- ----------------------------
INSERT INTO `phuong_an` VALUES (303, '2019-10-28 15:26:42', NULL, b'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for request
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request`  (
  `request_id` bigint(20) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `created_by` bigint(20) NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `updated_by` bigint(20) NULL DEFAULT NULL,
  `create_id` bigint(20) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `tp_id` bigint(20) NULL DEFAULT NULL,
  `kh_id` bigint(20) NULL DEFAULT NULL,
  `pdh_id` bigint(20) NULL DEFAULT NULL,
  `pa_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`request_id`) USING BTREE,
  INDEX `FKhx216w8xik6t8ilmb0olgyy8r`(`tp_id`) USING BTREE,
  INDEX `FKe5xafpywjfklmj576eh7vpfnw`(`kh_id`) USING BTREE,
  INDEX `FK5am580ieoytavfegava5f4b6b`(`pdh_id`) USING BTREE,
  INDEX `FK3u0y738afx7s56ukl3x7uvcck`(`pa_id`) USING BTREE,
  INDEX `FKge50vg07rwkpubod0qwqaly50`(`user_id`) USING BTREE,
  CONSTRAINT `FK3u0y738afx7s56ukl3x7uvcck` FOREIGN KEY (`pa_id`) REFERENCES `phuong_an` (`pa_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK5am580ieoytavfegava5f4b6b` FOREIGN KEY (`pdh_id`) REFERENCES `phieu_dat_hang` (`pdh_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKe5xafpywjfklmj576eh7vpfnw` FOREIGN KEY (`kh_id`) REFERENCES `kiem_hong` (`kh_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKge50vg07rwkpubod0qwqaly50` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKhx216w8xik6t8ilmb0olgyy8r` FOREIGN KEY (`tp_id`) REFERENCES `cong_nhan_thanh_pham` (`tp_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request
-- ----------------------------
INSERT INTO `request` VALUES (298, '2019-10-28 15:26:42', 217, b'0', NULL, NULL, NULL, 0, 299, 300, 302, 303, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` bigint(20) NOT NULL,
  `authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'LEVEL2');
INSERT INTO `role` VALUES (3, 'LEVEL3');
INSERT INTO `role` VALUES (4, 'LEVEL4');
INSERT INTO `role` VALUES (215, 'ADMIN');
INSERT INTO `role` VALUES (216, 'USER');

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
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phong_ban_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `FKhdrwlest5ngwfj0x436uw59iv`(`phong_ban_id`) USING BTREE,
  CONSTRAINT `FKhdrwlest5ngwfj0x436uw59iv` FOREIGN KEY (`phong_ban_id`) REFERENCES `phong_ban` (`phong_ban_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (192, '2019-10-26 04:46:27', NULL, b'0', NULL, NULL, '', '$2a$10$Uno8hUBiivbFSsp6j/e4aeNtCQL4OnOL3U2fEx/G0dtZHGCyFdfLq', NULL);
INSERT INTO `user` VALUES (193, '2019-10-26 04:46:28', NULL, b'0', NULL, NULL, '', '$2a$10$Ul32fQ8UWnxLNpjKd5mVdun649HenLfV4h2IMxDj9BoBDPDfAAp92', NULL);
INSERT INTO `user` VALUES (194, '2019-10-26 04:46:28', NULL, b'0', NULL, NULL, '', '$2a$10$KIhe.jV62XP7SaBGY7qIrOij/EGtirNLJ.GyyR.RGGw.7D1ynrj1G', NULL);
INSERT INTO `user` VALUES (195, '2019-10-26 04:46:28', NULL, b'0', NULL, NULL, '', '$2a$10$ZVmY6ymHOxFxu1CtwCmbVO.dEleXXZdMhTaoISZRDidpLNuSUGSbu', NULL);
INSERT INTO `user` VALUES (196, '2019-10-26 04:46:28', NULL, b'0', NULL, NULL, '', '$2a$10$8Uz7TQVyHgTq9zdauDr4q.aIOgPqAWn2BA.csP1fzvvVZhA.TDwS.', NULL);
INSERT INTO `user` VALUES (197, '2019-10-26 04:46:28', NULL, b'0', NULL, NULL, '', '$2a$10$ZL40kNPg7a4rOxHA.Tz75.lI5mRQhoEw3zkUv/l6LjrB1EHF4KEym', NULL);
INSERT INTO `user` VALUES (198, '2019-10-26 04:46:29', NULL, b'0', NULL, NULL, '', '$2a$10$T5tkCcKYE3rhrA6aA4pWC.ddb0xfNwuB1rFVocCXB.4zvoBh1Zl4S', NULL);
INSERT INTO `user` VALUES (199, '2019-10-26 04:46:29', NULL, b'0', NULL, NULL, '', '$2a$10$N3DApqTv/AKDJjaJ055XSuegTMkPAzJe0b7CgPD2/UjnXt0apFlgC', NULL);
INSERT INTO `user` VALUES (200, '2019-10-26 04:46:29', NULL, b'0', NULL, NULL, '', '$2a$10$hAH7YbRURKgsNT3IYdrkneEchAI3VLc/kvfHxKDQvpST4cf00J/f6', NULL);
INSERT INTO `user` VALUES (201, '2019-10-26 04:46:29', NULL, b'0', NULL, NULL, '', '$2a$10$tOVAN38dVGNgK53vvlLZe./ehJJZaaJ/Qv.ZqiKS4Udd.QdDtQP2K', NULL);
INSERT INTO `user` VALUES (202, '2019-10-26 04:46:29', NULL, b'0', NULL, NULL, '', '$2a$10$oNmo1rlPETgBjZ2Gpy4lbuzCI.Ip1E2XIQNnn7xo/auETz.lbdMpm', NULL);
INSERT INTO `user` VALUES (203, '2019-10-26 04:46:29', NULL, b'0', NULL, NULL, '', '$2a$10$yxQrQSyeNgKrlg3LunpKy.o4sExAV577IIeDb7KKhXFPf5Lv8qioO', NULL);
INSERT INTO `user` VALUES (204, '2019-10-26 04:46:30', NULL, b'0', NULL, NULL, '', '$2a$10$k/g1wP6eHxq.Cdy7.Wfjae4cxK5vOMKv86LhF1HCC2c03z8R6JXZi', NULL);
INSERT INTO `user` VALUES (205, '2019-10-26 04:46:30', NULL, b'0', NULL, NULL, '', '$2a$10$SO140DgZRwy.SW18PwI.xOCUdhu8uqAPFAkNemnV2BWeM/pxFv8ti', NULL);
INSERT INTO `user` VALUES (206, '2019-10-26 04:46:30', NULL, b'0', NULL, NULL, '', '$2a$10$DDhLDvDk6RbMEi7ASjq1vezkutatTPZ1NElgMqoDlu1MtXlICGOB2', NULL);
INSERT INTO `user` VALUES (207, '2019-10-26 04:46:30', NULL, b'0', NULL, NULL, '', '$2a$10$hKrjJMffJMxN.UN3ICt7q.jGdHpvh4OepD1oaNMpfSwV0jZjJqVXO', NULL);
INSERT INTO `user` VALUES (208, '2019-10-26 04:46:30', NULL, b'0', NULL, NULL, '', '$2a$10$pqEbENr1Ydg1rBrLf3jVVeoErVIPReptGjLglMctjvh5FmLRlVknW', NULL);
INSERT INTO `user` VALUES (209, '2019-10-26 04:46:31', NULL, b'0', NULL, NULL, '', '$2a$10$kogJ/5oh3XxVI5D4w9cH3OXHhK92zwggJl3U93jtdjMeVE49o3ZSm', NULL);
INSERT INTO `user` VALUES (210, '2019-10-26 04:46:31', NULL, b'0', NULL, NULL, '', '$2a$10$Fhlc9qENPlL5i/N2NSnQbeIaUA0HTNGaCy7PkmvcY8Zu5oa8qcxg2', NULL);
INSERT INTO `user` VALUES (211, '2019-10-26 04:46:31', NULL, b'0', NULL, NULL, '', '$2a$10$niklDBvA9ipivXVb5jfKY.BD3m8Q5.TxQi2TMN9KVM5bkQdwfA2JK', NULL);
INSERT INTO `user` VALUES (212, '2019-10-26 04:46:32', NULL, b'0', NULL, NULL, '', '$2a$10$LZGHV4G0Qra4.0Y9IJQIV.IVvgGzhx98dPsZxbWfUoeNGR25DZUZ6', NULL);
INSERT INTO `user` VALUES (213, '2019-10-26 04:46:32', NULL, b'0', NULL, NULL, '', '$2a$10$YT1sevtZUdtJDtnM5dfO1Ou7g5NDRPsSPpNGHWSvxOglzWwbgdKbW', NULL);
INSERT INTO `user` VALUES (214, '2019-10-26 04:48:24', NULL, b'0', NULL, NULL, 'admin2', '$2a$10$u46w0yecqymcCirhEcrV1OaPtVuu4Bgu5VpTc.kLXAiL4NdQgk5hS', NULL);
INSERT INTO `user` VALUES (217, '2019-10-28 15:15:41', NULL, b'0', NULL, NULL, 'admin', '$2a$10$Ae3uXKVaV/JxeHt4KmkXGenKla2uAW9NYXWYX1wNQBJnaGgH9SSrW', NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (214, 1);
INSERT INTO `user_role` VALUES (217, 1);
INSERT INTO `user_role` VALUES (192, 2);
INSERT INTO `user_role` VALUES (193, 2);
INSERT INTO `user_role` VALUES (194, 2);
INSERT INTO `user_role` VALUES (195, 2);
INSERT INTO `user_role` VALUES (196, 2);
INSERT INTO `user_role` VALUES (197, 2);
INSERT INTO `user_role` VALUES (198, 2);
INSERT INTO `user_role` VALUES (199, 2);
INSERT INTO `user_role` VALUES (200, 2);
INSERT INTO `user_role` VALUES (201, 2);
INSERT INTO `user_role` VALUES (202, 2);
INSERT INTO `user_role` VALUES (203, 2);
INSERT INTO `user_role` VALUES (204, 2);
INSERT INTO `user_role` VALUES (205, 2);
INSERT INTO `user_role` VALUES (206, 2);
INSERT INTO `user_role` VALUES (207, 2);
INSERT INTO `user_role` VALUES (208, 2);
INSERT INTO `user_role` VALUES (209, 2);
INSERT INTO `user_role` VALUES (210, 2);
INSERT INTO `user_role` VALUES (211, 2);
INSERT INTO `user_role` VALUES (212, 2);
INSERT INTO `user_role` VALUES (213, 2);

SET FOREIGN_KEY_CHECKS = 1;