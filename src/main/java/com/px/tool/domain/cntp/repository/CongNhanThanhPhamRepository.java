package com.px.tool.domain.cntp.repository;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongNhanThanhPhamRepository extends JpaRepository<CongNhanThanhPham, Long> {
    @Query("SELECT c FROM CongNhanThanhPham  c WHERE c.quanDocId = ?1 OR c.tpkcsId = ?1 OR c.toTruong1Id = ?1 OR c.toTruong2Id = ?1 OR c.toTruong3Id =?1 OR c.toTruong4Id = ?1 OR c.toTruong5Id = ?1")
//    @Query("SELECT c FROM cong_nhan_thanh_pham  c WHERE c.phan_xuong_thuc_hien LIKE ")
    List<CongNhanThanhPham> findAll(Long userId);
}
