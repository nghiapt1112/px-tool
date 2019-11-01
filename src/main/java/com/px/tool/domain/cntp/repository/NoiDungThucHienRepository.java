package com.px.tool.domain.cntp.repository;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NoiDungThucHienRepository extends JpaRepository<CongNhanThanhPham, Long> {
    @Modifying
    @Query("DELETE FROM CongNhanThanhPham pdhd WHERE pdhd.tpId IN ?1")
    void deleteAllByIds(Collection<Long> ids);
}
