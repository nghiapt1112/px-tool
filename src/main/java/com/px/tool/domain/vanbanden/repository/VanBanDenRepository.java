package com.px.tool.domain.vanbanden.repository;

import com.px.tool.domain.vanbanden.VanBanDen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VanBanDenRepository extends JpaRepository<VanBanDen, Long> {

    @Query("SELECT v FROM VanBanDen v WHERE v.createdBy =?1")
    Page<VanBanDen> findByCreatedBy(Long createdBy, Pageable pageable);

    @Query("SELECT v FROM VanBanDen v WHERE v.noiNhan =?1")
    Page<VanBanDen> findByNoiNhan(Long noiNhan, PageRequest of);

    @Query("SELECT v FROM VanBanDen v WHERE v.noiNhan =?1 and v.read is null or v.read <> true OR v.read = false OR v.read = 0")
    Page<VanBanDen> findNotification(Long noiNhan, PageRequest of);

}
