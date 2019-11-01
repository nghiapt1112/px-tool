package com.px.tool.domain.phuongan.repository;

import com.px.tool.domain.phuongan.PhuongAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongAnRepository extends JpaRepository<PhuongAn, Long> {
}
