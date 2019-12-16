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
    List<CongNhanThanhPham> findAll(Long userId);

//    @Query("SELECT c FROM CongNhanThanhPham  c FETCH ALL PROPERTIES WHERE c.noiDungThucHiens.nghiemThu =?1")
    @Query(value = "SELECT c.* , n.* FROM cong_nhan_thanh_pham AS c INNER JOIN noi_dung_thuc_hien as n ON n.tp_id = c.tp_id WHERE n.nghiem_thu = ?1 AND c.tpkcs_xac_nhan <> 1", nativeQuery = true)
    List<CongNhanThanhPham> findAllTheoNhanVienKCS(Long userId);
}
