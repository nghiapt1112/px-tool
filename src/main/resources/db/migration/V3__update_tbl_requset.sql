ALTER TABLE `request` CHANGE `receiver_id` `kiem_hong_receiver_id` bigint(20) DEFAULT NULL;
ALTER TABLE `request` ADD phieu_dat_hang_receiver_id bigint(1) DEFAULT NULL;
ALTER TABLE `request` ADD phuong_an_receiver_id bigint(1) DEFAULT NULL;
ALTER TABLE `request` ADD cntp_receiver_id bigint(1) DEFAULT NULL;

ALTER TABLE `user` ADD sign_img text CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL;