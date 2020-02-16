ALTER TABLE `phuong_an`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;

ALTER TABLE `cong_nhan_thanh_pham`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;

alter table `user`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;

alter table `phong_ban`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;

alter table `kiem_hong`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;

alter table `request`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;

alter table `phieu_dat_hang`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;

alter table `van_ban_den`
    MODIFY `updated_at` bigint(20) UNSIGNED NULL DEFAULT 0,
    MODIFY `created_at` bigint(20) UNSIGNED NULL DEFAULT 0;