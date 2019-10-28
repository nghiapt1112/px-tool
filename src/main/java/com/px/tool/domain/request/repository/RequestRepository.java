package com.px.tool.domain.request.repository;

import com.px.tool.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT rq FROM Request rq WHERE rq.createdBy IN ?1")
    List<Request> findByNguoiGui(Collection<Long> userIds);

    @Query("SELECT rq FROM Request rq WHERE rq.createdBy IN ?1")
    List<Request> findByNguoiNhan(Collection<Long> userIds);
}
