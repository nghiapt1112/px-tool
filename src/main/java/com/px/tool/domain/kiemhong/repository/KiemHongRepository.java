package com.px.tool.domain.kiemhong.repository;

import com.px.tool.domain.kiemhong.KiemHong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KiemHongRepository extends JpaRepository<KiemHong, Long> {
    List<KiemHong> findByCreatedBy(Long createdUserId);

    @Query("SELECT kh from KiemHong kh LEFT JOIN kh.kiemHongDetails dt WHERE dt.khDetailId = ?1 ")
    Optional<KiemHong> findByDetailId(Long detailId);
}