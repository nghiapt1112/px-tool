package com.px.tool.repository;

import com.px.tool.model.PhieuDatHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDatHangRepository extends JpaRepository<PhieuDatHang, Long> {
}
