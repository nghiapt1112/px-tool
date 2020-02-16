package com.px.tool.domain.vanbanden.repository;

import com.px.tool.domain.vanbanden.VanBanDen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VanBanDenRepository extends JpaRepository<VanBanDen, Long>, VanBanDenRepositoryCustom {

    @Query("SELECT v FROM VanBanDen v WHERE v.createdBy =?1 AND (v.deleted is null or v.deleted <> true OR v.deleted = false OR v.deleted = 0)")
    Page<VanBanDen> findByCreatedBy(Long createdBy, Pageable pageable);

    @Query("SELECT v FROM VanBanDen v WHERE v.noiNhan LIKE %:userId% and v.read is null or v.read <> true OR v.read = false OR v.read = 0 AND (v.deleted is null or v.deleted <> true OR v.deleted = false OR v.deleted = 0)")
    Page<VanBanDen> findNotification(@Param("userId") Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE VanBanDen  vbd SET vbd.folder = ?2 WHERE  vbd.vbdId = ?1")
    void moveFolder(Long vbdId, Long folderId);

    @Modifying
    @Transactional
    @Query("UPDATE VanBanDen  vbd SET vbd.deleted = true WHERE  vbd.vbdId = ?1")
    void delete(Long vbdId);
}
