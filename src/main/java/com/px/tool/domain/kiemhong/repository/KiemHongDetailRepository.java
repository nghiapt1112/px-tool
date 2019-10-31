package com.px.tool.domain.kiemhong.repository;

import com.px.tool.domain.kiemhong.KiemHongDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KiemHongDetailRepository extends JpaRepository<KiemHongDetail, Long> {
}
