package com.px.tool.domain.user.repository;

import com.google.common.collect.ImmutableList;
import com.px.tool.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<Long> group_29_40 = ImmutableList.of(8L, 9L);
    List<Long> group_17_25 = ImmutableList.of(17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L, 25L);
    List<Long> group_12_PLUS = ImmutableList.of(12L, 8L, 9L);
    List<Long> group_12 = ImmutableList.of(12L);
    List<Long> group_14 = ImmutableList.of(14L);
    List<Long> group_giam_doc = ImmutableList.of(2L, 3L, 4L, 5L, 6L);
    List<Long> group_cac_truong_phong = ImmutableList.of(8L, 9L, 12L, 14L);
    // CNTP
    List<Long> group_ke_hoach = ImmutableList.of(12L);
    List<Long> group_nv_KCS = ImmutableList.of(10L, 14L);

    Optional<User> findByEmail(String username);

    @Query("SELECT fb FROM User fb WHERE fb.phongBan.phongBanId IN ?1")
    List<User> findByGroup(Collection<Long> groups);

    @Query("SELECT u FROM User u WHERE u.userId IN ?1")
    List<User> findByIds(Collection<Long> ids);
}
