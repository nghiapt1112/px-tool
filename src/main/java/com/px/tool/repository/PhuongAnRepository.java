package com.px.tool.repository;

import com.px.tool.model.PhuongAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongAnRepository extends JpaRepository<PhuongAn, Long> {
}
