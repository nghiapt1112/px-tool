package com.px.tool.repository;

import com.px.tool.model.KiemHong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KiemHongRepository extends JpaRepository<KiemHong, Long> {
}
