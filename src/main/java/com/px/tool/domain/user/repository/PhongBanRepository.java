package com.px.tool.domain.user.repository;

import com.google.common.collect.ImmutableList;
import com.px.tool.domain.user.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PhongBanRepository extends JpaRepository<PhongBan, Long> {
    List<Long> group_29_40 = ImmutableList.of(8L, 9L);
    List<Long> group_17_25 = ImmutableList.of(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L, 25L);
    List<Long> group_12 = ImmutableList.of(12L);

    @Query("SELECT fb FROM PhongBan fb WHERE fb.group IN ?1")
    List<PhongBan> findByGroup(Collection<Long> groups);
}
