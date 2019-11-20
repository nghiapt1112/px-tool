package com.px.tool.domain.vanbanden.repository;

import com.px.tool.domain.vanbanden.VanBanDen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VanBanDenRepository extends JpaRepository<VanBanDen, Long> {
    List<VanBanDen> findByCreatedBy(Long createdBy);
    List<VanBanDen> findByNoiNhan(Long noiNhan);

}
