package com.px.tool.domain.vanbanden.repository;

import com.px.tool.domain.vanbanden.VanBanDen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VanBanDenRepository extends JpaRepository<VanBanDen , Long> {
}
