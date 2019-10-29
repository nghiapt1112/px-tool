package com.px.tool.domain.kiemhong.repository;

import com.px.tool.domain.kiemhong.KiemHong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KiemHongRepository extends JpaRepository<KiemHong, Long> {
    List<KiemHong> findByCreatedBy(Long createdUserId);
}