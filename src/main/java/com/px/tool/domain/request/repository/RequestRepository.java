package com.px.tool.domain.request.repository;

import com.px.tool.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT rq FROM Request rq WHERE rq.createdBy IN ?1")
    List<Request> findByNguoiGui(Collection<Long> userIds);

    @Query("SELECT rq FROM Request rq WHERE rq.kiemHongReceiverId IN ?1 OR rq.phieuDatHangReceiverId IN ?1 OR rq.phuongAnReceiverId IN ?1 OR rq.cntpReceiverId IN ?1")
    List<Request> findByNguoiNhan(Collection<Long> userIds);

    @Modifying
    @Transactional
    @Query("UPDATE Request rq SET rq.kiemHongReceiverId =?2, rq.phieuDatHangReceiverId = ?3, rq.phuongAnReceiverId = ?4, rq.cntpReceiverId =?5 WHERE rq.requestId = ?1")
    void updateReceiverId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId);
}