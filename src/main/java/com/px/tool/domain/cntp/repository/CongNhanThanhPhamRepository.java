package com.px.tool.domain.cntp.repository;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongNhanThanhPhamRepository extends JpaRepository<CongNhanThanhPham, Long> {
}
